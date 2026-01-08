package net.acetheeldritchking.cataclysm_spellbooks.mixins.entities;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.IMagicSummon;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(IMagicSummon.class)
public interface MagicSummonMixin extends IMagicSummon {
    @Shadow void onUnSummon();

    @Override
    default void onAntiMagic(MagicData playerMagicData) {
        if (this instanceof LivingEntity livingEntity)
        {
            if (livingEntity.hasEffect(CSPotionEffectRegistry.IPS_POTION_EFFECT.get()))
            {
                //System.out.println("Cancel unsummon");
            } else
            {
                onUnSummon();
            }
        }
    }
}
