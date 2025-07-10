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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
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
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.Optional;

public class HellishBladeProjectile extends AbstractMagicProjectile implements GeoEntity {
    private final AnimatableInstanceCache geoCache = GeckoLibUtil.createInstanceCache(this);
    private static final EntityDataAccessor<Boolean> SOUL;

    static {
        SOUL = SynchedEntityData.defineId(HellishBladeProjectile.class, EntityDataSerializers.BOOLEAN);
    }

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
        this.level().addParticle(ModParticle.TRAP_FLAME.get(), vec3.x, vec3.y, vec3.z, 0, 0, 0);
    }

    @Override
    public void impactParticles(double x, double y, double z) {
        MagicManager.spawnParticles
                (this.level(), ModParticle.TRAP_FLAME.get(), x, y, z, 5, 0, 0, 0, 1, true);
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
        this.level().playSound(null, getX(), getY(), getZ(), sound, SoundSource.NEUTRAL, 1.5f, 1.0f);
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

            ScreenShake_Entity.ScreenShake(this.level(), livingTarget.position(), 20, 0.1F, 20, 40);
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
        if (!this.level().isClientSide)
        {
            BlazingAoE aoE = new BlazingAoE(this.level());
            aoE.setOwner(getOwner());
            aoE.setDuration(100);
            aoE.setDamage(0.5F);
            aoE.setRadius(3.0F);
            aoE.setCircular();
            aoE.moveTo(location);
            this.level().addFreshEntity(aoE);
        }
    }

    public boolean getIsSoul()
    {
        return this.entityData.get(SOUL);
    }

    public void setIsSoul(boolean soul)
    {
        this.entityData.set(SOUL, soul);
    }

    // Geckolib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        //
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return geoCache;
    }

    // NBT
    @Override
    protected void defineSynchedData() {
        this.entityData.define(SOUL, false);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.setIsSoul(pCompound.getBoolean("Soul"));
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putBoolean("Soul", this.getIsSoul());
    }
}
