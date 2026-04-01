package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import net.minecraft.world.item.ItemStack;

public class BloomStoneMageArmorItem extends ImbuableCataclysmArmor {
	public BloomStoneMageArmorItem(Type slot, Properties settings) {
		super(CSArmorMaterials.BOULDER_BLOSSOM_ARMOR, slot, settings);
	}

	// Durability
	@Override
	public boolean isDamageable(ItemStack stack) {
		return false;
	}
}
