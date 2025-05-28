package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

public class RewirePotionEffect extends MagicMobEffect {
    public RewirePotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 132213);

        this.addAttributeModifier(Attributes.MOVEMENT_SPEED, "a7901d59-69c9-4189-9f07-50d86951035e", RewirePotionEffect.SPEED_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "6e03f63d-e8c1-45d5-98fb-0faf6d41a488", RewirePotionEffect.ATTACK_DAMAGE_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);

        this.addAttributeModifier(Attributes.ARMOR, "8040653e-b4e9-42b9-8805-9064a1f03b91", RewirePotionEffect.ARMOR_PENALTY_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(Attributes.ARMOR_TOUGHNESS, "742c29f8-64a0-4e50-827f-43604dc87f67", RewirePotionEffect.TOUGHNESS_PENALTY_PER_LEVEL, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    // Buffs
    public static final float SPEED_PER_LEVEL = 0.20f;
    public static final float ATTACK_DAMAGE_PER_LEVEL = 0.20f;

    // Debuffs
    public static final float ARMOR_PENALTY_PER_LEVEL = 0.20f;
    public static final float TOUGHNESS_PENALTY_PER_LEVEL = 0.20f;

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
