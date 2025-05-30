package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class SoftwareUpdatePotionEffect extends MagicMobEffect {
    public SoftwareUpdatePotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 8571381);

        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "204e4bd9-08c5-4d77-b90c-4223bbc650ac", SoftwareUpdatePotionEffect.MOVEMENT_SPEED_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(AttributeRegistry.COOLDOWN_REDUCTION.get(), "9760a734-1ad6-4fb3-9d1c-54dff1e558d4", SoftwareUpdatePotionEffect.COOLDOWN_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    // Buffs
    public static final float MOVEMENT_SPEED_PER_LEVEL = 0.05f;
    public static final float COOLDOWN_PER_LEVEL = 0.10f;

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
