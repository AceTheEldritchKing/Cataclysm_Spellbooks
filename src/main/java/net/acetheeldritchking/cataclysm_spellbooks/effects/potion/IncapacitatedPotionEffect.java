package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class IncapacitatedPotionEffect extends MobEffect {
    public IncapacitatedPotionEffect() {
        super(MobEffectCategory.HARMFUL, 11319909);
        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "17d794d8-4e48-4e95-86d4-f34650d75844",
                -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
