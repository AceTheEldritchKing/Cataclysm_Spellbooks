package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class MechanicalFlightArmorItem extends ImbuableCataclysmArmor {
    public MechanicalFlightArmorItem(CSArmorMaterials materialIn, EquipmentSlot slot, Properties settings) {
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
}
