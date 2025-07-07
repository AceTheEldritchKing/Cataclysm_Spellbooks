package net.acetheeldritchking.cataclysm_spellbooks.util;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.entity.projectile.Flame_Jet_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.message.MessageParticle;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.PacketDistributor;

public class CSUtils {
    // Get caster eye height, pretty much what it says
    public static double getEyeHeight(LivingEntity entity)
    {
        return entity.getY() + entity.getEyeHeight() - 0.2;
    }

    // Halberd spawning
    public static void spawnHalberdWindmill(int numofBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay, LivingEntity caster, Level level, float damage, int spellLevel)
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

                if (!level.isClientSide())
                {
                    //System.out.println("Particles");
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }

                spawnHalberds(spawnX, spawnZ, caster.getY() - 5, caster.getY() + 3, currentAngle, d1, damage, caster, level, spellLevel);
            }
        }
    }

    public static void spawnHalberds(double x, double z, double minY, double maxY, float rotation, int delay, float damage, LivingEntity caster, Level level, int spellLevel)
    {
        BlockPos pos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        int maxIterations = Mth.clamp(spellLevel * 4, 1, 25);
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

    // Cursium Chestplate rebirth stuff
    public static boolean tryCurisumChestplateRebirth(LivingEntity livingEntity)
    {
        ItemStack chestplate = livingEntity.getItemBySlot(EquipmentSlot.CHEST);
        if ((chestplate.getItem() == ItemRegistries.CURSIUM_MAGE_CHESTPLATE.get() || chestplate.getItem() == ItemRegistries.CURSIUM_MAGE_CHESTPLATE_ELYTRA.get())
        && !livingEntity.hasEffect(ModEffect.EFFECTGHOST_SICKNESS.get()) && !livingEntity.hasEffect(ModEffect.EFFECTGHOST_FORM.get()))
        {
            livingEntity.setHealth(5);
            livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 200, 0));
            livingEntity.addEffect(new MobEffectInstance(ModEffect.EFFECTGHOST_FORM.get(), 100, 0));

            double x = livingEntity.getX();
            double y = livingEntity.getY() + 0.3F;
            double z = livingEntity.getZ();
            float size = 3.0F;

            for (ServerPlayer serverPlayer : ((ServerLevel) livingEntity.level).players())
            {
                if (serverPlayer.distanceToSqr(Vec3.atCenterOf(livingEntity.blockPosition())) < 1024)
                {
                    MessageParticle particle = new MessageParticle();

                    for (float i = -size; i <= size; i++)
                    {
                        for (float j = -size; j <= size; j++)
                        {
                            for (float k = -size; k <= size; k++)
                            {
                                double d3 = i + (livingEntity.getRandom().nextDouble() - livingEntity.getRandom().nextDouble()) * 0.5D;
                                double d4 = j + (livingEntity.getRandom().nextDouble() - livingEntity.getRandom().nextDouble()) * 0.5D;
                                double d5 = k + (livingEntity.getRandom().nextDouble() - livingEntity.getRandom().nextDouble()) * 0.5D;
                                double d6 = Mth.sqrt((float) (d3 * d3 + d4 * d4 + d5 * d5)) / 0.5 +livingEntity.getRandom().nextGaussian() * 0.05;

                                particle.queueParticle(ModParticle.CURSED_FLAME.get(), false, x, y, z, (d3 / d6), (d4 / d6), (d5 / d6));
                                if (i != -size && i != size && j != -size && j != size)
                                {
                                    k += size * 2 - 1;
                                }
                            }
                        }
                    }
                    Cataclysm.NETWORK_WRAPPER.send(PacketDistributor.PLAYER.with(() -> serverPlayer), particle);
                }
            }
            return true;
        }

        return false;
    }

    // Flame Jet Spawning
    public static void spawnFlameJets(Level level, double x, double z, double minY, double maxY, float rotation, int delay, LivingEntity caster, float damage)
    {
        BlockPos pos = new BlockPos(x, maxY, z);
        boolean flag = false;
        double d0 = 0.0D;

        do {
            BlockPos pos1 = pos.below();
            BlockState blockState = level.getBlockState(pos1);

            if (blockState.isFaceSturdy(level, pos1, Direction.UP)) {

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

        } while (pos.getY() >= Mth.floor(minY) - 1);

        if (flag) {
            Flame_Jet_Entity flameJet = new Flame_Jet_Entity(level, x, pos.getY() + d0, z, rotation, delay, damage, caster);
            level.addFreshEntity(flameJet);
        }
    }

    // Circular Flame Jets
    public static void spawnCircularFlameJets(float vec3, float math, int vertex, int rune, double time, Level level, LivingEntity caster, float damage)
    {
        float cos = Mth.cos((float) (caster.yBodyRot + (Math.PI/180)));
        float sin = Mth.sin((float) (caster.yBodyRot + (Math.PI/180)));
        double theta = caster.yBodyRot * (Math.PI/180);
        double vecX = Math.cos(theta);
        double vecZ = Math.sin(theta);

        for (int i = 0; i < vertex; i++)
        {
            float angle = (float) (i * Math.PI / ((double) vertex /2));
            for (int k = 0; k < rune; ++k)
            {
                double d2 = 1.1D * (k + 1);
                int d3 = (int) (time * (k + 1));

                spawnFlameJets(
                        level,
                        caster.getX() + vec3 * vecX + cos * math + Mth.cos(angle) * 1.25D * d2,
                        caster.getZ() + vec3 * vecZ + sin * math + Mth.sin(angle) * 1.25D * d2,
                        caster.getY() - 2,
                        caster.getY() + 2,
                        angle,
                        d3,
                        caster,
                        damage);
            }
        }
    }

    // Flame Jet spawning
    public static void spawnFlameJetWindmill(int numofBranches, int particlesPerBranch, double initialRadius, double radiusIncrement, double curveFactor, int delay, LivingEntity caster, Level level, float damage)
    {
        float angleIncrement = (float) (2 * Math.PI / numofBranches);

        for (int branch = 0; branch < numofBranches; ++branch)
        {
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

                if (!level.isClientSide())
                {
                    level.addParticle(ModParticle.PHANTOM_WING_FLAME.get(), spawnX, spawnY, spawnZ, deltaX, deltaY, deltaZ);
                }

                spawnFlameJets(level, spawnX, spawnZ, caster.getY() - 5, caster.getY() + 3, currentAngle, d1, caster, damage);
            }
        }
    }

    // For unlocking spells
    public static boolean isValidUnlockItemInInventory(Item item, Player player)
    {
        for (int i = 0; i < player.getInventory().getContainerSize(); ++i)
        {
            ItemStack itemStack = player.getInventory().getItem(i);
            if (itemStack.getItem() == item)
            {
                return true;
            }
        }

        return false;
    }
}
