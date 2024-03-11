package net.acetheeldritchking.cataclysm_spellbooks.spells;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.CastTargetingData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class VoidRuneSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "void_rune");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.void_rune",
                        Utils.stringTruncation(getSpellPower(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(20)
            .build();

    public VoidRuneSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 4;
        this.spellPowerPerLevel = 0;
        this.castTime = 0;
        this.baseManaCost = 50;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return super.getCastStartSound();
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.VOID_RUNE_RISING.get());
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 50, .15f);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof CastTargetingData targetingData)
        {
            var targetEntity = targetingData.getTarget((ServerLevel) level);
            if (targetEntity != null)
            {
                /*
                BlockPos pos = new BlockPos(targetX, targetY, targetZ);

                Vec3 targetCenter = targetEntity.position().add(0, pos.getY(), 0);
                Void_Rune_Entity voidRune = new Void_Rune_Entity
                        (entity.level, 0, pos.getY(), 0, 1.0f, 1, entity);

                voidRune.moveTo(targetCenter);
                entity.level.addFreshEntity(voidRune);

                if (entity.distanceToSqr(targetEntity) < 9.0D)
                {
                    for (int i = 0; i < getCount(spellLevel, entity); i++)
                    {

                       this.summonVoidRune(entity.getX() + Mth.cos(f1) * 1.5D,
                               entity.getZ() + Mth.sin(f1) * 1.5D, d0, d1, f1, 0, entity);
                    }
                }
                */

                double targetX = targetEntity.getX();
                double targetY = targetEntity.getY();
                double targetZ = targetEntity.getZ();

                double d0 = Math.min(targetY, entity.getY());
                double d1 = Math.min(targetY, entity.getY() + 1.0D);
                float f = (float) Mth.atan2(targetZ - entity.getZ(), targetX - entity.getX());

                Vec3 forward = entity.getForward().multiply(1, 0, 1).normalize();
                Vec3 start = entity.getEyePosition().add(forward.scale(1.5));

                for (int i = 0; i < getCount(spellLevel, entity); i++)
                {
                    Vec3 spawn = start.add(forward.scale(i));
                    if (!level.getBlockState(new BlockPos(spawn).below()).isAir())
                    {
                        float f1 = (float) (f + (i * Math.PI * 0.4f));
                        int delay = i / 3;
                        this.summonVoidRune(targetX + Mth.cos(f1) * 1.5D,
                                targetZ + Mth.sin(f1) * 1.5D, d0, d1, f1, delay, entity);
                    }
                }
            }
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    // Literally looking at Evoker & Ender Guardian because they are almost the same
    private void summonVoidRune(double x, double minY, double maxY, double z, float rotation, int delay, LivingEntity caster)
    {
        BlockPos pos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos pos1 = pos.below();
            BlockState blockState = caster.level.getBlockState(pos1);
            if (blockState.isFaceSturdy(caster.level, pos1, Direction.UP))
            {
                if (!caster.level.isEmptyBlock(pos))
                {
                    BlockState blockState1 = caster.level.getBlockState(pos);
                    VoxelShape voxelShape = blockState1.getCollisionShape(caster.level, pos);
                    if (!voxelShape.isEmpty())
                    {
                        d0 = voxelShape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            pos = pos.below();
        } while (pos.getY() >= Mth.floor(minY - 1));

        if (flag)
        {
            caster.level.addFreshEntity(new Void_Rune_Entity(caster.level, x, pos.getY() + d0, z, rotation, delay, caster));
        }
    }

    private int getCount(int spellLevel, LivingEntity caster)
    {
        return 5 + getLevel(spellLevel, caster);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST;
    }
}
