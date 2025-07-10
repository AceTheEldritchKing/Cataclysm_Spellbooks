package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.flash_bang;

import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class FlashBangProjectileEntity extends AbstractMagicProjectile implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    protected int effectDuration;

    public FlashBangProjectileEntity(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(false);
    }

    public FlashBangProjectileEntity(Level level, LivingEntity shooter) {
        this(CSEntityRegistry.FLASH_BANG_PROJECTILE.get(), level);
        setOwner(shooter);
    }

    @Override
    public boolean isNoGravity() {
        return false;
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = getDeltaMovement();
        double d0 = this.getX() - vec3.x;
        double d1 = this.getY() - vec3.y;
        double d2 = this.getZ() - vec3.z;
        var count = Mth.clamp((int) (vec3.lengthSqr() * 4), 1, 4);
        for (int i = 0; i < count; i++) {
            Vec3 random = Utils.getRandomVec3(.25);
            var f = i / ((float) count);
            var x = Mth.lerp(f, d0, this.getX());
            var y = Mth.lerp(f, d1, this.getY());
            var z = Mth.lerp(f, d2, this.getZ());
            this.level().addParticle(ParticleTypes.LARGE_SMOKE, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
            this.level().addParticle(ParticleHelper.EMBERS, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
        }
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles
                (this.level(), ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, x, y, z, 15, 0, 0, 0, 1, true);
    }

    @Override
    public float getSpeed() {
        return 1.15F;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(SoundEvents.GENERIC_EXPLODE);
    }

    @Override
    protected void onHit(HitResult hitresult) {
        super.onHit(hitresult);
        if (!this.level().isClientSide)
        {
            impactParticles(xOld, yOld, zOld);

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
                    int duration = (int) (getEffectDuration() * modifier);
                    float damage = (float) (this.damage * modifier);

                    DamageSources.applyDamage(entity, damage, SpellRegistries.FLASH_BANG.get().getDamageSource(this, getOwner()));

                    if (entity instanceof LivingEntity livingEntity)
                    {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, duration, 0, true, true, true));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, duration, 0, true, true, true));
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, duration, 0, true, true, true));
                    }
                }
            }

            MagicManager.spawnParticles(this.level(), new BlastwaveParticleOptions(CSSchoolRegistry.TECHNOMANCY.get().getTargetingColor(), radius * 2),
                    getX(), getY(), getZ(),
                    1, 0, 0, 0, 0, false);
            playSound(SoundEvents.GENERIC_EXPLODE, 4.0F, (1.0F + (this.level().random.nextFloat() - this.level().random.nextFloat()) * 0.2F) * 0.7F);
            this.discard();
        }
    }

    public int getEffectDuration()
    {
        return effectDuration;
    }

    public void setEffectDuration(int amount)
    {
        this.effectDuration = amount * 20;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.effectDuration = tag.getInt("Duration");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.putInt("Duration", this.getEffectDuration());
    }
}
