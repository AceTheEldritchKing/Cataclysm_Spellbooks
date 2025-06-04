package net.acetheeldritchking.cataclysm_spellbooks.mixins.entities;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MagicSummon.class)
public interface MagicSummonMixin extends MagicSummon {
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
