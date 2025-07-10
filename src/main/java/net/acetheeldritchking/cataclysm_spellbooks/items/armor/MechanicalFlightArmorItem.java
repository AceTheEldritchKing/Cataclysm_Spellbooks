package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.particle.TrackLightningParticle;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import com.github.L_Ender.cataclysm.items.KeybindUsingArmor;
import com.github.L_Ender.cataclysm.message.MessageArmorKey;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nullable;
import java.util.List;

import static com.github.L_Ender.cataclysm.entity.projectile.Eye_Of_Dungeon_Entity.lerpRotation;

public class MechanicalFlightArmorItem extends ImbuableCataclysmArmor implements KeybindUsingArmor {
    public MechanicalFlightArmorItem(CSArmorMaterials materialIn, Type slot, Properties settings) {
        super(materialIn, slot, settings);
    }

    // Durability
    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (CMConfig.Armor_Infinity_Durability)
        {
            super.setDamage(stack, 0);
        }
        else
        {
            super.setDamage(stack, damage);
        }
    }

    // Elytra
    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (this.type == Type.CHESTPLATE)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
        if (this.type == Type.CHESTPLATE)
        {
            return ElytraItem.isFlyEnabled(stack);
        }
        else
        {
            return false;
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (this.type == Type.HELMET) {
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_helmet.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_helmet.desc2", ModKeybind.HELMET_KEY_ABILITY.getTranslatedKeyMessage()).withStyle(ChatFormatting.DARK_GREEN));
        }
        if (this.type == Type.CHESTPLATE) {
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_chestplate.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_chestplate.desc2").withStyle(ChatFormatting.DARK_GREEN));
        }
        if (this.type == Type.LEGGINGS) {
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_leggings.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_leggings.desc2").withStyle(ChatFormatting.DARK_GREEN));
        }
        if (this.type == Type.BOOTS) {
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc2", ModKeybind.BOOTS_KEY_ABILITY.getTranslatedKeyMessage()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pEntity instanceof Player player)
        {
            // Flight
            if (
                    this.type == Type.CHESTPLATE && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get()) && player.isFallFlying() ||
                    this.type == Type.CHESTPLATE && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get()) && player.isFallFlying() ||
                    this.type == Type.CHESTPLATE && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get()) && player.isFallFlying()
            )
            {
                Vec3 playerMotion = player.getDeltaMovement().add(player.getLookAngle()).normalize();

                // Looked at Icarus' code for this
                Vec3 playerVelocity = player.getDeltaMovement();
                Vec3 playerRotation = player.getForward();

                double speed = (0.25 * (player.getXRot() < -75 && player.getXRot() > -105 ? 3F : 1.5F)) / 1.5;

                player.setDeltaMovement(playerVelocity.add(
                        playerRotation.x * speed + (playerRotation.x * 1.5D - playerVelocity.x) * speed,
                        playerRotation.y * speed + (playerRotation.y * 1.5D - playerVelocity.y) * speed,
                        playerRotation.z * speed + (playerRotation.z * 1.5D - playerVelocity.z) * speed
                ).normalize());


                // Particle effects per armor set
                // Don't play this every tick
                if (player.tickCount % 3 == 0)
                {
                    if (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get()))
                    {
                        Vec3 vec3 = player.getDeltaMovement();
                        double d0 = player.getX() - vec3.x;
                        double d1 = player.getY() - vec3.y;
                        double d2 = player.getZ() - vec3.z;
                        var count = Mth.clamp((int) (vec3.lengthSqr() * 4), 1, 4);
                        for (int j = 0; j < count; j++) {
                            Vec3 random = Utils.getRandomVec3(.25);
                            var f = j / ((float) count);
                            var x = Mth.lerp(f, d0, player.getX());
                            var y = Mth.lerp(f, d1, player.getY());
                            var z = Mth.lerp(f, d2, player.getZ());

                            player.level().addParticle(ParticleHelper.FIRE, (x + 0.6) - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                            player.level().addParticle(ParticleHelper.FIRE, (x - 0.6) - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                        }
                    }

                    if (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get()))
                    {
                        Vec3 vec3 = player.getDeltaMovement();
                        double d0 = player.getX() - vec3.x;
                        double d1 = player.getY() - vec3.y;
                        double d2 = player.getZ() - vec3.z;
                        var count = Mth.clamp((int) (vec3.lengthSqr() * 4), 1, 4);
                        for (int j = 0; j < count; j++) {
                            Vec3 random = Utils.getRandomVec3(.25);
                            var f = j / ((float) count);
                            var x = Mth.lerp(f, d0, player.getX());
                            var y = Mth.lerp(f, d1, player.getY());
                            var z = Mth.lerp(f, d2, player.getZ());

                            // Top jets
                            player.level().addParticle(ParticleHelper.FIRE, (x + 0.5) - random.x, (y + 0.2) + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                            player.level().addParticle(ParticleHelper.FIRE, (x - 0.5) - random.x, (y + 0.2) + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);

                            // Bottom jets
                            player.level().addParticle(ParticleHelper.FIRE, (x + 0.5) - random.x, (y - 0.5) + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                            player.level().addParticle(ParticleHelper.FIRE, (x - 0.5) - random.x, (y - 0.5) + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                        }
                    }

                    if (player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get()))
                    {
                        Vec3 vec3 = player.getDeltaMovement();
                        double d0 = player.getX() - vec3.x;
                        double d1 = player.getY() - vec3.y;
                        double d2 = player.getZ() - vec3.z;
                        var count = Mth.clamp((int) (vec3.lengthSqr() * 4), 1, 4);
                        for (int j = 0; j < count; j++) {
                            Vec3 random = Utils.getRandomVec3(.25);
                            var f = j / ((float) count);
                            var x = Mth.lerp(f, d0, player.getX());
                            var y = Mth.lerp(f, d1, player.getY());
                            var z = Mth.lerp(f, d2, player.getZ());

                            player.level().addParticle(ParticleHelper.FIRE, (x + 0.7) - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                            player.level().addParticle(ParticleHelper.FIRE, (x - 0.7) - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                            player.level().addParticle(ParticleHelper.FIRE, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                        }
                    }
                }
            }

            // Keybind abilities
            if (
                    player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_SPEED_HELMET.get() ||
                    player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_POWER_HELMET.get() ||
                    player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get()
            )
            {
                if (pLevel.isClientSide())
                {
                    if (Cataclysm.PROXY.getClientSidePlayer() == pEntity && Cataclysm.PROXY.isKeyDown(5))
                    {
                        Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.HEAD.ordinal(), player.getId(), 5));
                        onKeyPacket(player, pStack, 5);
                    }
                }
            }

            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistries.EXCELSIUS_WARLOCK_BOOTS.get())
            {
                if (pLevel.isClientSide())
                {
                    if (Cataclysm.PROXY.getClientSidePlayer() == pEntity && Cataclysm.PROXY.isKeyDown(7))
                    {
                        Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.FEET.ordinal(), player.getId(), 7));
                        onKeyPacket(player, pStack, 7);
                    }
                }
            }
        }
    }

    // Keybind stuff
    @Override
    public void onKeyPacket(Player player, ItemStack itemStack, int i) {
        if (i == 5)
        {
            if (
                    player != null && !player.getCooldowns().isOnCooldown(ItemRegistries.EXCELSIUS_SPEED_HELMET.get()) ||
                    player != null && !player.getCooldowns().isOnCooldown(ItemRegistries.EXCELSIUS_POWER_HELMET.get()) ||
                    player != null && !player.getCooldowns().isOnCooldown(ItemRegistries.EXCELSIUS_RESIST_HELMET.get())
            )
            {
                boolean flag = false;
                List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(24));
                for (Entity entities : list)
                {
                    if (entities instanceof LivingEntity livingEntity)
                    {
                        flag = true;
                        // 10 glowing, 15 for sniper
                        livingEntity.addEffect(new MobEffectInstance(MobEffectRegistry.GUIDING_BOLT.get(), 200));
                        player.addEffect(new MobEffectInstance(CSPotionEffectRegistry.SNIPER_EFFECT.get(), 300));
                    }
                }

                if (flag)
                {
                    player.getCooldowns().addCooldown(ItemRegistries.EXCELSIUS_SPEED_HELMET.get(), 200);
                    player.getCooldowns().addCooldown(ItemRegistries.EXCELSIUS_POWER_HELMET.get(), 200);
                    player.getCooldowns().addCooldown(ItemRegistries.EXCELSIUS_RESIST_HELMET.get(), 200);
                }
            }
        }

        if (i == 7)
        {
            if (player != null && !player.getCooldowns().isOnCooldown(ItemRegistries.EXCELSIUS_WARLOCK_BOOTS.get()))
            {
                // Jump up into the air, burning nearby entities
                List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(5));
                for (Entity entities : list)
                {
                    if (entities instanceof LivingEntity livingEntity)
                    {
                        livingEntity.setSecondsOnFire(5);
                    }
                }

                Vec3 up = player.getLookAngle().multiply(1, 0, 1).normalize().add(0, 1, 0);

                player.setDeltaMovement(up.x, up.y, up.z);

                Vec3 vec3 = player.getDeltaMovement();
                double d0 = player.getX() - vec3.x;
                double d1 = player.getY() - vec3.y;
                double d2 = player.getZ() - vec3.z;
                var count = Mth.clamp((int) (vec3.lengthSqr() * 4), 1, 4);
                for (int j = 0; j < count; j++) {
                    Vec3 random = Utils.getRandomVec3(.25);
                    var f = j / ((float) count);
                    var x = Mth.lerp(f, d0, player.getX());
                    var y = Mth.lerp(f, d1, player.getY());
                    var z = Mth.lerp(f, d2, player.getZ());
                    player.level().addParticle(ParticleTypes.LARGE_SMOKE, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                    player.level().addParticle(ParticleHelper.EMBERS, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                }

                // 3 second cooldown, it's just jumping
                player.getCooldowns().addCooldown(ItemRegistries.EXCELSIUS_WARLOCK_BOOTS.get(), 60);
            }
        }
    }
}
