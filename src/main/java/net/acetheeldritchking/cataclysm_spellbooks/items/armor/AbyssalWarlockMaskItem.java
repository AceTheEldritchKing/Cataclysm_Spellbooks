package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import net.minecraft.world.item.ItemStack;

public class AbyssalWarlockMaskItem extends ImbuableCataclysmArmor {
	public AbyssalWarlockMaskItem(Type slot, Properties settings) {
		super(CSArmorMaterials.ABYSSAL_WARLOCK_ARMOR, slot, settings);
	}

	// Durability
	@Override
	public boolean isDamageable(ItemStack stack) {
		return false;
	}
}
