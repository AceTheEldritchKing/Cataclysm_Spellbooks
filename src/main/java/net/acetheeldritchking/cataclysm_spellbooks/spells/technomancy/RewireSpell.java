package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.RewirePotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class RewireSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "rewire");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getEffectDuration(spellLevel, caster), 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageMovementSpeed(spellLevel), 0), Component.translatable("attribute.name.generic.movement_speed")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageAttackDamage(spellLevel), 0), Component.translatable("attribute.name.generic.attack_damage")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageArmorReduction(spellLevel), 0), Component.translatable("attribute.name.generic.armor")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageToughnessReduction(spellLevel), 0), Component.translatable("attribute.name.generic.armor_toughness"))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(30)
            .build();

    public RewireSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
        this.baseManaCost = 25;
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
        return CastType.INSTANT;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_CONSTRUCT_SUMMON;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, .15f);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetingData)
        {
            // Recasts
            if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId()))
            {
                playerMagicData.getPlayerRecasts().addRecast
                        (new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity),
                                10*20, castSource, null), playerMagicData);
            }

            var targetEntity = targetingData.getTarget((ServerLevel) level);

            if (targetEntity != null && targetEntity instanceof MagicSummon)
            {
                targetEntity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.REWIRED_EFFECT.get(),
                        getEffectDuration(spellLevel, entity),
                        spellLevel - 1,
                        true,
                        true,
                        true));
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public int getEffectDuration(int spellLevel, LivingEntity caster)
    {
        int amount = (int) (getSpellPower(spellLevel, caster) * 20);
        return amount;
    }

    private float getPercentageMovementSpeed(int spellLevel)
    {
        return spellLevel * RewirePotionEffect.SPEED_PER_LEVEL * 100;
    }

    private float getPercentageAttackDamage(int spellLevel)
    {
        return spellLevel * RewirePotionEffect.ATTACK_DAMAGE_PER_LEVEL * 100;
    }

    private float getPercentageArmorReduction(int spellLevel)
    {
        return spellLevel * RewirePotionEffect.ARMOR_PENALTY_PER_LEVEL * 100;
    }

    private float getPercentageToughnessReduction(int spellLevel)
    {
        return spellLevel * RewirePotionEffect.TOUGHNESS_PENALTY_PER_LEVEL * 100;
    }
}
