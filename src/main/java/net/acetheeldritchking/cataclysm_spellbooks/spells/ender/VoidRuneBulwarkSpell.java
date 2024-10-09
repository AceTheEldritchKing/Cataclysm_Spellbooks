package net.acetheeldritchking.cataclysm_spellbooks.spells.ender;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;

@AutoSpellConfig
public class VoidRuneBulwarkSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "void_bulwark");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.ring_count", getRings(spellLevel)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(50)
            .build();

    public VoidRuneBulwarkSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 10;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double casterX = entity.getX();
        double casterY = entity.getY();
        double casterZ = entity.getZ();

        int casterYPosition = Mth.floor(casterY - 3);

        //spawnVoidRuneCircle(level, casterX, casterZ, casterYPosition, casterY + 1, 0, entity, 6, 3, spellLevel);
        spawnVoidRuneCircle(level, entity, spellLevel);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnVoidRune(Level level, double x, double z, double minY, double maxY, double rotation, int delay, LivingEntity caster)
    {
        BlockPos blockPos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos blockPos1 = blockPos.below();
            BlockState blockState = level.getBlockState(blockPos1);
            if (blockState.isFaceSturdy(level, blockPos1, Direction.UP))
            {
                if (!level.isEmptyBlock(blockPos))
                {
                    BlockState blockState1 = level.getBlockState(blockPos);
                    VoxelShape voxelShape = blockState1.getCollisionShape(level, blockPos);
                    if (!voxelShape.isEmpty())
                    {
                        d0 = voxelShape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            blockPos = blockPos.below();
        } while (blockPos.getY() >= Mth.floor(minY) - 1);

        if (flag)
        {
            Void_Rune_Entity voidRune = new Void_Rune_Entity(level, x, blockPos.getY() + d0, z, (float) rotation, delay, caster);
            //voidRune.moveTo(caster.getX(), caster.getY(), caster.getZ());
            level.addFreshEntity(voidRune);
        }
    }

    private void spawnVoidRuneCircle (Level level, LivingEntity caster, int spellLevel)
    {
        double casterX = caster.getX();
        double casterY = caster.getY();
        double casterZ = caster.getZ();
        double casterHeadY = casterY + 1;

        int casterYPosition = Mth.floor(casterY - 3);

        int numRings = spellLevel;
        double count = 1;
        // I don't want too many entities spawning
        int entitiesPerRing = Mth.clamp((int) getSpellPower(spellLevel, caster), 1, 50);
        int warmUp = 0;

        for (int rings = 0; rings < numRings; rings++)
        {
            double radius = count + rings * 1.5;
            int entitiesInRing = entitiesPerRing + rings * 4;
            int increaseWarmUp = warmUp + rings * 5;

            for (int i = 0; i < entitiesInRing; i++)
            {
                double angle = 2 * Math.PI * i / entitiesInRing;

                double x = casterX + Mth.cos((float) angle) * radius;
                double z = casterZ + Mth.sin((float) angle) * radius;

                spawnVoidRune(level, x, z, casterYPosition, casterHeadY, angle, increaseWarmUp, caster);
            }
        }
    }

    private int getRings (int spellLevel)
    {
        return spellLevel;
    }
}
