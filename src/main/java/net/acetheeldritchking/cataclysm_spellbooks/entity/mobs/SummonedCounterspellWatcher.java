package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.The_Watcher_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.util.OwnerHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.UUID;

public class SummonedCounterspellWatcher extends The_Watcher_Entity implements MagicSummon {
    protected LivingEntity cachedSummoner;
    protected UUID summonerUUID;

    public SummonedCounterspellWatcher(EntityType entity, Level world) {
        super(entity, world);
        xpReward = 0;
    }

    public SummonedCounterspellWatcher(Level level, LivingEntity owner) {
        this(CSEntityRegistry.SUMMONED_COUNTERSPELL_WATCHER.get(), level);
        setSummoner(owner);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.getAvailableGoals().removeIf(goal ->
                goal.getGoal() instanceof HurtByTargetGoal || goal.getGoal() instanceof NearestAttackableTargetGoal
        );
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.5f, true));
        this.goalSelector.addGoal(3, new GenericFollowOwnerGoal(this, this::getSummoner, 1.0f, 10, 2, false, 50));
        this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 8.0F, 1.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Mob.class, 9.0F));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0, 80));

        // Attack mobs on sight
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, true));

        this.targetSelector.addGoal(1, new GenericOwnerHurtByTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(2, new GenericOwnerHurtTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(3, new GenericCopyOwnerTargetGoal(this, this::getSummoner));
        this.targetSelector.addGoal(4, (new GenericHurtByTargetGoal(this, (entity) -> entity == getSummoner())).setAlertOthers());
        super.registerGoals();
    }

    @Override
    public boolean hurt(DamageSource source, float damage) {
        if (shouldIgnoreDamage(source))
            return false;
        return super.hurt(source, damage);
    }

    @Override
    public boolean doHurtTarget(Entity pEntity) {

        if (pEntity instanceof AntiMagicSusceptible antiMagicSusceptible)
        {
            if (antiMagicSusceptible instanceof MagicSummon summon)
            {
                if (summon.getSummoner() == pEntity)
                {
                    antiMagicSusceptible.onAntiMagic(MagicData.getPlayerMagicData((LivingEntity) pEntity));
                } else
                {
                    antiMagicSusceptible.onAntiMagic(MagicData.getPlayerMagicData((LivingEntity) pEntity));
                }
            } else
            {
                antiMagicSusceptible.onAntiMagic(MagicData.getPlayerMagicData((LivingEntity) pEntity));
            }
        } else if (pEntity instanceof ServerPlayer player)
        {
            Utils.serverSideCancelCast(player, true);
            MagicData.getPlayerMagicData(player).getPlayerRecasts().removeAll(RecastResult.COUNTERSPELL);
        } else if (pEntity instanceof IMagicEntity abstractSpellCastingMob)
        {
            abstractSpellCastingMob.cancelCast();
        }
        if (pEntity instanceof LivingEntity livingEntity)
        {
            for (MobEffect mobEffect : livingEntity.getActiveEffectsMap().keySet().stream().toList())
            {
                if (mobEffect instanceof MagicMobEffect magicMobEffect)
                {
                    livingEntity.removeEffect(magicMobEffect);
                }
            }
        }

        Explosion explosion = new Explosion(level, null, SpellRegistries.DOS_SWARM.get().getDamageSource(this, getSummoner()), null, this.getX(), this.getY(), this.getZ(), 2, false, Explosion.BlockInteraction.NONE);
        if (!net.minecraftforge.event.ForgeEventFactory.onExplosionStart(level, explosion)) {
            explosion.explode();
            explosion.finalizeExplosion(true);
        }

        this.kill();

        return Utils.doMeleeAttack(this, pEntity, SpellRegistries.DOS_SWARM.get().getDamageSource(this, getSummoner()));
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide())
        {
            if (tickCount % 20 == 0)
            {
                MagicManager.spawnParticles(this.level, ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        getX(), getY(), getZ(),
                        1, 0.1, 0.2, 0.1, 0.01, true);

                MagicManager.spawnParticles(this.level, ParticleTypes.LARGE_SMOKE,
                        getX(), getY(), getZ(),
                        5, 0.1, 0.4, 0.1, 0.01, true);

                MagicManager.spawnParticles(this.level, ParticleTypes.FLAME,
                        getX(), getY(), getZ(),
                        15, 0.4, 0.8, 0.4, 0.04, true);
            }
        }
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
            spawnParticles(this);
            discard();
        }
    }

    private void spawnParticles(LivingEntity entity)
    {
        ServerLevel level = (ServerLevel) entity.level;
        level.sendParticles(ModParticle.EM_PULSE.get(), entity.getX(), entity.getY() + 1, entity.getZ(), 1, 0, 0, 0, 0.0);
    }

    @Override
    public boolean isAlliedTo(Entity entityIn) {
        //return super.isAlliedTo(entityIn) || this.isAlliedHelper(entityIn);
        if (entityIn == this)
        {
            return true;
        }
        else if (entityIn == getSummoner() || this.isAlliedHelper(entityIn))
        {
            return true;
        }
        else if (getSummoner() != null && !entityIn.isAlliedTo(getSummoner()))
        {
            return false;
        }
        else
        {
            return this.getTeam() == null && entityIn.getTeam() == null;
        }
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
