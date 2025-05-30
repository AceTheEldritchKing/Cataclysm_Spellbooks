package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.SoftwareUpdatePotionEffect;
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
public class SoftwareUpdateSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "software_update");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getEffectDuration(spellLevel, caster), 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageMovementSpeed(spellLevel), 0), Component.translatable("attribute.name.generic.movement_speed")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageCooldown(spellLevel), 0), Component.translatable("attribute.irons_spellbooks.cooldown_reduction"))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(50)
            .build();

    public SoftwareUpdateSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
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
        return CastType.INSTANT;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!entity.hasEffect(MobEffectRegistry.CHARGED.get()) || !entity.hasEffect(MobEffectRegistry.HASTENED.get()) || !entity.hasEffect(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get()))
        {
            entity.removeEffect(MobEffectRegistry.CHARGED.get());
            entity.removeEffect(MobEffectRegistry.HASTENED.get());
            entity.removeEffect(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get());

            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get(),
                    getEffectDuration(spellLevel, entity),
                    spellLevel - 1,
                    true,
                    true,
                    true));
        } else
        {
            entity.removeEffect(MobEffectRegistry.CHARGED.get());
            entity.removeEffect(MobEffectRegistry.HASTENED.get());
            entity.removeEffect(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get());

            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get(),
                    getEffectDuration(spellLevel, entity),
                    spellLevel - 1,
                    true,
                    true,
                    true));
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
        return spellLevel * SoftwareUpdatePotionEffect.MOVEMENT_SPEED_PER_LEVEL * 100;
    }

    private float getPercentageCooldown(int spellLevel)
    {
        return spellLevel * SoftwareUpdatePotionEffect.COOLDOWN_PER_LEVEL * 100;
    }
}
