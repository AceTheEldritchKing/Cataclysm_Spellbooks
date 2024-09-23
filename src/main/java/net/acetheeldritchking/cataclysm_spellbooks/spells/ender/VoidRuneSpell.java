package net.acetheeldritchking.cataclysm_spellbooks.spells.ender;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
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
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 3;
        this.spellPowerPerLevel = 1;
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
        return CastType.INSTANT;
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
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetingData)
        {
            var targetEntity = targetingData.getTarget((ServerLevel) level);
            if (targetEntity != null)
            {
                double targetX = targetEntity.getX();
                double targetY = targetEntity.getY();
                double targetZ = targetEntity.getZ();

                for (int i = 0; i < getDuration(spellLevel, entity); i++)
                {
                    double d0 = targetY;
                    double d1 = targetY + 1.0D;
                    float f = (float) Mth.atan2(targetZ, targetX);

                    float f1 = (float) (f + (Math.PI * 0.4f));
                    int delay = i / 3;

                    this.summonVoidRune(targetX + Mth.cos(f1) * 1.5D,
                            targetZ + Mth.sin(f1) * 1.5D, d0, d1, f1, delay, entity, targetEntity, spellLevel);
                }
            }
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    // Literally looking at Evoker & Ender Guardian because they are almost the same
    private void summonVoidRune(double x, double minY, double maxY, double z, float rotation, int delay, LivingEntity caster, LivingEntity target, int spellLevel)
    {
        BlockPos pos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        double targetX = target.getX();
        double targetY = target.getY();
        double targetZ = target.getZ();

        Level level = caster.level;

        do {
            BlockPos pos1 = pos.below();
            BlockState blockState = level.getBlockState(pos1);
            if (blockState.isFaceSturdy(level, pos1, Direction.UP))
            {
                if (!level.isEmptyBlock(pos))
                {
                    BlockState blockState1 = level.getBlockState(pos);
                    VoxelShape voxelShape = blockState1.getCollisionShape(level, pos);
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
            Void_Rune_Entity voidRune = new Void_Rune_Entity(level, x, pos.getY() + d0, z, rotation, delay, caster);
            voidRune.moveTo(targetX, targetY, targetZ);

            spawnRuneAndEffects(level, voidRune, target, caster, spellLevel);
        }
    }

    private void spawnRuneAndEffects(Level level, Void_Rune_Entity rune, LivingEntity target, LivingEntity caster, int spellLevel)
    {
        level.addFreshEntity(rune);
        target.addEffect(new MobEffectInstance(CSPotionEffectRegistry.SUMMON_VOID_RUNE.get(),
                getEffectDuration(spellLevel, caster), 1, false, false, false));
    }

    private int getDuration(int spellLevel, LivingEntity caster)
    {
        return getLevelFor(spellLevel, caster);
    }

    private int getEffectDuration(int spellPower, LivingEntity caster)
    {
        int duration = ((int) (getSpellPower(spellPower, caster) * 100))/2;
        int maxTicksForDuration = 10 * 20;
        int maxDuration = Math.min(duration * 20, maxTicksForDuration);

        return maxDuration;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ANIMATION_INSTANT_CAST;
    }
}
