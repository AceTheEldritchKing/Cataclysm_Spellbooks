package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import net.minecraft.world.item.ItemStack;

public class ImbuableCataclysmArmor extends CSArmorItem implements IPresetSpellContainer {
    public ImbuableCataclysmArmor(CSArmorMaterials materialIn, Type slot, Properties settings) {
        super(materialIn, slot, settings);
    }

    @Override
    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (itemStack.getItem() instanceof CSArmorItem armorItem)
        {
            if (armorItem.getType() == Type.CHESTPLATE || armorItem.getType() == Type.HELMET)
            {
                if (!ISpellContainer.isSpellContainer(itemStack)) {
                    var spellContainer = ISpellContainer.create(1, true, true);
                    spellContainer.save(itemStack);
                }
            }
        }
    }
}
