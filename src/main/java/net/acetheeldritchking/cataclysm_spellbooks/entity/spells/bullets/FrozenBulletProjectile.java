package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.bullets;

import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockEntity;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Optional;

public class FrozenBulletProjectile extends AbstractMagicProjectile implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public double prevDeltaMovementX;
    public double prevDeltaMovementY;
    public double prevDeltaMovementZ;

    public FrozenBulletProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public FrozenBulletProjectile(Level level, LivingEntity shooter) {
        this(CSEntityRegistry.FROZEN_BULLET_PROJECTILE.get(), level);
        setOwner(shooter);
    }

    @Override
    public void trailParticles() {
        for (int i = 0; i < 5; i++)
        {
            double x = getX() + (1.5F * (this.random.nextFloat() - 0.5F));
            double y = getY() + (1.5F * (this.random.nextFloat() - 0.5F));
            double z = getZ() + (1.5F * (this.random.nextFloat() - 0.5F));

            this.level.addParticle(ParticleTypes.SNOWFLAKE, x, y, z, -getDeltaMovement().x, -getDeltaMovement().y, -getDeltaMovement().z);
        }
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles(level, ParticleTypes.SNOWFLAKE, x, y, z, 10, .1, .1, .1, .18, true);
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

        if (this.level.isClientSide)
        {
            double x = getX() + 1.5F * (this.random.nextFloat() - 0.5F);
            double y = getY() + 1.5F * (this.random.nextFloat() - 0.5F);
            double z = getZ() + 1.5F * (this.random.nextFloat() - 0.5F);

            float random = 0.04F;

            float r = 181/255F + this.random.nextFloat() * random * 1.5F;
            float g = 236/255F + this.random.nextFloat() * random;
            float b = 248/255F + this.random.nextFloat() * random;

            this.level.addParticle(new LightTrailParticle.OrbData(r, g, b, 0.1F, this.getBbHeight()/2, this.getId()), x, y, z, 0, 0, 0);
        }

        super.tick();
    }

    @Override
    public float getSpeed() {
        return 0.45F;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(SoundRegistry.ICE_IMPACT.get());
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        var target = pResult.getEntity();
        DamageSources.applyDamage(target, damage,
                SpellRegistries.CRYOPIERCER.get().getDamageSource(this, getOwner()));
        // Ignore i-frames
        pResult.getEntity().invulnerableTime = 0;

        Vec3 spawn = target.position();

        GlacialBlockEntity glacialBlock = new GlacialBlockEntity(this.level, (LivingEntity) this.getOwner());

        if (target instanceof LivingEntity livingTarget)
        {
            livingTarget.addEffect(new MobEffectInstance(CSPotionEffectRegistry.DISABLED_EFFECT.get(), 100, 0, true, true, true));
            livingTarget.addEffect(new MobEffectInstance(MobEffectRegistry.CHILLED.get(), 100, 1, true, true, true));

            glacialBlock.setDuration(15 * 20);
            glacialBlock.setTarget(livingTarget);
            glacialBlock.moveTo(spawn);
            level.addFreshEntity(glacialBlock);
            target.stopRiding();
            target.startRiding(glacialBlock, true);
        }

        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        discard();
    }

    @Override
    protected void onHit(HitResult hitresult) {
        super.onHit(hitresult);

        if (!this.level.isClientSide)
        {
            float radius = getExplosionRadius();
            var radiusSqr = radius * radius;
            var entities = level.getEntities(this, this.getBoundingBox().inflate(radius));
            Vec3 losPoint = Utils.raycastForBlock(level, this.position(), this.position().add(0, 2, 0), ClipContext.Fluid.NONE).getLocation();

            for (Entity entity : entities)
            {
                double distanceToSqr = entity.distanceToSqr(hitresult.getLocation());

                if (distanceToSqr < radiusSqr && canHitEntity(entity) && Utils.hasLineOfSight(level, losPoint, entity.getBoundingBox().getCenter(), true))
                {
                    double modifier = (1 - distanceToSqr / radiusSqr);
                    float damage = (float) (getDamage() * modifier);

                    ScreenShake_Entity.ScreenShake(level, entity.position(), 2.0F, 0.15F, 20, 20);

                    DamageSources.applyDamage(entity, damage, SpellRegistries.CRYOPIERCER.get().getDamageSource(this, getOwner()));
                }
            }

            MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.ICE.get().getTargetingColor(), this.getExplosionRadius() * 2),
                    getX(), getY(), getZ(),
                    1, 0, 0, 0, 0, false);

            if (hitresult instanceof EntityHitResult entityHitResult)
            {
                onHitEntity(entityHitResult);
            }

            discard();
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        //
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
