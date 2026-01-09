package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class IncinerationSpell extends AbstractIgnisSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "incineration");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.range",
                        Utils.stringTruncation(spellLevel, 2)),
                Component.translatable("ui.cataclysm_spellbooks.incineration_duration",
                        Utils.stringTruncation(spellPower(spellLevel, caster)/10, 2)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(10)
            .build();

    public IncinerationSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 60;
        this.baseManaCost = 100;
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
        return Optional.of(ModSounds.IGNIS_AMBIENT.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.FLAME_BURST.get());
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        final float MAX_HEALTH = entity.getMaxHealth();
        float baseHealth = entity.getHealth();
        double percent = (baseHealth/MAX_HEALTH) * 100;

        double casterX = entity.getX();
        double casterZ = entity.getZ();
        double casterHeadY = entity.getY() + 1.0D;
        int standOnYPos = Mth.floor(entity.getY()) - 2;
        float yawRadians = (float) Math.toRadians(90.0F + entity.getYRot());
        for (int i = 0; i < spellPower(spellLevel, entity)/10; i++)
        {
            double d2 = 2.25D * (i + 1);
            int j2 = (int) (1.5F * i);

            double casterXYaw = casterX + Mth.cos(yawRadians) * d2;
            double casterZYaw = casterZ + Mth.sin(yawRadians) * d2;

            if (percent <= 50)
            {
                spawnFlameStrike(casterXYaw, casterZYaw, standOnYPos, casterHeadY, yawRadians, spellLevel * 20, j2, j2, level, 1.0F, true, entity, spellLevel);
            }
            else
            {
                spawnFlameStrike(casterXYaw, casterZYaw, standOnYPos, casterHeadY, yawRadians, spellLevel * 20, j2, j2, level, 1.0F, false, entity, spellLevel);
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnFlameStrike(double x, double z, double minY, double maxY, float rotation, int duration, int wait, int delay, Level level, float radius, boolean isSoul, LivingEntity caster, int spellLevel)
    {
        BlockPos pos = new BlockPos((int) x, (int) maxY, (int) z);
        boolean flag = false;
        double d0 = 0.0D;

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
        }
        while (pos.getY() >= Mth.floor(minY) - 1);

        if (flag)
        {
            level.addFreshEntity(new Flame_Strike_Entity(level, x, pos.getY() + d0, z, rotation, duration, wait, delay, radius, getDamage(spellLevel, caster), getHPDamage(spellLevel), isSoul, caster));

            if (isSoul);
            {
                level.addFreshEntity(new Flame_Strike_Entity(level, x, pos.getY() + d0, z, rotation, duration, wait, delay, radius, (float) (getDamage(spellLevel, caster) * 1.5), getHPDamage(spellLevel), isSoul, caster));
                //System.out.println("Is soul?");
            }
        }
    }

    private float spellPower(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return (float) (getSpellPower(spellLevel, caster)/1.5);
    }

    private float getHPDamage(int spellLevel)
    {
        return (float) (spellLevel * 10) /100;
    }
}
