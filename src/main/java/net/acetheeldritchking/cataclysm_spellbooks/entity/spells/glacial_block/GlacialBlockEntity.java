package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.spells.root.PreventDismount;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.UUID;

public class GlacialBlockEntity extends LivingEntity implements IAnimatable, PreventDismount, AntiMagicSusceptible {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    @Nullable
    private LivingEntity owner;

    @Override
    public float getScale() {
        return target == null ? 1 : target.getScale();
    }

    @Nullable
    private UUID ownerUUID;
    private int duration;
    private boolean playSound = true;
    private LivingEntity target;

    public GlacialBlockEntity(EntityType<? extends LivingEntity> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public GlacialBlockEntity(Level level, LivingEntity owner) {
        this(CSEntityRegistry.GLACIAL_BLOCK.get(), level);
        setOwner(owner);
    }

    public LivingEntity getTarget() {
        return this.target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean shouldRiderSit() {
        return false;
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0D;
    }

    @Override
    public boolean shouldRiderFaceForward(Player player) {
        return true;
    }

    @Override
    public EntityDimensions getDimensions(Pose pPose) {
        var frozen = getFirstPassenger();

        if (frozen != null) {
            //IronsSpellbooks.LOGGER.debug("getDimensions {}", rooted.getBbWidth());
            return EntityDimensions.fixed(frozen.getBbWidth() * 1.25f, .75f);
        }

        return super.getDimensions(pPose);
    }

    @Override
    public void tick() {
        super.tick();

        if (playSound)
        {
            this.refreshDimensions();
            playSound(SoundRegistry.ICE_BLOCK_CAST.get(), 2F, 1);
            playSound = false;
        }

        if (!level.isClientSide)
        {
            if (tickCount > duration || (target != null && target.isDeadOrDying()) || !isVehicle())
            {
                this.removeGlacialBlock();
            }
            else
            {
                if (tickCount > 20)
                {
                    DamageSources.applyDamage(target, 5,
                            SpellRegistries.CRYOPIERCER.get().getDamageSource(this, getOwner()));
                }
            }
        }
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.owner = owner;
        this.ownerUUID = owner == null ? null : owner.getUUID();
    }

    @Nullable
    public LivingEntity getOwner() {
        if (this.owner == null && this.ownerUUID != null && this.level instanceof ServerLevel)
        {
            Entity entity = ((ServerLevel)this.level).getEntity(this.ownerUUID);
            if (entity instanceof LivingEntity)
            {
                this.owner = (LivingEntity) entity;
            }
        }

        return owner;
    }

    public void removeGlacialBlock() {
        if (level.isClientSide) {
            for (int i = 0; i < 5; i++) {
                level.addParticle(ParticleHelper.SNOWFLAKE, getX() + Utils.getRandomScaled(.1f), getY() + Utils.getRandomScaled(.1f), getZ() + Utils.getRandomScaled(.1f), Utils.getRandomScaled(2f), -random.nextFloat() * .5f, Utils.getRandomScaled(2f));
            }
        }
        this.ejectPassengers();
        this.discard();
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public boolean hasIndirectPassenger(Entity pEntity) {
        return true;
    }

    @Override
    public void onAntiMagic(MagicData playerMagicData) {
        removeGlacialBlock();
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isPickable() {
        return false;
    }

    @Override
    public boolean isDamageSourceBlocked(DamageSource pDamageSource) {
        return true;
    }

    @Override
    public boolean showVehicleHealth() {
        return false;
    }

    @Override
    public void positionRider(Entity pPassenger) {
        int x = (int) (this.getX() - pPassenger.getX());
        int y = (int) (this.getY() - pPassenger.getY());
        int z = (int) (this.getZ() - pPassenger.getZ());
        x *= x;
        y *= y;
        z *= z;

        // Eventually I want to cancel being able to teleport while frozen
        if (x + y + z > 5 * 5)
        {
            this.removeGlacialBlock();
        } else
        {
            pPassenger.setPos(this.getX(), this.getY(), this.getZ());
        }
    }

    @Override
    protected boolean isImmobile() {
        return true;
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean isPushedByFluid(FluidType type) {
        return false;
    }

    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.isBypassInvul())
        {
            this.removeGlacialBlock();
            return true;
        }
        return false;
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.singleton(ItemStack.EMPTY);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot pSlot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setItemSlot(EquipmentSlot pSlot, ItemStack pStack) {
        //
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }

    // NBT
    @Override
    public void addAdditionalSaveData(CompoundTag pCompound) {
        super.addAdditionalSaveData(pCompound);
        pCompound.putInt("Age", this.tickCount);
        if (this.ownerUUID != null)
        {
            pCompound.putUUID("Owner", this.ownerUUID);
        }
        pCompound.putInt("Duration", duration);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag pCompound) {
        super.readAdditionalSaveData(pCompound);
        this.tickCount = pCompound.getInt("Age");
        if (pCompound.hasUUID("Owner"))
        {
            this.ownerUUID = pCompound.getUUID("Owner");
        }
        this.duration = pCompound.getInt("Duration");
    }

    @Override
    public void registerControllers(AnimationData data) {

    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
