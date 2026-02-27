package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

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
