package net.acetheeldritchking.cataclysm_spellbooks.items.staffs;

import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.IPresetSpellContainer;
import io.redspace.ironsspellbooks.api.spells.ISpellContainer;
import io.redspace.ironsspellbooks.api.spells.SpellData;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ImbuableStaffItem extends StaffItem implements IPresetSpellContainer {
    List<SpellData> spellData = null;
    SpellDataRegistryHolder[] spellDataRegistryHolders;

    public ImbuableStaffItem(Properties properties, double attackDamage, double attackSpeed, Map<Attribute, AttributeModifier> additionalAttributes, SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(properties, attackDamage, attackSpeed, additionalAttributes);
        this.spellDataRegistryHolders = spellDataRegistryHolders;
    }

    public List<SpellData> getSpells()
    {
        if (this.spellData == null)
        {
            this.spellData = Arrays.stream(this.spellDataRegistryHolders).map(SpellDataRegistryHolder::getSpellData).toList();
            this.spellDataRegistryHolders = null;
        }

        return this.spellData;
    }

    @Override
    public void initializeSpellContainer(ItemStack itemStack) {
        if (itemStack == null) {
            return;
        }

        if (!ISpellContainer.isSpellContainer(itemStack)) {
            var spells = getSpells();
            var spellContainer = ISpellContainer.create(spells.size(), true, false);
            spells.forEach(spellData -> spellContainer.addSpell(spellData.getSpell(), spellData.getLevel(), true, null));
            spellContainer.save(itemStack);
        }
    }
}
