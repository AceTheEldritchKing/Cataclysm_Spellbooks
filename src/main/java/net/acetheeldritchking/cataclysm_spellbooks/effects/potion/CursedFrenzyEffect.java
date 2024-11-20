package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

public class CursedFrenzyEffect extends MobEffect {
    public CursedFrenzyEffect() {
        super(MobEffectCategory.BENEFICIAL, 4583645);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        List<Entity> entitiesNearby = pLivingEntity.level.getEntities(pLivingEntity, pLivingEntity.getBoundingBox().inflate(0.25, 0.5, 0.25));

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
        } else if (pLivingEntity.verticalCollision || pLivingEntity.minorHorizontalCollision)
        {
            System.out.println("Collide");
            spawnHalberdLine(10, 10, 1.0F, 1.0F, 0.2F, 1, pLivingEntity, pLivingEntity.level, 5, 2);
        } else if (pLivingEntity.horizontalCollision)
        {
            pLivingEntity.removeEffect(this);
        }
        pLivingEntity.fallDistance = 0;

        if (pLivingEntity.level.isClientSide)
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
                pLivingEntity.level.addParticle(new RingParticle.RingData(yaw, pitch, 40, 0.337f, 0.925f, 0.8f, 1.0f, 50f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0, 0, 0);
                pLivingEntity.level.addParticle(new RingParticle.RingData(yaw2, pitch, 40, 0.337f, 0.925f, 0.8f, 1.0f, 50f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0, 0, 0);
            }
        }

        int yLevelStanding = Mth.floor(pLivingEntity.getY()) - 3;
        double headY = pLivingEntity.getY() + 2;
        float yawToRadians = (float) Math.toRadians(90 + pLivingEntity.getYRot());
        spawnHalberds(pLivingEntity.getX(), headY, pLivingEntity.getZ(), yLevelStanding, yawToRadians, 1, 5, pLivingEntity, pLivingEntity.level, 1);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }

    private void spawnHalberdLine(int numofBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay, LivingEntity caster, Level level, float damage, int spellLevel)
    {
        float angleIncrement = (float) (2 * Math.PI / numofBranches);

        for (int branch = 0; branch < numofBranches; ++branch)
        {
            //System.out.println("Spawn Halberds Field");
            float baseAngle = angleIncrement * branch;

            for (int i = 0; i < particlesPerBranch; ++i)
            {
                double currentRadius = initialRadius + i * radiusIncrement;
                float currentAngle = (float) (baseAngle + i * angleIncrement / initialRadius + (i * curveFactor));

                double offsetX = currentRadius * Math.cos(currentAngle);
                double offsetZ = currentRadius * Math.sin(currentAngle);

                double spawnX = caster.getX() + offsetX;
                double spawnY = caster.getY() + 0.3D;
                double spawnZ = caster.getZ() + offsetZ;

                int d1 = delay * (i + 1);

                double deltaX = level.random.nextGaussian() * 0.007D;
                double deltaY = level.random.nextGaussian() * 0.007D;
                double deltaZ = level.random.nextGaussian() * 0.007D;
                if (level.isClientSide)
                {
                    //System.out.println("Particles");
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }

                spawnHalberds(spawnX, spawnZ, caster.getY() - 5, caster.getY() + 3, currentAngle, d1, damage, caster, level, spellLevel);
            }
        }
    }

    public void spawnHalberds(double x, double z, double minY, double maxY, float rotation, int delay, float damage, LivingEntity caster, Level level, int spellLevel)
    {
        BlockPos pos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        int maxIterations = spellLevel * 4;
        int iterationCount = 0;

        do {
            //System.out.println("Trying to find block at pos: " + pos);

            BlockPos pos1 = pos.below();
            BlockState blockState = level.getBlockState(pos1);

            if (blockState.isFaceSturdy(level, pos1, Direction.UP)) {

                //System.out.println("Found a sturdy block at: " + pos1);

                if (!level.isEmptyBlock(pos)) {
                    BlockState blockState1 = level.getBlockState(pos);
                    VoxelShape shape = blockState1.getCollisionShape(level, pos);

                    if (!shape.isEmpty()) {
                        d0 = shape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            pos = pos.below();
            iterationCount++;

        } while (pos.getY() >= Mth.floor(minY) && iterationCount < maxIterations);

        if (flag) {
            //System.out.println("Actually Spawn Halberds at pos: " + pos);
            Phantom_Halberd_Entity phantomHalberd = new Phantom_Halberd_Entity(level, x, pos.getY() + d0, z, rotation, delay, caster, damage);
            level.addFreshEntity(phantomHalberd);
            //System.out.println("Halberd spawned successfully!");
        } /*else {
            System.out.println("Failed to find a valid position to spawn the halberd.");
        }*/
    }
}
