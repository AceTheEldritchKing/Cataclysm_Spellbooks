package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;

public class ImbuableCataclysmArmor extends CSArmorItem implements IPresetSpellContainer {
    public ImbuableCataclysmArmor(CSArmorMaterials materialIn, ArmorItem.Type slot, Properties settings) {
        super(materialIn, slot, settings);
    }

    @Override
    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (itemStack.getItem() instanceof CSArmorItem armorItem)
        {
            if (armorItem.getType().getSlot() == EquipmentSlot.CHEST || armorItem.getType().getSlot() == EquipmentSlot.HEAD)
            {
                if (!ISpellContainer.isSpellContainer(itemStack)) {
                    var spellContainer = ISpellContainer.create(1, true, true);
                    spellContainer.save(itemStack);
                }
            }
        }
    }
}
