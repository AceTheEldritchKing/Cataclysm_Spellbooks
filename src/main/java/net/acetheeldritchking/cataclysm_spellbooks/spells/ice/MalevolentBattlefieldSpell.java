package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class MalevolentBattlefieldSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "malevolent_battlefield");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.halberd_rings", spellLevel),
                Component.translatable("ui.cataclysm_spellbooks.halberd_amount", Utils.stringTruncation(getSpellPower(spellLevel, caster), 0)),
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(60)
            .build();

    public MalevolentBattlefieldSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
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
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_MALEVOLENT_HAND_SIGN;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SPIT_FINISH_ANIMATION;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.WARDEN_HEARTBEAT);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.MALEDICTUS_BATTLE_CRY.get());
    }

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        System.out.println("Pre cast");

        double radius = 15;

        List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        for (LivingEntity targets : entitiesNearby)
        {
            targets.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 4*20, 1, false, false, false));
        }
        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 4*20, 1, false, false, false));

        super.onServerPreCast(level, spellLevel, entity, playerMagicData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Summon halberd field
        if (level.isClientSide)
        {
            System.out.println("Particles on cast");
            double casterX = entity.getX();
            double casterY = entity.getY();
            double casterZ = entity.getZ();
            float f = Mth.cos((float) (entity.yBodyRot * Math.PI / 180));
            float f1 = Mth.sin((float) (entity.yBodyRot * Math.PI / 180));
            double theta = entity.yBodyRot * (Math.PI / 180);
            theta += Math.PI / 2;
            double vecX = Math.cos(theta);
            double vecZ = Math.sin(theta);

            level.addParticle(new RingParticle.RingData(0, (float) Math.PI / 2f, 40, 0.337f, 0.925f, 0.8f, 1.0f, 30f, false,
                    RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), casterX + 2.5F * vecX + f * 0.2F, casterY + 0.02F, casterZ + 2.5F * vecZ + f1 * 0.2F, 0, 0, 0);
        }
        spawnHalberdField(spellLevel * 3, 1, 3.0, 0.75, 0.2, 1, entity, level, getDamage(spellLevel, entity));
        System.out.println("After cast");

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnHalberdField(int numofBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay, LivingEntity caster, Level level, float damage)
    {
        float angleIncrement = (float) (2 * Math.PI / numofBranches);

        for (int branch = 0; branch < numofBranches; ++branch)
        {
            System.out.println("Spawn Halberds Field");
            float baseAngle = angleIncrement * branch;

            for (int i = 0; i < particlesPerBranch; ++i)
            {
                double currentRadius = initialRadius + i * radiusIncrement;
                float currentAngle = (float) (baseAngle + i * angleIncrement / initialRadius + (i * curveFactor));

                double offsetX = currentRadius * Math.cos(currentAngle);
                double offsetZ = currentRadius * Math.sin(currentAngle);

                double spawnX = caster.getX() + offsetX;
                double spawnY = caster.getY() + 0.3D;
                double spawnZ = caster.getZ() + offsetZ;

                int d1 = delay * (i + 1);

                spawnHalberds(spawnX, spawnZ, caster.getY() - 5, caster.getY() + 3, currentAngle, d1, damage, caster, level);

                double deltaX = level.random.nextGaussian() * 0.007D;
                double deltaY = level.random.nextGaussian() * 0.007D;
                double deltaZ = level.random.nextGaussian() * 0.007D;
                if (level.isClientSide)
                {
                    System.out.println("Particles");
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }
            }
        }
    }

    private void spawnHalberds(double x, double z, double minY, double maxY, float rotation, int delay, float damage, LivingEntity caster, Level level)
    {
        BlockPos pos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            System.out.println("Spawn Halberds");
            BlockPos pos1 = pos.above();
            BlockState blockState = level.getBlockState(pos1);

            if (blockState.isFaceSturdy(level, pos1, Direction.DOWN))
            {
                if (!level.isEmptyBlock(pos))
                {
                    BlockState blockState1 = level.getBlockState(pos);
                    VoxelShape shape = blockState1.getCollisionShape(level, pos);

                    if (!shape.isEmpty())
                    {
                        d0 = shape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            pos = pos.above();
        } while (pos.getY() >= Mth.floor(minY) - 1);

        if (flag)
        {
            System.out.println("Actually Spawn Halberds");
            Phantom_Halberd_Entity phantomHalberd = new Phantom_Halberd_Entity(level, x, pos.getY() + d0, z, rotation, delay, caster, damage);
            level.addFreshEntity(phantomHalberd);
        }
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 5;
    }
}
