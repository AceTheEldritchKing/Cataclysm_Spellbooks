package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
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
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 1)),
                Component.translatable("ui.cataclsym_spellboks.soul_render_damage", Utils.stringTruncation(getBonusDamage(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(60)
            .build();

    public MalevolentBattlefieldSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 80;
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
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.WARDEN_HEARTBEAT);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.MALEDICTUS_BATTLE_CRY.get());
    }

    @Override
    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        //System.out.println("Pre cast");

        double radius = 15;

        List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        for (LivingEntity targets : entitiesNearby)
        {
            targets.addEffect(new MobEffectInstance(MobEffects.DARKNESS, getCastTime(spellLevel), 1, false, false, false));
        }
        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, getCastTime(spellLevel), 1, false, false, false));

        super.onServerPreCast(level, spellLevel, entity, playerMagicData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Summon halberd field
        Item soulRenderer = ModItems.SOUL_RENDER.get();

        if (entity.getMainHandItem().is(soulRenderer))
        {
            spawnHalberdField(spellLevel * 4, (int) getSpellPower(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, getBonusDamage(spellLevel, entity), spellLevel);
        }
        else
        {
            spawnHalberdField(spellLevel * 4, (int) getSpellPower(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, getDamage(spellLevel, entity), spellLevel);
        }
        //System.out.println("After cast");

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnHalberdField(int numofBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay, LivingEntity caster, Level level, float damage, int spellLevel)
    {
        float angleIncrement = (float) (2 * Math.PI / numofBranches);

        for (int branch = 0; branch < numofBranches; ++branch)
        {
            //System.out.println("Spawn Halberds Field");
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

                spawnHalberds(spawnX, spawnZ, caster.getY() - 5, caster.getY() + 3, currentAngle, d1, damage, caster, level, spellLevel);

                double deltaX = level.random.nextGaussian() * 0.007D;
                double deltaY = level.random.nextGaussian() * 0.007D;
                double deltaZ = level.random.nextGaussian() * 0.007D;
                if (level.isClientSide)
                {
                    //System.out.println("Particles");
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }
            }
        }
    }

    private void spawnHalberds(double x, double z, double minY, double maxY, float rotation, int delay, float damage, LivingEntity caster, Level level, int spellLevel)
    {
        BlockPos pos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        int maxIterations = spellLevel * 4;
        int iterationCount = 0;

        do {
            //System.out.println("Trying to find block at pos: " + pos);

            BlockPos pos1 = pos.below();
            BlockState blockState = level.getBlockState(pos1);

            if (blockState.isFaceSturdy(level, pos1, Direction.UP)) {

                //System.out.println("Found a sturdy block at: " + pos1);

                if (!level.isEmptyBlock(pos)) {
                    BlockState blockState1 = level.getBlockState(pos);
                    VoxelShape shape = blockState1.getCollisionShape(level, pos);

                    if (!shape.isEmpty()) {
                        d0 = shape.max(Direction.Axis.Y);
                    }
                }

                flag = true;
                break;
            }

            pos = pos.below();
            iterationCount++;

        } while (pos.getY() >= Mth.floor(minY) && iterationCount < maxIterations);

        if (flag) {
            //System.out.println("Actually Spawn Halberds at pos: " + pos);
            Phantom_Halberd_Entity phantomHalberd = new Phantom_Halberd_Entity(level, x, pos.getY() + d0, z, rotation, delay, caster, damage);
            level.addFreshEntity(phantomHalberd);
            //System.out.println("Halberd spawned successfully!");
        } /*else {
            System.out.println("Failed to find a valid position to spawn the halberd.");
        }*/
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 5;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster)
    {
        float baseDamage = getDamage(spellLevel, caster);
        int bonusAmount = (int) (3.5 + spellLevel);

        return baseDamage + bonusAmount;
    }
}
