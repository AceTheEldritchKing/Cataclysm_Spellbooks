package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

// Empty class, handle stuff in server events
public class ImmunityPotionEffect extends MobEffect {
    public ImmunityPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 8571381);
    }
}
