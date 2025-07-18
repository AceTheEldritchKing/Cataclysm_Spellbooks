package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class ManaOverchargedPotionEffect extends MobEffect {
    public ManaOverchargedPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 8571381);
        this.addAttributeModifier(AttributeRegistry.MAX_MANA.get(), "fa91389c-34f3-4fcb-866f-ffcc44c0e3f0", ManaOverchargedPotionEffect.MANA_BONUS, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), "821167be-7ff7-4ca8-92a6-3a6c2de2b52a", ManaOverchargedPotionEffect.SPELL_POWER_BONUS, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float MANA_BONUS = 0.10f;
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
