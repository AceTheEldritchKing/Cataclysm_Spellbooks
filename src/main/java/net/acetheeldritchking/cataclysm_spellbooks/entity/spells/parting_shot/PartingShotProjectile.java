package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.parting_shot;

import com.github.L_Ender.cataclysm.client.particle.TrackLightningParticle;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
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
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.ForgeEventFactory;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Optional;

public class PartingShotProjectile extends AbstractMagicProjectile implements IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

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
        level.addParticle(new TrackLightningParticle.OrbData(232, 59, 59), vec3.x, vec3.y, vec3.z, vec3.x, vec3.y, vec3.z);
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

        this.level.addParticle(new TrackLightningParticle.OrbData(232, 59, 59), center.x, center.y, center.z, arcVec.x, arcVec.y, arcVec.z);

        super.tick();
    }

    @Override
    public float getSpeed() {
        return 0.3F;
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

        int i = target.getRemainingFireTicks();
        target.setSecondsOnFire(5);

        if (target instanceof LivingEntity livingTarget)
        {
            livingTarget.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 0));
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

        if (!this.level.isClientSide)
        {
            Entity entity = this.getOwner();
            BlockPos pos;

            if (CSConfig.doSpellGriefing.get())
            {
                pos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level.isEmptyBlock(pos))
                {
                    this.level.setBlockAndUpdate(pos, BaseFireBlock.getState(this.level, pos));
                }
            } else if (!(entity instanceof Mob) || ForgeEventFactory.getMobGriefingEvent(this.level, entity))
            {
                pos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level.isEmptyBlock(pos))
                {
                    this.level.setBlockAndUpdate(pos, BaseFireBlock.getState(this.level, pos));
                }
            }
        }

        discard();
    }

    @Override
    protected void onHit(HitResult hitresult) {
        if (!this.level.isClientSide)
        {
            if (CSConfig.doSpellGriefing.get())
            {
                // EXPLOSION
                Explosion explosion = new Explosion(level, null, SpellRegistry.FIREBALL_SPELL.get().getDamageSource(this, getOwner()), null, this.getX(), this.getY(), this.getZ(), this.getExplosionRadius() / 2, true, Explosion.BlockInteraction.DESTROY);
                if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(level, explosion)) {
                    explosion.explode();
                    explosion.finalizeExplosion(false);
                }
            }

            // I just want red, man
            MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.BLOOD.get().getTargetingColor(), this.getExplosionRadius() * 2),
                    getX(), getY(), getZ(),
                    1, 0, 0, 0, 0, false);

            discard();
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        // no animations
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
