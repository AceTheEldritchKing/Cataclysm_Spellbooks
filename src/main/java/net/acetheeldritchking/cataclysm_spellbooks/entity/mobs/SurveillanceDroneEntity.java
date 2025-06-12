package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs;

import com.github.L_Ender.cataclysm.client.particle.TrackLightningParticle;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.UUID;

public class SurveillanceDroneEntity extends Monster implements MagicSummon, IAnimatable {
    AnimationFactory factory = GeckoLibUtil.createFactory(this);
    protected LivingEntity cachedSummoner;
    protected UUID summonerUUID;
    protected int healingAmount;

    public SurveillanceDroneEntity(EntityType<? extends Monster> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public SurveillanceDroneEntity(Level level, LivingEntity owner)
    {
        this(CSEntityRegistry.SURVEILLANCE_DRONE.get(), level);
        setSummoner(owner);
    }

    public static AttributeSupplier setAttributes()
    {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.ATTACK_DAMAGE, 0.0f)
                .add(Attributes.ATTACK_SPEED, 0.0f)
                .add(Attributes.MOVEMENT_SPEED, 1.5f)
                .add(Attributes.FLYING_SPEED, 1.5D)
                .build();
    }

    // Goals
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(5, new GenericFollowOwnerGoal(this, this::getSummoner, .3f, 10, 2, true, 50));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, 4.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 9.0F));

        this.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(4, (new GenericHurtByTargetGoal(this, (entity) -> entity == getSummoner())).setAlertOthers());
    }

    @Override
    public boolean isAlliedTo(Entity pEntity) {
        return super.isAlliedTo(pEntity) || this.isAlliedHelper(pEntity);
    }

    @Override
    public LivingEntity getSummoner() {
        return OwnerHelper.getAndCacheOwner(level, cachedSummoner, summonerUUID);
    }

    public void setSummoner(@Nullable LivingEntity owner)
    {
        if (owner != null)
        {
            this.summonerUUID = owner.getUUID();
            this.cachedSummoner = owner;
        }
    }

    @Override
    public void tick() {
        float radius = 6.6F;
        float distance = 2.0F;

        Vec3 healingCenter = this.position().add(this.getForward().multiply(distance, 3.5F, distance));

        var entities = level.getEntities(this, AABB.ofSize(healingCenter, radius * 2, radius, radius * 2));

        // Every forty-five seconds, heal
        if (tickCount % 20 * 45 == 0)
        {
            for (Entity entity : entities)
            {
                if (entity instanceof LivingEntity livingEntity)
                {
                    if (getSummoner() != null && livingEntity.isAlliedTo(getSummoner()) && this.isPassenger())
                    {
                        //MagicManager.spawnParticles(this.level, new BlastwaveParticleOptions(CSSchoolRegistry.TECHNOMANCY.get().getTargetingColor(), radius * 2), entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 0, true);
                        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(CSSchoolRegistry.TECHNOMANCY.get().getTargetingColor(), radius * 2),
                                getX(), getY(), getZ(),
                                1, 0, 0, 0, 0, false);
                        livingEntity.heal(getHealingAmount());
                        getSummoner().heal((float) getHealingAmount() / 2);

                        System.out.println("Healing: " + getHealingAmount());
                        System.out.println("Healing for summoner: " + getHealingAmount()/2);
                    }
                }
            }
        }
    }

    public int getHealingAmount()
    {
        return healingAmount;
    }

    public void setHealingAmount(int amount)
    {
        this.healingAmount = amount;
    }

    // Attacks and Death
    @Override
    public void die(DamageSource pDamageSource) {
        this.onDeathHelper();
        super.die(pDamageSource);
    }

    @Override
    public void onRemovedFromWorld() {
        this.onRemovedHelper(this, CSPotionEffectRegistry.WATCHER_TIMER.get());
        super.onRemovedFromWorld();
    }

    @Override
    public void onUnSummon() {
        if (!level.isClientSide)
        {
            MagicManager.spawnParticles(level, ParticleTypes.POOF,
                    getX(), getY(), getZ(),
                    25, 0.4, 0.8, 0.4, 0.03, false);
            discard();
        }
    }

    @Override
    public boolean causeFallDamage(float pFallDistance, float pMultiplier, DamageSource pSource) {
        return false;
    }

    // GECKOLIB STUFF
    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<SurveillanceDroneEntity> controller = new AnimationController<>(this, "controller", 0, this::predicate);
        data.addAnimationController(controller);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        AnimationBuilder builder = new AnimationBuilder();

        builder.addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP);

        event.getController().setAnimation(builder);
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    // NBT
    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.summonerUUID = OwnerHelper.deserializeOwner(pCompound);
        this.healingAmount = pCompound.getInt("Healing");
    }

    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        OwnerHelper.serializeOwner(pCompound, summonerUUID);
        pCompound.putInt("Healing", this.getHealingAmount());
    }
}
