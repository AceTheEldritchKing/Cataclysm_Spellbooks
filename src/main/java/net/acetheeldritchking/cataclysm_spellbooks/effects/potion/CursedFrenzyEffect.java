package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

import java.util.List;

public class CursedFrenzyEffect extends MobEffect {
    public CursedFrenzyEffect() {
        super(MobEffectCategory.BENEFICIAL, 4583645);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        List<Entity> entitiesNearby = pLivingEntity.level().getEntities(pLivingEntity, pLivingEntity.getBoundingBox().inflate(0.25, 0.5, 0.25));

        if (!entitiesNearby.isEmpty())
        {
            for (Entity targets : entitiesNearby)
            {
                if (targets instanceof LivingEntity)
                {
                    DamageSources.applyDamage(targets, pAmplifier, SpellRegistries.CURSED_RUSH.get().getDamageSource(pLivingEntity));
                    targets.invulnerableTime = 20;
                }
            }
        } /*else if (pLivingEntity.verticalCollision)
        {
            if (!pLivingEntity.level.isClientSide())
            {
                System.out.println("Vertical Collision");
                CSUtils.spawnHalberdWindmill(10, 10, 1.0F, 1.0F, 0.2F, 1, pLivingEntity, pLivingEntity.level, 5, 2);
            }
            //spawnHalberdLine();
        } */
        else if (pLivingEntity.horizontalCollision || pLivingEntity.minorHorizontalCollision)
        {
            /*if (!pLivingEntity.level.isClientSide())
            {
                System.out.println("Horizontal Collision");
                CSUtils.spawnHalberdWindmill(10, 10, 1.0F, 1.0F, 0.2F, 1, pLivingEntity, pLivingEntity.level, 5, 2);
            }*/
            pLivingEntity.removeEffect(this);
        }
        pLivingEntity.fallDistance = 0;

        if (pLivingEntity.level().isClientSide)
        {
            // Should be every 2 seconds
            if (pLivingEntity.tickCount % 3 == 0)
            {
                double x = pLivingEntity.getX();
                double y = pLivingEntity.getY() + pLivingEntity.getBbHeight() / 2;
                double z = pLivingEntity.getZ();

                float yaw = (float) Math.toRadians(-pLivingEntity.getYRot());
                float yaw2 = (float) Math.toRadians(-pLivingEntity.getYRot() + 180);
                float pitch = (float) Math.toRadians(-pLivingEntity.getXRot());
                pLivingEntity.level().addParticle(new RingParticle.RingData(yaw, pitch, 40, 0.337f, 0.925f, 0.8f, 1.0f, 50f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0, 0, 0);
                pLivingEntity.level().addParticle(new RingParticle.RingData(yaw2, pitch, 40, 0.337f, 0.925f, 0.8f, 1.0f, 50f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0, 0, 0);
            }
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
