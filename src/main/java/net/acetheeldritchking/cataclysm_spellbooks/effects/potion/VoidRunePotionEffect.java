package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class VoidRunePotionEffect extends MobEffect {
    public VoidRunePotionEffect() {
        super(MobEffectCategory.HARMFUL, 7812264);
    }

    public static final float ATTACK_DAMAGE_PER_SPELL_LEVEL = 0.10F;

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide())
        {
            ServerLevel world = (ServerLevel) pLivingEntity.level;

            double x = pLivingEntity.getX();
            double y = pLivingEntity.getY();
            double z = pLivingEntity.getZ();
            float yRot = pLivingEntity.getYRot();

            for (int i = 0; i < pAmplifier; i++)
            {
                world.addFreshEntity(new Void_Rune_Entity(world, x, y ,z, yRot, 1, ATTACK_DAMAGE_PER_SPELL_LEVEL, null));
            }
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 8 == 0;
    }
}
