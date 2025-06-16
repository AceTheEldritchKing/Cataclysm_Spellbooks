package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISpellDamageSource;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.disabling_swipe.DisablingSwipeAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
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

@AutoSpellConfig
public class DisablingSwipeSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "disabling_swipe");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", getDamageText(spellLevel, caster)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(15)
            .build();

    public DisablingSwipeSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 3;
        this.castTime = 10;
        this.baseManaCost = 30;
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
    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return getCastTime(spellLevel);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ONE_HANDED_HORIZONTAL_SWING_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = 3.25F;
        float distance = 1.65F;
        Vec3 hitLocation = entity.position().add(0, entity.getBbHeight() * 0.3F, 0).add(entity.getForward().multiply(distance, 0.35F, distance));
        var entities = level.getEntities(entity, AABB.ofSize(hitLocation, radius * 2, radius, radius * 2));

        for (Entity target : entities)
        {
            if (entity.isPickable() && entity.distanceToSqr(target) < radius * radius && Utils.hasLineOfSight(level, entity.getEyePosition(), target.getBoundingBox().getCenter(), true))
            {
                if (DamageSources.applyDamage(target, getDamage(spellLevel, entity), this.getDamageSource(entity)))
                {
                    MagicManager.spawnParticles(level, ParticleHelper.ELECTRIC_SPARKS, target.getX(), target.getY() + target.getBbHeight() * .5f, target.getZ(), 50, target.getBbWidth() * .5f, target.getBbHeight() * .5f, target.getBbWidth() * .5f, .03, false);
                    EnchantmentHelper.doPostDamageEffects(entity, target);
                }
            }
        }
        boolean mirrored = false;
        if (entity instanceof Player player)
        {
            var selection = new SpellSelectionManager(player).getSelection();
            new SpellSelectionManager(player).getSelection();
            if (selection != null)
            {
                mirrored = selection.slot.equals(SpellSelectionManager.OFFHAND);
            }
        }

        DisablingSwipeAoE swipe = new DisablingSwipeAoE(level, mirrored);
        swipe.moveTo(hitLocation);
        swipe.setYRot(entity.getYRot());
        level.addFreshEntity(swipe);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public DamageSource getDamageSource(@Nullable Entity projectile, @Nullable Entity attacker) {
        return ((ISpellDamageSource) super.getDamageSource(projectile, attacker)).get();
    }

    private float getDamage(int spellLevel, LivingEntity entity)
    {
        return getSpellPower(spellLevel, entity) + Utils.getWeaponDamage(entity, MobType.UNDEFINED);
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
}
