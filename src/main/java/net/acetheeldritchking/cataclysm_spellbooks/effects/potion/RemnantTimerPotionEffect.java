package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import io.redspace.ironsspellbooks.effect.SummonTimer;
import net.minecraft.world.effect.MobEffectCategory;

public class RemnantTimerPotionEffect extends SummonTimer {
    public RemnantTimerPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffcd31);
    }
}
