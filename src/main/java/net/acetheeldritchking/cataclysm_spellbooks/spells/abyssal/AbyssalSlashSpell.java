package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class AbyssalSlashSpell extends AbstractAbyssalSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "abyssal_slash");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", getDamageText(spellLevel, caster))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(40)
            .build();

    public AbyssalSlashSpell()
    {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 3;
        this.castTime = 15;
        this.baseManaCost = 35;
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
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.FLAMING_STRIKE_UPSWING.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.ABYSS_BLAST_ONLY_SHOOT.get());
    }

    @Override
    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    @Override
    public void onClientPreCast(Level level, int spellLevel, LivingEntity entity, InteractionHand hand, @Nullable MagicData playerMagicData) {
        super.onClientPreCast(level, spellLevel, entity, hand, playerMagicData);

        spawnParticles(entity);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = 4.10f;
        float distance = 2.0f;
        Vec3 slashLocation = entity.position().add(entity.getForward().multiply(distance, 0.3f, distance));
        spawnParticles(entity);

        var entities = level.getEntities(entity,
                AABB.ofSize(slashLocation, radius * 2, radius, radius *2));
        for (Entity targetEntity : entities)
        {
            if (entity.isPickable() && entity.distanceToSqr(targetEntity) < radius * radius &&
            Utils.hasLineOfSight(level, entity.getEyePosition(), targetEntity.getBoundingBox().getCenter(),
                    true))
            {
                if (DamageSources.applyDamage(targetEntity, getDamage(spellLevel, entity), this.getDamageSource(entity)))
                {
                    if (targetEntity instanceof LivingEntity livingTarget)
                    {
                        // Do extra damage if target has any of the effects on them, hehehe
                        Boolean hasBurn = livingTarget.hasEffect(ModEffect.EFFECTABYSSAL_BURN.get());
                        Boolean hasFear = livingTarget.hasEffect(ModEffect.EFFECTABYSSAL_FEAR.get());
                        Boolean hasCurse = livingTarget.hasEffect(ModEffect.EFFECTABYSSAL_CURSE.get());

                        if (hasCurse || hasBurn || hasFear)
                        {
                            float bonus = 0.2f;
                            float bonusDamage = getDamage(spellLevel, entity) * bonus;
                            float totalDamage = getDamage(spellLevel, entity) + bonusDamage;

                            DamageSources.applyDamage(livingTarget, totalDamage, this.getDamageSource(entity));
                        }
                    }
                    EnchantmentHelper.doPostDamageEffects(entity, targetEntity);
                }
            }
        }
        CameraShakeManager.addCameraShake(new CameraShakeData(10, entity.position(), 10));

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) + Utils.getWeaponDamage(caster, MobType.UNDEFINED);
    }

    private String getDamageText(int spellLevel, LivingEntity caster)
    {
        if (caster != null)
        {
            float weaponDamage = Utils.getWeaponDamage(caster, MobType.UNDEFINED);
            String plus = "";
            if (weaponDamage > 0)
            {
                plus = String.format(" (+%s)", Utils.stringTruncation(weaponDamage, 1));
            }
            String damage = Utils.stringTruncation(getDamage(spellLevel, caster), 1);
            return damage + plus;
        }
        return "" + getSpellPower(spellLevel, caster);
    }

    private void spawnParticles(LivingEntity entity)
    {
        ServerLevel level = (ServerLevel) entity.level();
        level.sendParticles(ModParticle.SHOCK_WAVE.get(), entity.getX(), entity.getY() + 1, entity.getZ(), 1, 0, 0, 0, 0.0);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ONE_HANDED_HORIZONTAL_SWING_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }
}
