package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CooldownOverchargedPotionEffect extends MobEffect {
    public CooldownOverchargedPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 8571381);
        this.addAttributeModifier(AttributeRegistry.COOLDOWN_REDUCTION.get(), "13863b8b-8bc6-44c0-afd3-278df38d7c64", CooldownOverchargedPotionEffect.COOLDOWN_BONUS, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), "1b4e6b3f-991c-4c4f-bc9f-6237cfaec5e3", CooldownOverchargedPotionEffect.SPELL_POWER_BONUS, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float COOLDOWN_BONUS = 0.10f;
    public static final float SPELL_POWER_BONUS = 0.10f;

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
