package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.parting_shot;

import com.github.L_Ender.cataclysm.client.particle.TrackLightningParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.no_man_zone.NoManZoneAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class PartingShotProjectile extends AbstractMagicProjectile implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public PartingShotProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public PartingShotProjectile(Level level, LivingEntity shooter) {
        this(CSEntityRegistry.PARTING_SHOT_PROJECTILE.get(), level);
        setOwner(shooter);
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = this.position().subtract(getDeltaMovement());
        this.level().addParticle(new TrackLightningParticle.OrbData(232, 59, 59), vec3.x, vec3.y, vec3.z, vec3.x, vec3.y, vec3.z);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        //MagicManager.spawnParticles
                //(level, new TrackLightningParticle.OrbData(255, 6, 62), x, y, z, 3, 0, 0, 0, 0.5, true);
    }

    @Override
    public void travel() {
        this.setPos(this.position().add(this.getDeltaMovement()));
        if (!this.isNoGravity())
        {
            Vec3 vec3 = this.getDeltaMovement();
            this.setDeltaMovement(vec3.x, vec3.y - 0.05000000074505806, vec3.z);
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void tick() {
        Vec3 deltaMovement = getDeltaMovement();
        double distance = deltaMovement.horizontalDistance();

        Vec3 arcVec;

        double x = deltaMovement.x;
        double y = deltaMovement.y;
        double z = deltaMovement.z;

        setYRot((float) (Mth.atan2(x, z) * (180 / Math.PI)));
        setXRot((float) (Mth.atan2(y, distance) * (180 / Math.PI)));
        setXRot(lerpRotation(xRotO, getXRot()));
        setYRot(lerpRotation(yRotO, getYRot()));

        Vec3 center = this.position().add(deltaMovement);
        arcVec = center.add(new Vec3((this.random.nextFloat() - 0.5F), (this.random.nextFloat() - 0.5F), (this.random.nextFloat() - 0.5F)));

        //this.level.addParticle(new TrackLightningParticle.OrbData(255, 6, 62), arcVec.x, arcVec.y, arcVec.z, 0, 0, 0);

        this.level().addParticle(new TrackLightningParticle.OrbData(232, 59, 59), center.x, center.y, center.z, arcVec.x, arcVec.y, arcVec.z);

        super.tick();
    }

    @Override
    public float getSpeed() {
        return 0.45F;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.empty();
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        var target = pResult.getEntity();
        DamageSources.applyDamage(target, damage,
                SpellRegistries.PARTING_SHOT.get().getDamageSource(this, getOwner()));
        // Ignore i-frames
        pResult.getEntity().invulnerableTime = 0;

        int i = target.getRemainingFireTicks();
        target.setSecondsOnFire(5);

        if (target instanceof LivingEntity livingTarget)
        {
            livingTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0, true, true, true));
            livingTarget.addEffect(new MobEffectInstance(CSPotionEffectRegistry.DISABLED_EFFECT.get(), 100, 0, true, true, true));
        }

        if (!target.hurt(SpellRegistries.PARTING_SHOT.get().getDamageSource(this, getOwner()), this.getDamage()))
        {
            target.setRemainingFireTicks(i);
        }

        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (!this.level().isClientSide)
        {
            Entity entity = this.getOwner();
            BlockPos pos;

            if (CSConfig.doSpellGriefing.get())
            {
                pos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level().isEmptyBlock(pos))
                {
                    this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
                }
            } else if (!(entity instanceof Mob) || ForgeEventFactory.getMobGriefingEvent(this.level(), entity))
            {
                pos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level().isEmptyBlock(pos))
                {
                    this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
                }
            }
        }

        discard();
    }

    @Override
    protected void onHit(HitResult hitresult) {
        super.onHit(hitresult);
        if (!this.level().isClientSide)
        {
            float radius = getExplosionRadius();
            var radiusSqr = radius * radius;
            var entities = level().getEntities(this, this.getBoundingBox().inflate(radius));
            Vec3 losPoint = Utils.raycastForBlock(this.level(), this.position(), this.position().add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();

            for (Entity entity : entities)
            {
                double distanceToSqr = entity.distanceToSqr(hitresult.getLocation());

                if (distanceToSqr < radiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(this.level(), losPoint, entity.getBoundingBox().getCenter(), true))
                {
                    double modifier = (1 - distanceToSqr / radiusSqr);
                    float damage = (float) (getDamage() * modifier);

                    ScreenShake_Entity.ScreenShake(this.level(), entity.position(), 5.0F, 0.15F, 20, 20);

                    DamageSources.applyDamage(entity, damage, SpellRegistries.PARTING_SHOT.get().getDamageSource(this, getOwner()));
                }
            }

            if (CSConfig.doSpellGriefing.get())
            {
                // EXPLOSION
                Explosion explosion = new Explosion(this.level(), null, SpellRegistries.PARTING_SHOT.get().getDamageSource(this, getOwner()), null, this.getX(), this.getY(), this.getZ(), this.getExplosionRadius() / 2, true, Explosion.BlockInteraction.DESTROY);
                if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(this.level(), explosion)) {
                    explosion.explode();
                    explosion.finalizeExplosion(false);
                }
            }

            // I just want red, man
            MagicManager.spawnParticles(this.level(), new BlastwaveParticleOptions(SchoolRegistry.FIRE.get().getTargetingColor(), this.getExplosionRadius() * 2),
                    getX(), getY(), getZ(),
                    1, 0, 0, 0, 0, false);

            if (hitresult instanceof EntityHitResult entityHitResult)
            {
                onHitEntity(entityHitResult);
            }

            createAoEField(hitresult.getLocation());

            discard();
        }
    }

    public void createAoEField(Vec3 location)
    {
        if (!this.level().isClientSide)
        {
            NoManZoneAoE aoE = new NoManZoneAoE(this.level());
            aoE.setOwner(getOwner());
            aoE.setDuration(200);
            aoE.setDamage(5.5F);
            aoE.setRadius(6.0F);
            aoE.setCircular();
            aoE.moveTo(location);
            this.level().addFreshEntity(aoE);
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }
}
