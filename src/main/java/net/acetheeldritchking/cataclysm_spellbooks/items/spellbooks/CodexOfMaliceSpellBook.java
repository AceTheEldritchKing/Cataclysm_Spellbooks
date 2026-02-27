package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CodexOfMaliceSpellBook extends SpellBook {
    public CodexOfMaliceSpellBook() {
        super(12, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1));
        withSpellbookAttributes(
                new AttributeContainer(AttributeRegistry.MAX_MANA, 300, AttributeModifier.Operation.ADDITION),
                new AttributeContainer(AttributeRegistry.ICE_SPELL_POWER, 0.30F, AttributeModifier.Operation.MULTIPLY_BASE)
        );
    }
}
