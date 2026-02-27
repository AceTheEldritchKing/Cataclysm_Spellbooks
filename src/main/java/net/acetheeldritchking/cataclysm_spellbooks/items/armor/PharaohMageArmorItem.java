package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

public class PharaohMageArmorItem extends ImbuableCataclysmArmor {
    public PharaohMageArmorItem(Type slot, Properties settings) {
        super(CSArmorMaterials.PHARAOH_MAGE_ARMOR, slot, settings);
    }

    // Durability
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
