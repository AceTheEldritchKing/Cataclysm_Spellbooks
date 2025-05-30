package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.HardwireUpdatePotionEffect;
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
public class HardwareUpdateSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "hardware_update");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getEffectDuration(spellLevel, caster), 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageArmor(spellLevel), 0), Component.translatable("attribute.name.generic.armor")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageAttackDamage(spellLevel), 0), Component.translatable("attribute.name.generic.attack_damage"))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(50)
            .build();

    public HardwareUpdateSpell()
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
        if (!entity.hasEffect(MobEffectRegistry.CHARGED.get()) || !entity.hasEffect(MobEffectRegistry.HASTENED.get()) || !entity.hasEffect(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get()))
        {
            entity.removeEffect(MobEffectRegistry.CHARGED.get());
            entity.removeEffect(MobEffectRegistry.HASTENED.get());
            entity.removeEffect(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get());

            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get(),
                    getEffectDuration(spellLevel, entity),
                    spellLevel - 1,
                    true,
                    true,
                    true));
        } else
        {
            entity.removeEffect(MobEffectRegistry.CHARGED.get());
            entity.removeEffect(MobEffectRegistry.HASTENED.get());
            entity.removeEffect(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get());

            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get(),
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

    private float getPercentageArmor(int spellLevel)
    {
        return spellLevel * HardwireUpdatePotionEffect.ARMOR_PER_LEVEL * 100;
    }

    private float getPercentageAttackDamage(int spellLevel)
    {
        return spellLevel * HardwireUpdatePotionEffect.ATTACK_DAMAGE_PER_LEVEL * 100;
    }
}
