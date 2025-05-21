package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.spell_books.SimpleAttributeSpellBook;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public class CodexOfMaliceSpellBook extends SimpleAttributeSpellBook {
    public CodexOfMaliceSpellBook(Multimap<Attribute, AttributeModifier> defaultModifiers) {
        super(12, SpellRarity.LEGENDARY, defaultModifiers, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1));
    }
}
