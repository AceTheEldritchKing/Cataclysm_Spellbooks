package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.extended;

import com.github.L_Ender.cataclysm.entity.projectile.Laser_Beam_Entity;
import com.github.L_Ender.cataclysm.util.CMDamageTypes;
import io.redspace.ironsspellbooks.damage.DamageSources;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.event.ForgeEventFactory;

public class ExtendedLaserBeamEntity extends Laser_Beam_Entity {
    public ExtendedLaserBeamEntity(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    public ExtendedLaserBeamEntity(EntityType type, double x, double y, double z, Level worldIn) {
        super(type, worldIn);
        this.setPos(x, y, z);
    }

    public ExtendedLaserBeamEntity(Level worldIn, LivingEntity shooter) {
        this(CSEntityRegistry.EXTENDED_LASER_BEAM.get(), shooter.getX(), shooter.getEyeY(), shooter.getZ(), worldIn);
        this.setOwner(shooter);
    }

    @Override
    protected void onHitEntity(EntityHitResult hitResult) {
        super.onHitEntity(hitResult);

        if (!this.level().isClientSide)
        {
            Entity entity = hitResult.getEntity();
            LivingEntity owner = (LivingEntity) this.getOwner();

            int i = entity.getRemainingFireTicks();
            entity.setSecondsOnFire(5);

            if (!entity.hurt(CMDamageTypes.causeLaserDamage(this, owner), this.getDamage()))
            {
                entity.setRemainingFireTicks(i);
            } else if (owner != null)
            {
                DamageSources.applyDamage(entity, getDamage(),
                        SpellRegistries.LASERBOLT.get().getDamageSource(this, getOwner()));
            }
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult pResult) {
        super.onHitBlock(pResult);

        if (!this.level().isClientSide)
        {
            Entity entity = this.getOwner();
            BlockPos pos;

            if (CSConfig.doSpellGriefing.get())
            {
                pos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level().isEmptyBlock(pos))
                {
                    this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
                }
            } else if (!(entity instanceof Mob) || ForgeEventFactory.getMobGriefingEvent(this.level(), entity))
            {
                pos = pResult.getBlockPos().relative(pResult.getDirection());
                if (this.level().isEmptyBlock(pos))
                {
                    this.level().setBlockAndUpdate(pos, BaseFireBlock.getState(this.level(), pos));
                }
            }
        }

        discard();
    }
}
