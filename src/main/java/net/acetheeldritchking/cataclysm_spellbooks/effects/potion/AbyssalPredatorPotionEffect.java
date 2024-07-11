package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;

public class AbyssalPredatorPotionEffect extends MagicMobEffect {
    public AbyssalPredatorPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 5984177);
        this.addAttributeModifier(Attributes.ATTACK_SPEED, "f5f22724-fb4a-49f9-b303-cdf84357c50b", AbyssalPredatorPotionEffect.ATTACK_SPEED_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "3e8ee83b-e6f3-4c70-a39c-de09c8e66858", AbyssalPredatorPotionEffect.ATTACK_DAMAGE_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(ForgeMod.SWIM_SPEED.get(), "e31ad3ab-4985-4a00-9656-bc42bd52e494", AbyssalPredatorPotionEffect.SWIM_SPEED_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float SWIM_SPEED_PER_LEVEL = 0.30f;
    // Base buffs, deal more damage when in water
    public static final float ATTACK_SPEED_PER_LEVEL = 0.20f;
    public static final float ATTACK_DAMAGE_PER_LEVEL = 0.10f;
    // Bonus Effects while in water
    public static final float MOVEMENT_SPEED_BONUS_PER_LEVEL = 0.10f;
    public static final float ATTACK_DAMAGE_BONUS_PER_LEVEL = 0.05f;

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
