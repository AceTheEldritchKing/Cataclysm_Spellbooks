package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AbstractMagicProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blazing_aoe.BlazingAoE;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class HellishBladeProjectile extends AbstractMagicProjectile implements GeoAnimatable {
    private final AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);

    public HellishBladeProjectile(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.setNoGravity(true);
    }

    public HellishBladeProjectile(Level level, LivingEntity shooter)
    {
        this(CSEntityRegistry.HELLISH_BLADE_PROJECTILE.get(), level);
        setOwner(shooter);
    }

    @Override
    public void trailParticles() {
        Vec3 vec3 = this.position().subtract(getDeltaMovement());
        level().addParticle(ModParticle.TRAP_FLAME.get(), vec3.x, vec3.y, vec3.z, 0, 0, 0);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles
                (level(), ModParticle.TRAP_FLAME.get(), x, y, z, 5, 0, 0, 0, 1, true);
    }

    @Override
    public float getSpeed() {
        return 0.8f;
    }

    @Override
    public Optional<SoundEvent> getImpactSound() {
        return Optional.of(ModSounds.IGNIS_IMPACT.get());
    }

    @Override
    protected void doImpactSound(SoundEvent sound) {
        level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 1.5f, 1.0f);
    }

    @Override
    protected void onHitEntity(EntityHitResult pResult) {
        var target = pResult.getEntity();
        DamageSources.applyDamage(target, damage,
                SpellRegistries.HELLISH_BLADE.get().getDamageSource(this, getOwner()));
        if (target instanceof LivingEntity livingTarget)
        {
            livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 100, 0));
            livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), 60, 0));

            if (livingTarget instanceof Player playerTarget)
            {
                // Disable shield if blocking
                playerTarget.disableShield(true);
            }

            ScreenShake_Entity.ScreenShake(level(), livingTarget.position(), 20, 0.1F, 20, 40);
        }
        discard();
    }

    @Override
    protected void onHit(HitResult hitresult) {
        super.onHit(hitresult);
        createAoEField(hitresult.getLocation());

        discard();
    }

    public void createAoEField(Vec3 location)
    {
        if (!level().isClientSide)
        {
            BlazingAoE aoE = new BlazingAoE(level());
            aoE.setOwner(getOwner());
            aoE.setDuration(100);
            aoE.setDamage(0.5F);
            aoE.setRadius(3.0F);
            aoE.setCircular();
            aoE.moveTo(location);
            level().addFreshEntity(aoE);
        }
    }

    @Override
    public void registerControllers(software.bernie.geckolib.core.animation.AnimatableManager.ControllerRegistrar controllerRegistrar)  {
        // No animations
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
