package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.bullets;

import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.scorched_earth_aoe.ScorchedEarthAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class MoltenBulletProjectile extends AbstractMagicProjectile implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    public double prevDeltaMovementX;
    public double prevDeltaMovementY;
    public double prevDeltaMovementZ;

    public MoltenBulletProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public MoltenBulletProjectile(Level level, LivingEntity shooter) {
        this(CSEntityRegistry.MOLTEN_BULLET_PROJECTILE.get(), level);
        setOwner(shooter);
    }

    @Override
    public void trailParticles() {
        for (int i = 0; i < 5; i++)
        {
            double x = getX() + (1.5F * (this.random.nextFloat() - 0.5F));
            double y = getY() + (1.5F * (this.random.nextFloat() - 0.5F));
            double z = getZ() + (1.5F * (this.random.nextFloat() - 0.5F));

            this.level().addParticle(ParticleTypes.FLAME, x, y, z, -getDeltaMovement().x, -getDeltaMovement().y, -getDeltaMovement().z);
        }
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(this.level(), ModParticle.FLARE_EXPLODE.get(), x, y, z, 10, .1, .1, .1, .18, true);
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
    public void tick() {
        this.prevDeltaMovementX = getDeltaMovement().x;
        this.prevDeltaMovementY = getDeltaMovement().y;
        this.prevDeltaMovementZ = getDeltaMovement().z;

        setYRot(-((float) Mth.atan2(getDeltaMovement().x, getDeltaMovement().z)) * (180F / (float)Math.PI));

        if (this.level().isClientSide)
        {
            double x = getX() + 1.5F * (this.random.nextFloat() - 0.5F);
            double y = getY() + 1.5F * (this.random.nextFloat() - 0.5F);
            double z = getZ() + 1.5F * (this.random.nextFloat() - 0.5F);

            float random = 0.04F;

            float r = 195/255F + this.random.nextFloat() * random * 1.5F;
            float g = 95/255F + this.random.nextFloat() * random;
            float b = 3/255F + this.random.nextFloat() * random;

            this.level().addParticle(new LightTrailParticle.OrbData(r, g, b, 0.1F, this.getBbHeight()/2, this.getId()), x, y, z, 0, 0, 0);
        }

        super.tick();
    }

    @Override
    public float getSpeed() {
        return 0.45F;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(SoundEvents.GENERIC_EXPLODE);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        var target = pResult.getEntity();
        DamageSources.applyDamage(target, damage,
                SpellRegistries.SCORCHED_EARTH.get().getDamageSource(this, getOwner()));
        // Ignore i-frames
        pResult.getEntity().invulnerableTime = 0;

        // More fire
        int i = target.getRemainingFireTicks();
        target.setSecondsOnFire(25);

        if (target instanceof LivingEntity livingTarget)
        {
            livingTarget.addEffect(new MobEffectInstance(CSPotionEffectRegistry.DISABLED_EFFECT.get(), 100, 0, true, true, true));
        }

        if (!target.hurt(SpellRegistries.SCORCHED_EARTH.get().getDamageSource(this, getOwner()), this.getDamage()))
        {
            target.setRemainingFireTicks(i);
        }

        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
       if (!this.level().isClientSide)
       {
           spawnXFlameJet(10, 2.0);
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
            var entities = this.level().getEntities(this, this.getBoundingBox().inflate(radius));
            Vec3 losPoint = Utils.raycastForBlock(this.level(), this.position(), this.position().add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();

            for (Entity entity : entities)
            {
                double distanceToSqr = entity.distanceToSqr(hitresult.getLocation());

                if (distanceToSqr < radiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(this.level(), losPoint, entity.getBoundingBox().getCenter(), true))
                {
                    double modifier = (1 - distanceToSqr / radiusSqr);
                    float damage = (float) (getDamage() * modifier);

                    ScreenShake_Entity.ScreenShake(this.level(), entity.position(), 2.0F, 0.15F, 20, 20);

                    DamageSources.applyDamage(entity, damage, SpellRegistries.SCORCHED_EARTH.get().getDamageSource(this, getOwner()));
                }
            }

            MagicManager.spawnParticles(this.level(), new BlastwaveParticleOptions(SchoolRegistry.FIRE.get().getTargetingColor(), this.getExplosionRadius() * 2),
                    getX(), getY(), getZ(),
                    1, 0, 0, 0, 0, false);

            if (hitresult instanceof EntityHitResult entityHitResult)
            {
                onHitEntity(entityHitResult);
            }

            createAoEField(hitresult.getLocation());
            createQuakeAoEField(hitresult.getLocation());

            discard();
        }
    }

    public void createAoEField(Vec3 location)
    {
        if (!this.level().isClientSide)
        {
            ScorchedEarthAoE aoE = new ScorchedEarthAoE(this.level());
            aoE.setOwner(getOwner());
            aoE.setDuration(200);
            aoE.setDamage(5.5F);
            aoE.setRadius(3.5F);
            aoE.setCircular();
            aoE.moveTo(location);
            this.level().addFreshEntity(aoE);
        }
    }

    public void createQuakeAoEField(Vec3 location)
    {
        if (!this.level().isClientSide)
        {
            EarthquakeAoe aoE = new EarthquakeAoe(this.level());
            aoE.setOwner(getOwner());
            aoE.setDuration(50);
            aoE.setDamage(0);
            aoE.setRadius(1.5F);
            aoE.setSlownessAmplifier(0);
            aoE.setCircular();
            aoE.moveTo(location);
            this.level().addFreshEntity(aoE);
        }
    }

    public void spawnXFlameJet(int rune, double time)
    {
        for (int i = 0; i < 4; i++)
        {
            float yawRadians = (float) Math.toRadians(45.0F + this.getYRot());
            float throwAngle = (float) (yawRadians + i * Math.PI / 2.0F);

            for(int k = 0; k < rune; ++k) {
                double d2 = 0.8 * (k + 1);
                int d3 = (int) (time * (k + 1));
                CSUtils.spawnFlameJets(this.level(), this.getX() + (double)Mth.cos(throwAngle) * 1.25 * d2, this.getZ() + (double)Mth.sin(throwAngle) * 1.25 * d2, this.getY() - 2.0, this.getY() + 2.0, throwAngle, d3, (LivingEntity) getOwner(), this.getDamage());
            }
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
