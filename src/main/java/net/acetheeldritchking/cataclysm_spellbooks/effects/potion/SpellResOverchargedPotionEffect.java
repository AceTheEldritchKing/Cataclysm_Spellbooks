package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class SpellResOverchargedPotionEffect extends MobEffect {
    public SpellResOverchargedPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 8571381);
        this.addAttributeModifier(AttributeRegistry.SPELL_RESIST.get(), "928f0a67-df68-42eb-b838-52c6dc4255c0", SpellResOverchargedPotionEffect.SPELL_RES_BONUS, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), "531b39a8-0276-4869-b703-5582a6041577", SpellResOverchargedPotionEffect.SPELL_POWER_BONUS, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float SPELL_RES_BONUS = 0.10f;
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
