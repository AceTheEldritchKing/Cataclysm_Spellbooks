package net.acetheeldritchking.cataclysm_spellbooks.spells.blood;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.final_rend.FinalRendAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSoundRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
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
public class FinalRendSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "final_rend");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", getDamageText(spellLevel, caster)),
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.stringTruncation(getEffectDuration(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.BLOOD_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(45)
            .build();

    public FinalRendSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 25;
        this.spellPowerPerLevel = 1;
        this.castTime = 70;
        this.baseManaCost = 150;
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
        return CSSpellAnimations.ANIMATION_POWERFUL_SWORD_SLASH;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundRegistry.SHOCKWAVE_PREPARE.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(CSSoundRegistry.ELECTRIC_SWORD_SWING.get());
    }

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.IMMUNITY_EFFECT.get(), getCastTime(spellLevel), 0, false, false, false));

        super.onServerPreCast(level, spellLevel, entity, playerMagicData);
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        if (entity.tickCount % 10 == 0)
        {
            // ring 1
            int count = 16;
            float particleRadius = 1.25f;
            for (int i = 0; i < count; i++) {
                double x, z;
                double theta = Math.toRadians((double) 360 / count) * i;
                x = Math.cos(theta) * particleRadius;
                z = Math.sin(theta) * particleRadius;
                MagicManager.spawnParticles(entity.level(), ModParticle.SPARK.get(), entity.position().x + x, entity.position().y, entity.position().z + z, 1, 0, 0, 0, 0.1, false);
            }

            // ring 2
            int count2 = 16;
            float particleRadius2 = 3.25f;
            for (int i = 0; i < count; i++) {
                double x, z;
                double theta = Math.toRadians((double) 360 / count2) * i;
                x = Math.cos(theta) * particleRadius2;
                z = Math.sin(theta) * particleRadius2;
                MagicManager.spawnParticles(entity.level(), ModParticle.SPARK.get(), entity.position().x + x, entity.position().y, entity.position().z + z, 1, 0, 0, 0, 0.1, false);
            }

            // ring 3
            int count3 = 16;
            float particleRadius3 = 5.25f;
            for (int i = 0; i < count; i++) {
                double x, z;
                double theta = Math.toRadians((double) 360 / count3) * i;
                x = Math.cos(theta) * particleRadius3;
                z = Math.sin(theta) * particleRadius3;
                MagicManager.spawnParticles(entity.level(), ModParticle.SPARK.get(), entity.position().x + x, entity.position().y, entity.position().z + z, 1, 0, 0, 0, 0.1, false);
            }
        }

        super.onServerCastTick(level, spellLevel, entity, playerMagicData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = 3.25F;
        float distance = 2.15F;
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
        boolean mirrored = false;
        var selection = new SpellSelectionManager((Player) entity).getSelection();
        new SpellSelectionManager((Player) entity).getSelection();
        if (selection != null)
        {
            mirrored = selection.slot.equals(SpellSelectionManager.OFFHAND);
        }

        FinalRendAoE swipe = new FinalRendAoE(level, mirrored);
        swipe.moveTo(hitLocation);
        swipe.setYRot(entity.getYRot());
        swipe.setEffectDuration(getEffectDuration(spellLevel, entity));
        swipe.setEffectAmplifier(spellLevel);
        level.addFreshEntity(swipe);

        ScreenShake_Entity.ScreenShake(level, entity.position(), 6.0F, 0.08F, 20, 20);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setIFrames(0).setLifestealPercent(1.0F);
    }

    private float getDamage(int spellLevel, LivingEntity entity)
    {
        float damage = CSUtils.getDamageForAttributes(this, entity, spellLevel, CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), 1);
        return (damage * 1.5F) + Utils.getWeaponDamage(entity, MobType.UNDEFINED);
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
