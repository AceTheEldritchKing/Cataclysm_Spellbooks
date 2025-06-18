package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class BaseOverchargedPotionEffect extends MobEffect {
    public BaseOverchargedPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 8571381);
        this.addAttributeModifier(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), "b4860b65-f196-445e-885c-7ce06080cbf2", BaseOverchargedPotionEffect.SPELL_POWER_BONUS, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

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
