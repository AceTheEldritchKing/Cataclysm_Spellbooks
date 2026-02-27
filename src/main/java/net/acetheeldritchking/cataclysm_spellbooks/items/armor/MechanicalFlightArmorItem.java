package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
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
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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


public class MechanicalFlightArmorItem extends ImbuableCataclysmArmor implements KeybindUsingArmor {
    public MechanicalFlightArmorItem(CSArmorMaterials materialIn, Type slot, Properties settings) {
        super(materialIn, slot, settings);
    }

    // Durability
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
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
            if (Screen.hasShiftDown())
            {
                tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc").withStyle(ChatFormatting.DARK_GREEN));
                tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc2", ModKeybind.BOOTS_KEY_ABILITY.getTranslatedKeyMessage()).withStyle(ChatFormatting.DARK_GREEN));
                tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc3", ModKeybind.BOOTS_KEY_ABILITY.getTranslatedKeyMessage()).withStyle(ChatFormatting.DARK_GREEN));
                tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc4", ModKeybind.BOOTS_KEY_ABILITY.getTranslatedKeyMessage()).withStyle(ChatFormatting.DARK_GREEN));
            } else
            {
                tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc").withStyle(ChatFormatting.DARK_GREEN));
                tooltip.add(Component.translatable("item.cataclysm_spellbooks.excelsius_boots.desc_shift").withStyle(ChatFormatting.DARK_GREEN));
            }
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pEntity instanceof Player player)
        {
            // Flight - we don't want to do cool fly if we are crouching
            if (
                    (this.type == Type.CHESTPLATE && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get()) && player.isFallFlying() ||
                    this.type == Type.CHESTPLATE && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get()) && player.isFallFlying() ||
                    this.type == Type.CHESTPLATE && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get()) && player.isFallFlying())
                    && !player.isCrouching()
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
                    player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_RESIST_HELMET.get()
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

                // Normal jump boost
                if (!player.isFallFlying() && !player.isCrouching())
                {
                    if (!isHovering())
                    {
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

                        player.level().playSound(player, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.55F, 1);
                    } else if (isHovering())
                    {
                        List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(5));
                        for (Entity entities : list)
                        {
                            if (entities instanceof LivingEntity livingEntity)
                            {
                                livingEntity.setSecondsOnFire(5);
                            }
                        }

                        Vec3 up = player.getLookAngle().multiply(0.2, 0, 0.2).normalize().add(0, 0.1, 0);

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

                        player.level().playSound(player, player.blockPosition(), SoundEvents.FIRECHARGE_USE, SoundSource.PLAYERS, 0.55F, 0.75F);
                    }
                }
                // Sonic Burst
                else if (player.isFallFlying() && !player.isCrouching())
                {
                    List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(5));
                    for (Entity entities : list)
                    {
                        if (entities instanceof LivingEntity livingEntity)
                        {
                            livingEntity.setSecondsOnFire(15);
                        }
                    }

                    // Particles
                    double x = player.getX();
                    double y = player.getY() + player.getBbHeight() / 2;
                    double z = player.getZ();

                    float yaw = (float) Math.toRadians(-player.getYRot());
                    float yaw2 = (float) Math.toRadians(-player.getYRot() + 180);
                    float pitch = (float) Math.toRadians(-player.getXRot());

                    player.level().addParticle(new RingParticle.RingData(yaw, pitch, 55, 0.80f, 0.4f, 0.0f, 1.0f, 50f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0, 0, 0);
                    player.level().addParticle(new RingParticle.RingData(yaw2, pitch, 55, 0.80f, 0.4f, 0.0f, 1.0f, 50f, false, RingParticle.EnumRingBehavior.GROW_THEN_SHRINK), x, y, z, 0, 0, 0);

                    player.level().playSound(player, player.blockPosition(), SoundEvents.DRAGON_FIREBALL_EXPLODE, SoundSource.PLAYERS, 0.55F, 1);
                }
                // Hover
                else if (!player.isFallFlying())
                {
                    if (player.isCrouching())
                    {
                        if (player.isNoGravity())
                        {
                            // display a message to the player
                            if (player instanceof ServerPlayer serverPlayer)
                            {
                                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("display.cataclysm_spellbooks.hover_disabled")
                                        .withStyle(s -> s.withColor(TextColor.fromRgb(0xF7D245)))));
                                serverPlayer.level().playSound(null , player.getX() , player.getY() , player.getZ() ,
                                        SoundEvents.EXPERIENCE_ORB_PICKUP , SoundSource.PLAYERS , 0.5f , 1.1f);
                            }

                            Vec3 down = player.getLookAngle().multiply(0, 0, 0).normalize().add(0, -0.1, 0);

                            player.setDeltaMovement(down.x, down.y, down.z);

                            player.setNoGravity(false);
                            setHovering(false);
                        } else
                        {
                            // display a message to the player
                            if (player instanceof ServerPlayer serverPlayer)
                            {
                                serverPlayer.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("display.cataclysm_spellbooks.hover_enabled")
                                        .withStyle(s -> s.withColor(TextColor.fromRgb(0xF7D245)))));
                                serverPlayer.level().playSound(null , player.getX() , player.getY() , player.getZ() ,
                                        SoundEvents.EXPERIENCE_ORB_PICKUP , SoundSource.PLAYERS , 0.5f , 1.1f);
                            }

                            player.setNoGravity(true);
                            setHovering(true);

                            Vec3 up = player.getLookAngle().multiply(0.2, 0, 0.2).normalize().add(0, 0.2, 0);

                            player.setDeltaMovement(up.x, up.y, up.z);
                        }

                        Vec3 vec3 = player.getDeltaMovement();

                        double d0 = player.getX() - vec3.x;
                        double d1 = player.getY() - vec3.y;
                        double d2 = player.getZ() - vec3.z;
                        var count = Mth.clamp((int) (vec3.lengthSqr() * 2), 1, 2);
                        for (int j = 0; j < count; j++) {
                            Vec3 random = Utils.getRandomVec3(.25);
                            var f = j / ((float) count);
                            var x = Mth.lerp(f, d0, player.getX());
                            var y = Mth.lerp(f, d1, player.getY());
                            var z = Mth.lerp(f, d2, player.getZ());
                            player.level().addParticle(ParticleTypes.LARGE_SMOKE, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                            player.level().addParticle(ParticleHelper.EMBERS, x - random.x, y + 0.5D - random.y, z - random.z, random.x * .5f, random.y * .5f, random.z * .5f);
                        }
                    }
                }

                // 3 second cooldown, it's just jumping
                player.getCooldowns().addCooldown(ItemRegistries.EXCELSIUS_WARLOCK_BOOTS.get(), 60);
            }
        }
    }

    public static boolean hover;

    public boolean isHovering()
    {
        return hover;
    }

    public void setHovering(boolean value)
    {
        hover = value;
    }
}
