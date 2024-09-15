package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs;

import com.github.L_Ender.cataclysm.init.ModEffect;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.object.PlayState;
import software.bernie.geckolib.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class SummonedAbyssalGnawer extends Monster implements MagicSummon, GeoAnimatable {
    AnimatableInstanceCache factory = GeckoLibUtil.createInstanceCache(this);
    protected LivingEntity cachedSummoner;
    protected UUID summonerUUID;

    public SummonedAbyssalGnawer(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        xpReward = 0;
    }

    public SummonedAbyssalGnawer(Level level, LivingEntity owner)
    {
        this(CSEntityRegistry.ABYSSAL_GNAWERS.get(), level);
        setSummoner(owner);
    }

    public static AttributeSupplier setAttributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 16.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0f)
                .add(Attributes.ATTACK_SPEED, 0.8f)
                .add(Attributes.MOVEMENT_SPEED, 0.5f)
                .add(Attributes.FLYING_SPEED, 0.2D)
                .build();
    }

    // Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5f, true));
        this.goalSelector.addGoal(4, new AbyssalGnawerSwimGoal(this));
        this.goalSelector.addGoal(5, new GenericFollowOwnerGoal(this, this::getSummoner, .3f, 10, 2, true, 50));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 9.0F));

        this.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(4, (new GenericHurtByTargetGoal(this, (entity) -> entity == getSummoner())).setAlertOthers());
    }

    @Override
    public LivingEntity getSummoner() {
        return OwnerHelper.getAndCacheOwner(level(), cachedSummoner, summonerUUID);
    }

    public void setSummoner(@Nullable LivingEntity owner)
    {
        if (owner != null)
        {
            this.summonerUUID = owner.getUUID();
            this.cachedSummoner = owner;
        }
    }

    // Movement
    @Override
    public void tick() {
        if (isInWater())
        {
            // Should be faster in water
            float baseSpeed = getSpeed();
            setSpeed(baseSpeed * 2);
        }
        super.tick();
    }

    /*@Override
    public boolean isNoGravity() {
        return true;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(@NotNull Level level)
    {
        //CreatureWaterPathNavigation creatureWaterPathNavigation = new CreatureWaterPathNavigation(this, level)
        FlyingPathNavigation flyingPathNavigation = new FlyingPathNavigation(this, level)
        {
            public void tick()
            {
                super.tick();
            }
        };
        flyingPathNavigation.setCanFloat(true);
        flyingPathNavigation.setCanPassDoors(false);
        flyingPathNavigation.setCanOpenDoors(false);
        return flyingPathNavigation;
    }*/

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    @Override
    public void travel(Vec3 pTravelVector) {
        if (this.isEffectiveAi() && this.isInWater())
        {
            this.moveRelative(0.01F, pTravelVector);
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.setDeltaMovement(this.getDeltaMovement().scale(0.9D));
            if (this.getTarget() == null)
            {
                this.setDeltaMovement(this.getDeltaMovement().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(pTravelVector);
        }
    }

    @Override
    public double getTick(Object o) {
        return 0;
    }

    static class AbyssalGnawerSwimGoal extends RandomSwimmingGoal
    {
        AbyssalGnawerSwimGoal(SummonedAbyssalGnawer abyssalGnawer) {
            super(abyssalGnawer, 1.0D, 40);
        }
    }

    // Attacks and Death
    @Override
    public void die(DamageSource pDamageSource) {
        this.onDeathHelper();
        super.die(pDamageSource);
    }

    @Override
    public void onRemovedFromWorld() {
        this.onRemovedHelper(this, CSPotionEffectRegistry.ABYSSAL_GNAWER_TIMER.get());
        super.onRemovedFromWorld();
    }

    @Override
    public void onUnSummon() {
        if (!level().isClientSide)
        {
            MagicManager.spawnParticles(level(), ParticleTypes.POOF,
                    getX(), getY(), getZ(),
                    25, 0.4, 0.8, 0.4, 0.03, false);
            discard();
        }
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (!super.doHurtTarget(pEntity))
        {
            return false;
        }
        else
        {
            if (pEntity instanceof LivingEntity entity)
            {
                playSound(SoundEvents.EVOKER_FANGS_ATTACK, 0.5f, 1.5f);
                entity.addEffect(new MobEffectInstance(ModEffect.EFFECTABYSSAL_FEAR.get(),
                        60, 0));
            }
            return Utils.doMeleeAttack(this, pEntity, SpellRegistries.CONJURE_ABYSSAL_GNAWERS.get().getDamageSource(this, getSummoner()));
        }
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (shouldIgnoreDamage(pSource))
        {
            return false;
        }
        return super.hurt(pSource, pAmount);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource pDamageSource) {
        return SoundEvents.PUFFER_FISH_HURT;
    }

    @org.jetbrains.annotations.Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.SALMON_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.PUFFER_FISH_DEATH;
    }

    @Override
    protected SoundEvent getSwimSound() {
        return SoundEvents.FISH_SWIM;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar data) {
        AnimationController<SummonedAbyssalGnawer> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.add(controller);
    }

    private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
        RawAnimation builder = RawAnimation.begin();

        if (this.swinging) {
            builder.thenPlay("animation.cataclysm_spellbooks:abyssal_gnawers.attack");
        }
        else if (event.isMoving()) {
            builder.thenLoop("animation.cataclysm_spellbooks:abyssal_gnawers.swim");
        } else {
            builder.thenLoop("animation.cataclysm_spellbooks:abyssal_gnawers.idle");
        }

        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return factory;
    }

    // NBT
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.summonerUUID = OwnerHelper.deserializeOwner(pCompound);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        OwnerHelper.serializeOwner(pCompound, summonerUUID);
    }
}