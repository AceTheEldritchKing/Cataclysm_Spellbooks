package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.magic.SpellSelectionManager;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.MultiTargetEntityCastData;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISpellDamageSource;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.disabling_swipe.DisablingSwipeAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
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
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", getDamageText(spellLevel, caster)),
                Component.translatable("ui.irons_spellbooks.effect_length", getEffectDuration(spellLevel, caster)),
                Component.translatable("ui.cataclysm_spellbooks.recast_bonus_damage", getBonusDamage(spellLevel, caster))
        );
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        var recasts = playerMagicData.getPlayerRecasts();

        if (!recasts.hasRecastForSpell(getSpellId()))
        {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 100, castSource, null), playerMagicData);
        } else
        {
            var instance = recasts.getRecastInstance(this.getSpellId());
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);

        float radius = 3.25F;
        float distance = 1.65F;
        Vec3 hitLocation = serverPlayer.position().add(0, serverPlayer.getBbHeight() * 0.3F, 0).add(serverPlayer.getForward().multiply(distance, 0.35F, distance));
        var entities = serverPlayer.level.getEntities(serverPlayer, AABB.ofSize(hitLocation, radius * 2, radius, radius * 2));

        for (Entity target : entities)
        {
            if (serverPlayer.isPickable() && serverPlayer.distanceToSqr(target) < radius * radius && Utils.hasLineOfSight(serverPlayer.level, serverPlayer.getEyePosition(), target.getBoundingBox().getCenter(), true))
            {
                if (DamageSources.applyDamage(target, getDamage(recastInstance.getSpellLevel(), serverPlayer) + getBonusDamage(recastInstance.getSpellLevel(), serverPlayer), this.getDamageSource(serverPlayer)))
                {
                    MagicManager.spawnParticles(serverPlayer.level, ParticleHelper.ELECTRIC_SPARKS, target.getX(), target.getY() + target.getBbHeight() * .5f, target.getZ(), 50, target.getBbWidth() * .5f, target.getBbHeight() * .5f, target.getBbWidth() * .5f, .03, false);
                    EnchantmentHelper.doPostDamageEffects(serverPlayer, target);
                }
            }
        }
        boolean mirrored = false;
        var selection = new SpellSelectionManager(serverPlayer).getSelection();
        new SpellSelectionManager(serverPlayer).getSelection();
        if (selection != null)
        {
            mirrored = selection.slot.equals(SpellSelectionManager.OFFHAND);
        }

        DisablingSwipeAoE swipe = new DisablingSwipeAoE(serverPlayer.level, mirrored);
        swipe.moveTo(hitLocation);
        swipe.setYRot(serverPlayer.getYRot());
        swipe.setEffectDuration(getEffectDuration(recastInstance.getSpellLevel(), serverPlayer));
        swipe.setEffectAmplifier(recastInstance.getSpellLevel());
        serverPlayer.level.addFreshEntity(swipe);
    }

    @Override
    public DamageSource getDamageSource(@Nullable Entity projectile, @Nullable Entity attacker) {
        return ((ISpellDamageSource) super.getDamageSource(projectile, attacker)).get();
    }

    private float getDamage(int spellLevel, LivingEntity entity)
    {
        return (getSpellPower(spellLevel, entity) / 3.5F) + Utils.getWeaponDamage(entity, MobType.UNDEFINED);
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
