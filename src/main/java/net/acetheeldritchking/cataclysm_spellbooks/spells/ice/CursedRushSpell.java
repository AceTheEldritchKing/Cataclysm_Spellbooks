package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.ImpulseCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

@AutoSpellConfig
public class CursedRushSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "cursed_rush");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(80)
            .build();

    public CursedRushSpell()
    {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 5;
        this.castTime = 10;
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
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new ImpulseCastData();
    }

    @Override
    public void onClientCast(Level level, int spellLevel, LivingEntity entity, ICastData castData) {
        if (castData instanceof ImpulseCastData data)
        {
            entity.hasImpulse = data.hasImpulse;
            entity.setDeltaMovement(entity.getDeltaMovement().add(data.x, data.y, data.z));
        }

        super.onClientCast(level, spellLevel, entity, castData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.hasImpulse = true;
        float multiplier = ((getSpellPower(spellLevel, entity) + (spellLevel + 10)) / 10);

        Vec3 forwards = entity.getLookAngle();
        //forwards = forwards.yRot(90);
        if (playerMagicData.getAdditionalCastData() instanceof CursedRushDirectionOverrideCastData)
        {
            if (Utils.random.nextBoolean())
            {
                forwards = forwards.yRot(90);
            } else
            {
                forwards = forwards.yRot(-90);
            }
        }

        Vec3 vec3 = forwards.multiply(3, 1, 3).normalize().add(0, 0.25, 0).scale(multiplier);

        if (entity.isOnGround())
        {
            entity.setPos(entity.position().add(0, 1.5, 0));
            vec3.add(0, 0.25, 0);
        }

        playerMagicData.setAdditionalCastData(new ImpulseCastData((float) vec3.x, (float) vec3.y, (float) vec3.z, true));
        entity.setDeltaMovement(new Vec3(
                Mth.lerp(0.75F, entity.getDeltaMovement().x, vec3.x),
                Mth.lerp(0.75F, entity.getDeltaMovement().y, vec3.y),
                Mth.lerp(0.75F, entity.getDeltaMovement().z, vec3.z)
        ));

        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.CURSED_FRENZY.get(), 15, (int) getDamage(spellLevel, entity), false, false, false));
        spawnHalberdLine(5, 5, 1.0F, 1.0F, 0.2F, 1, entity, level, 5, spellLevel);

        //int yLevelStanding = Mth.floor(entity.getY()) - 3;
        //double headY = entity.getY() + 2;
        //float yawToRadians = (float) Math.toRadians(90 + entity.getYRot());
        //spawnHalberds(entity.position().x, headY, entity.position().y, yLevelStanding, yawToRadians, 1, 5, entity, level, 1);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnHalberdLine(int numofBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay, LivingEntity caster, Level level, float damage, int spellLevel)
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

                double deltaX = level.random.nextGaussian() * 0.007D;
                double deltaY = level.random.nextGaussian() * 0.007D;
                double deltaZ = level.random.nextGaussian() * 0.007D;
                if (level.isClientSide)
                {
                    //System.out.println("Particles");
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }

                spawnHalberds(spawnX, spawnZ, caster.getY() - 5, caster.getY() + 3, currentAngle, d1, damage, caster, level, spellLevel);
            }
        }
    }

    public void spawnHalberds(double x, double z, double minY, double maxY, float rotation, int delay, float damage, LivingEntity caster, Level level, int spellLevel)
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

    private static class CursedRushDirectionOverrideCastData implements ICastData
    {
        @Override
        public void reset()
        {
            // Doesn't seem like I have to put anything in here tbh?
        }
    }
}
