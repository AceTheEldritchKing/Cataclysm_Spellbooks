package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import com.google.common.collect.ImmutableMultimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public class DesertSpellBook extends UniqueSpellBook {
    public DesertSpellBook() {
        super(SpellDataRegistryHolder.of(
                new SpellDataRegistryHolder(SpellRegistries.MONOLITH_CRASH, 5),
                new SpellDataRegistryHolder(SpellRegistries.DESERT_WINDS, 3),
                new SpellDataRegistryHolder(SpellRegistries.SANDSTORM, 3),
                new SpellDataRegistryHolder(SpellRegistries.THOTHS_WITNESS, 1),
                new SpellDataRegistryHolder(SpellRegistries.PHARAOHS_WRATH, 3)
        ), 7);
        withSpellbookAttributes(
                new AttributeContainer(AttributeRegistry.MAX_MANA, 300, AttributeModifier.Operation.ADDITION),
                new AttributeContainer(AttributeRegistry.NATURE_SPELL_POWER, 0.30F, AttributeModifier.Operation.MULTIPLY_BASE),
                new AttributeContainer(AttributeRegistry.HOLY_SPELL_POWER, 0.20F, AttributeModifier.Operation.MULTIPLY_BASE)
        );
    }
}
