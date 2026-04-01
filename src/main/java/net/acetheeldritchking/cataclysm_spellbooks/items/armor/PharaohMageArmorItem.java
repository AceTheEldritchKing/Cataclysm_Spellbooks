package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

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
