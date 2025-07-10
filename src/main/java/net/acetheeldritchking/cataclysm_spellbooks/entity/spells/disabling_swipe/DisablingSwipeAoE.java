package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.disabling_swipe;

import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

import java.util.Optional;

public class DisablingSwipeAoE extends AoeEntity {
    private static final EntityDataAccessor<Boolean> DATA_IS_MIRRORED = SynchedEntityData.defineId(DisablingSwipeAoE.class, EntityDataSerializers.BOOLEAN);
    protected int effectAmplifier;
    protected float effectDuration;

    public DisablingSwipeAoE(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    LivingEntity target;

    public final int ticksPerFrame = 2;
    public final int deathTime = ticksPerFrame * 4;

    public DisablingSwipeAoE(Level level, boolean mirrored)
    {
        this(CSEntityRegistry.DISABLING_SWIPE.get(), level);
        if (mirrored)
        {
            this.getEntityData().set(DATA_IS_MIRRORED, true);
        }
    }

    public int getEffectAmplifier()
    {
        return effectAmplifier;
    }

    public void setEffectAmplifier(int amount)
    {
        this.effectAmplifier = amount;
    }

    public int getEffectDuration()
    {
        return effectAmplifier;
    }

    public void setEffectDuration(float amount)
    {
        this.effectDuration = amount;
    }

    @Override
    public void applyEffect(LivingEntity target) {
        target.addEffect(new MobEffectInstance(CSPotionEffectRegistry.DISABLED_EFFECT.get(), getEffectDuration(), getEffectAmplifier(), true, true, true));
    }

    @Override
    public void tick() {
        if (!firstTick)
        {
            checkHits();
            firstTick = true;
        }
        if (tickCount >= deathTime)
        {
            discard();
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.getEntityData().define(DATA_IS_MIRRORED, false);
    }

    public boolean isMirrored()
    {
        return this.getEntityData().get(DATA_IS_MIRRORED);
    }

    @Override
    public boolean shouldBeSaved() {
        return false;
    }

    @Override
    public float getParticleCount() {
        return 0;
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.empty();
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
