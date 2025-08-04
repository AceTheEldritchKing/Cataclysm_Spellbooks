package net.acetheeldritchking.cataclysm_spellbooks.spells.blood;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.quick_strike.QuickStrikeAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSoundRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
public class QuickStrikeSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "quick_strike");
    private Boolean mirrored;

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", getDamageText(spellLevel, caster)),
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.stringTruncation(getEffectDuration(spellLevel, caster), 1)),
                Component.translatable("ui.cataclysm_spellbooks.recast_bonus_damage", Utils.stringTruncation(getBonusDamage(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.BLOOD_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(25)
            .build();

    public QuickStrikeSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 3;
        this.castTime = 6;
        this.baseManaCost = 50;
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
    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return getCastTime(spellLevel);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        if (mirrored)
        {
            return CSSpellAnimations.ANIMATION_LEFT_HORIZONTAL_SLASH;
        } else
        {
            return CSSpellAnimations.ANIMATION_RIGHT_HORIZONTAL_SLASH;
        }
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.DIVINE_SMITE_WINDUP.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(CSSoundRegistry.ELECTRIC_SWORD_SWING.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId()))
        {
            playerMagicData.getPlayerRecasts().addRecast
                    (new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity),
                            15*20, castSource, null), playerMagicData);
        }

        float radius = 3.25F;
        float distance = 2.2F;
        Vec3 hitLocation = entity.position().add(0, entity.getBbHeight() * 0.3F, 0).add(entity.getForward().multiply(distance, 0.35F, distance));
        var entities = entity.level().getEntities(entity, AABB.ofSize(hitLocation, radius * 2, radius, radius * 2));

        for (Entity target : entities)
        {
            if (entity.isPickable() && entity.distanceToSqr(target) < radius * radius && Utils.hasLineOfSight(level, entity.getEyePosition(), target.getBoundingBox().getCenter(), true))
            {
                if (DamageSources.applyDamage(target, getDamage(spellLevel, entity) + getBonusDamage(spellLevel, entity), this.getDamageSource(entity)))
                {
                    MagicManager.spawnParticles(level, ParticleHelper.BLOOD, target.getX(), target.getY() + target.getBbHeight() * .5f, target.getZ(), 50, target.getBbWidth() * .5f, target.getBbHeight() * .5f, target.getBbWidth() * .5f, .03, false);
                    EnchantmentHelper.doPostDamageEffects(entity, target);
                }
            }
        }

        System.out.println("Recasts remaining: " + playerMagicData.getPlayerRecasts().getRemainingRecastsForSpell(spellId.toString()));
        mirrored = playerMagicData.getPlayerRecasts().getRemainingRecastsForSpell(spellId.toString()) % 2 == 0;

        QuickStrikeAoE swipe = new QuickStrikeAoE(level, mirrored);
        swipe.moveTo(hitLocation);
        swipe.setYRot(entity.getYRot());
        swipe.setEffectDuration(getEffectDuration(spellLevel, entity));
        swipe.setEffectAmplifier(spellLevel);
        level.addFreshEntity(swipe);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setIFrames(0).setLifestealPercent(0.50F);
    }

    private float getDamage(int spellLevel, LivingEntity entity)
    {
        return (getSpellPower(spellLevel, entity) / 1.5F) + Utils.getWeaponDamage(entity, MobType.UNDEFINED);
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster)
    {
        return getRecastCount(spellLevel, caster);
    }

    private float getEffectDuration(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster);
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
