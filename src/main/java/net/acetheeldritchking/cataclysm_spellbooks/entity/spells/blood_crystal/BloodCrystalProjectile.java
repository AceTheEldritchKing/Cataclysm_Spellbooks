package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blood_crystal;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class BloodCrystalProjectile extends AbstractMagicProjectile implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);

    public BloodCrystalProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public BloodCrystalProjectile(Level level, LivingEntity shooter) {
        this(CSEntityRegistry.BLOOD_CRYSTAL_PROJECTILE.get(), level);
        setOwner(shooter);
    }

    @Override
    public void trailParticles() {
        for (int i = 0; i < 4; i++)
        {
            double speed = 0.05F;
            double x = Utils.random.nextDouble() * 2 * speed - speed;
            double y = Utils.random.nextDouble() * 2 * speed - speed;
            double z = Utils.random.nextDouble() * 2 * speed - speed;

            this.level().addParticle(ParticleHelper.BLOOD, this.getX() + x, this.getY() + y, this.getZ() + z, x, y ,z);
        }
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(this.level(), ParticleHelper.BLOOD, x, y, z, 15, .1, .1, .1, .18, true);
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
    public float getSpeed() {
        return 1.5F;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(SoundRegistry.BLOOD_EXPLOSION.get());
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        var target = pResult.getEntity();
        DamageSources.applyDamage(target, damage,
                SpellRegistries.HEMORRHAGING_IMPACT.get().getDamageSource(this, getOwner()));
        // Ignore i-frames
        pResult.getEntity().invulnerableTime = 0;

        if (target instanceof LivingEntity livingTarget)
        {
            livingTarget.addEffect(new MobEffectInstance(CSPotionEffectRegistry.HEMOPHILIA_EFFECT.get(), 100, 0, true, true, true));
            livingTarget.addEffect(new MobEffectInstance(CSPotionEffectRegistry.DISABLED_EFFECT.get(), 100, 0, true, true, true));
        }
    }

    @Override
    protected void onHit(HitResult hitresult) {
        super.onHit(hitresult);

        if (hitresult instanceof EntityHitResult entityHitResult)
        {
            onHitEntity(entityHitResult);
        }

        if (!this.level().isClientSide)
        {
            float radius = getExplosionRadius();
            var radiusSqr = radius * radius;
            var entities = this.level().getEntities(this, this.getBoundingBox().inflate(radius));
            Vec3 losPoint = Utils.raycastForBlock(this.level(), this.position(), this.position().add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();

            for (Entity entity : entities)
            {
                double distanceToSqr = entity.distanceToSqr(hitresult.getLocation());

                if (distanceToSqr < radiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(this.level(), losPoint, entity.getBoundingBox().getCenter(), true))
                {
                    double modifier = (1 - distanceToSqr / radiusSqr);
                    float damage = (float) (getDamage() * modifier);

                    ScreenShake_Entity.ScreenShake(this.level(), entity.position(), 5.0F, 0.15F, 20, 20);

                    if (entity instanceof LivingEntity livingTarget)
                    {
                        livingTarget.addEffect(new MobEffectInstance(CSPotionEffectRegistry.HEMOPHILIA_EFFECT.get(), 100, 0, true, true, true));
                    }

                    DamageSources.applyDamage(entity, damage, SpellRegistries.HEMORRHAGING_IMPACT.get().getDamageSource(this, getOwner()));
                }
            }

            MagicManager.spawnParticles(this.level(), new BlastwaveParticleOptions(SchoolRegistry.BLOOD.get().getTargetingColor(), 4),
                    getX(), getY(), getZ(),
                    1, 0, 0, 0, 0, false);
        }

        discard();
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
