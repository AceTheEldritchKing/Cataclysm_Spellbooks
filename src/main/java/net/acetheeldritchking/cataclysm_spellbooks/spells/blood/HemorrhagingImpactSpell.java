package net.acetheeldritchking.cataclysm_spellbooks.spells.blood;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.spells.blood_needle.BloodNeedle;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blood_crystal.BloodCrystalProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@AutoSpellConfig
public class HemorrhagingImpactSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "hemorrhaging_impact");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.cataclysm_spellbooks.needle_damage", Utils.stringTruncation(getDamage(spellLevel, caster)/2, 2))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.BLOOD_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(120)
            .build();

    public HemorrhagingImpactSpell()
    {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 25;
        this.spellPowerPerLevel = 0;
        this.castTime = 35;
        this.baseManaCost = 150;
    }

    @Override
    public boolean canBeCraftedBy(Player player) {
        return false;
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean allowCrafting() {
        return false;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return getCastTime(spellLevel);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_CHARGE_GUN_FULL;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Blood Spear
        BloodCrystalProjectile bloodSpear = new BloodCrystalProjectile(level, entity);

        bloodSpear.setPos(entity.position().add(0, entity.getEyeHeight() - bloodSpear.getBoundingBox().getYsize() * .5f, 0));
        bloodSpear.shootFromRotation(entity, entity.getXRot(), entity.getYHeadRot(), 0, 1, 1);

        float damage = getDamage(spellLevel, entity);

        bloodSpear.setDamage(damage);
        bloodSpear.setExplosionRadius(3);

        ScreenShake_Entity.ScreenShake(level, entity.position(), 5.0F, 0.15F, 20, 20);

        level.addFreshEntity(bloodSpear);

        // Blood Needles
        Timer needleTimer = new Timer();
        int needleTimerDelay = 100;
        int needleCount = 10;

        int degrees = 360 / needleCount;
        var needleRaycast = Utils.raycastForEntity(level, entity, 32,true);
        for (int i = 0; i < needleCount; i++)
        {
            int delay = needleCount * needleTimerDelay;

            needleTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // needles
                    BloodNeedle needle = new BloodNeedle(level, entity);
                    int rot = degrees * needleCount - (degrees/2);
                    needle.setDamage(damage/2);
                    needle.setZRot(rot);
                    Vec3 spawn = entity.getEyePosition().add(new Vec3(0, 1.5, 0).zRot(rot * Mth.DEG_TO_RAD).xRot(-entity.getXRot() * Mth.DEG_TO_RAD).yRot(-entity.getYRot() * Mth.DEG_TO_RAD));
                    needle.moveTo(spawn);
                    needle.shoot(needleRaycast.getLocation().subtract(spawn).normalize());

                    level.addFreshEntity(needle);
                }
            }, delay);

            needleTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    needleTimer.cancel();
                }
            }, (long) (needleCount + 1) * needleTimerDelay);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setLifestealPercent(0.75F);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 2.0f;
    }
}
