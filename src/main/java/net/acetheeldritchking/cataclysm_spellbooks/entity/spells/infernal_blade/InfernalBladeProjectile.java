package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class InfernalBladeProjectile extends AbstractMagicProjectile implements GeoAnimatable {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public InfernalBladeProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public InfernalBladeProjectile(Level level, LivingEntity shooter)
    {
        this(CSEntityRegistry.INFERNAL_BLADE_PROJECTILE.get(), level);
        setOwner(shooter);
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

        double x = deltaMovement.x;
        double y = deltaMovement.y;
        double z = deltaMovement.z;

        setYRot((float) (Mth.atan2(x, z) * (180 / Math.PI)));
        setXRot((float) (Mth.atan2(y, distance) * (180 / Math.PI)));
        setXRot(lerpRotation(xRotO, getXRot()));
        setYRot(lerpRotation(yRotO, getYRot()));
        super.tick();
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = this.position().subtract(getDeltaMovement());
        level().addParticle(ParticleHelper.EMBERS, vec3.x, vec3.y, vec3.z, 0, 0, 0);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles
                (level(), ModParticle.TRAP_FLAME.get(), x, y, z, 5, 0, 0, 0, 1, true);
    }

    @Override
    public float getSpeed() {
        return 0.7f;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(ModSounds.IGNIS_POKE.get());
    }

    @Override
    protected void doImpactSound(SoundEvent sound) {
        level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 1.5f, 0.5f);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        var target = pResult.getEntity();
        DamageSources.applyDamage(target, damage,
                SpellRegistries.INFERNAL_STRIKE.get().getDamageSource(this, getOwner()));
        if (target instanceof LivingEntity livingTarget)
        {
            livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 100, 0));
        }
        discard();
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);
        discard();
    }

    // Geckolib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        AnimationController<InfernalBladeProjectile> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.add(controller);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        RawAnimation builder = RawAnimation.begin();
        builder.thenLoop("animation.infernal_blade_small.idle");
        event.getController().setAnimation(builder);

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }
}
