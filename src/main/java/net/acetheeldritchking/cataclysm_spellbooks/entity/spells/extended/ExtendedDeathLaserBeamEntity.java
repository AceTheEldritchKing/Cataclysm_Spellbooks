package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.extended;

import com.github.L_Ender.cataclysm.client.tool.ControlledAnimation;
import com.github.L_Ender.cataclysm.entity.projectile.Death_Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ExtendedDeathLaserBeamEntity extends Death_Laser_Beam_Entity {
    private Vec3[] attractorPos;

    public ExtendedDeathLaserBeamEntity(EntityType<? extends ExtendedDeathLaserBeamEntity> type, Level world) {
        super(type, world);
        this.appear = new ControlledAnimation(3);
        this.on = true;
        this.blockSide = null;
        this.noCulling = true;
        if (world.isClientSide) {
            this.attractorPos = new Vec3[]{new Vec3(0.0, 0.0, 0.0)};
        }
    }

    public ExtendedDeathLaserBeamEntity(EntityType<? extends ExtendedDeathLaserBeamEntity> type, Level world, LivingEntity caster, double x, double y, double z, float yaw, float pitch, int duration, float damage, float Hpdamage) {
        super(type, world);
        this.caster = caster;
        this.setYaw(yaw);
        this.setPitch(pitch);
        this.setDuration(duration);
        this.setPos(x, y, z);
        this.setDamage(damage);
        this.setHpDamage(Hpdamage);
        this.calculateEndPos();
        if (!world.isClientSide) {
            this.setCasterID(caster.getId());
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.tickCount > 20)
        {
            this.calculateEndPos();

            Vec3 laserVec3 = new Vec3(this.getX(), this.getY(), this.getZ());
            Vec3 endPosVec3 = new Vec3(this.endPosX, this.endPosY, this.endPosZ);

            List<LivingEntity> hit = this.extendedLaserbeamHitResult(this.level(), laserVec3, endPosVec3, false, true, true).entities;

            if (!this.level().isClientSide)
            {
                for (LivingEntity entity : hit)
                {
                    if (this.caster != null && !this.caster.isAlliedTo(entity) && entity != this.caster)
                    {
                        DamageSources.applyDamage(entity, getDamage(),
                                SpellRegistries.ATOMIC_LASER.get().getDamageSource(this, this.caster));

                        boolean flag = entity.hurt(CMDamageTypes.causeDeathLaserDamage(this, this.caster), (float)((double)this.getDamage() + Math.min(this.getDamage(), (entity.getMaxHealth() * this.getHpDamage()) * 0.01)));

                        if (this.getFire() && flag)
                        {
                            entity.setSecondsOnFire(5);
                        }
                    }
                }
            }
        }
    }

    private void calculateEndPos() {
        if (this.level().isClientSide()) {
            this.endPosX = this.getX() + 30.0 * Math.cos(this.renderYaw) * Math.cos(this.renderPitch);
            this.endPosZ = this.getZ() + 30.0 * Math.sin(this.renderYaw) * Math.cos(this.renderPitch);
            this.endPosY = this.getY() + 30.0 * Math.sin(this.renderPitch);
        } else {
            this.endPosX = this.getX() + 30.0 * Math.cos(this.getYaw()) * Math.cos(this.getPitch());
            this.endPosZ = this.getZ() + 30.0 * Math.sin(this.getYaw()) * Math.cos(this.getPitch());
            this.endPosY = this.getY() + 30.0 * Math.sin(this.getPitch());
        }

    }

    public ExtendedLaserbeamHitResult extendedLaserbeamHitResult(Level level, Vec3 from, Vec3 to, boolean stopOnLiquid, boolean ignoreBlockWithoutBB, boolean returnLastUncollidableBlock)
    {
        ExtendedLaserbeamHitResult result = new ExtendedLaserbeamHitResult();

        result.setBlockHitResult(level.clip(new ClipContext(from, to, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, this)));

        if (result.blockHitResult != null)
        {
            Vec3 hit = result.blockHitResult.getLocation();

            this.collidePosX = hit.x;
            this.collidePosY = hit.y;
            this.collidePosZ = hit.z;

            this.blockSide = result.blockHitResult.getDirection();
        }
        else {
            this.collidePosX = this.endPosX;
            this.collidePosY = this.endPosY;
            this.collidePosZ = this.endPosZ;

            this.blockSide = null;
        }

        AABB collision = new AABB(Math.min(this.getX(), this.collidePosX), Math.min(this.getY(), this.collidePosY), Math.min(this.getZ(), this.collidePosZ), Math.max(this.getX(), this.collidePosX), Math.max(this.getY(), this.collidePosY), Math.max(this.getZ(), this.collidePosZ)).inflate(1, 1, 1);

        List<LivingEntity> entities = level.getEntitiesOfClass(LivingEntity.class, collision);

        for (LivingEntity entity : entities)
        {
            if (entity != this.caster)
            {
                float padding = entity.getPickRadius() + 0.5F;
                AABB entityBB = entity.getBoundingBox().inflate(padding, padding, padding);
                Optional<Vec3> hitLocation = entityBB.clip(from, to);

                if (entityBB.contains(from))
                {
                    result.addEntityHit(entity);
                } else if (hitLocation.isPresent())
                {
                    result.addEntityHit(entity);
                }
            }
        }

        return result;
    }

    public static class ExtendedLaserbeamHitResult
    {
        protected BlockHitResult blockHitResult;
        protected final List<LivingEntity> entities = new ArrayList<>();

        public ExtendedLaserbeamHitResult()
        {
            // Empty constructor here
        }

        public BlockHitResult getBlockHitResult()
        {
            return this.blockHitResult;
        }

        public void setBlockHitResult(HitResult blockHitResult)
        {
            if (blockHitResult.getType() == HitResult.Type.BLOCK)
            {
                this.blockHitResult = (BlockHitResult) blockHitResult;
            }
        }

        public void addEntityHit(LivingEntity entity)
        {
            this.entities.add(entity);
        }
    }
}
