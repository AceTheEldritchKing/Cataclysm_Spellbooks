package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.AbyssalPredatorPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

@AutoSpellConfig
public class AbyssalPredatorSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "abyssal_predator");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getSpellPower(spellLevel, caster) * 20, 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageSwimSpeed(spellLevel, caster), 0), Component.translatable("attribute.name.forge.swim_speed")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageAttackDamage(spellLevel, caster), 0), Component.translatable("attribute.name.generic.attack_damage")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageAttackSpeed(spellLevel, caster), 0), Component.translatable("attribute.name.generic.attack_speed"))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(40)
            .build();

    public AbyssalPredatorSpell()
    {
        this.manaCostPerLevel = 25;
        this.baseSpellPower = 30;
        this.spellPowerPerLevel = 8;
        this.castTime = 0;
        this.baseManaCost = 55;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get(),
                (int) (getSpellPower(spellLevel, entity) * 20),
                spellLevel - 1,
                false,
                false,
                true));

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getPercentageSwimSpeed(int spellLevel, LivingEntity caster)
    {
        return spellLevel * AbyssalPredatorPotionEffect.SWIM_SPEED_PER_LEVEL * 100;
    }

    private float getPercentageAttackDamage(int spellLevel, LivingEntity caster)
    {
        return spellLevel * AbyssalPredatorPotionEffect.ATTACK_DAMAGE_PER_LEVEL * 100;
    }

    private float getPercentageAttackSpeed(int spellLevel, LivingEntity caster)
    {
        return spellLevel * AbyssalPredatorPotionEffect.ATTACK_SPEED_PER_LEVEL * 100;
    }
}
