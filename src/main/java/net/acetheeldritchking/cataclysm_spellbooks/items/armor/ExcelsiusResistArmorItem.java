package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class ExcelsiusResistArmorItem extends MechanicalFlightArmorItem {
    public ExcelsiusResistArmorItem(EquipmentSlot slot, Properties settings) {
        super(CSArmorMaterials.EXCELSIUS_RESIST_ARMOR, slot, settings);
    }

    @Override
    public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
        if (this.slot == EquipmentSlot.CHEST)
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
        if (this.slot == EquipmentSlot.CHEST)
        {
            return ElytraItem.isFlyEnabled(stack);
        }
        else
        {
            return false;
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pEntity instanceof Player player)
        {
            if (this.slot == EquipmentSlot.CHEST && player.getItemBySlot(EquipmentSlot.CHEST).is(ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get()) && player.isFallFlying())
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
            }
        }
    }
}
