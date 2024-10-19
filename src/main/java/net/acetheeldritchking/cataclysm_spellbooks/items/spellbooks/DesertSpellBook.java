package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import com.google.common.collect.ImmutableMultimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.UniqueSpellBook;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;
import java.util.function.Supplier;

public class DesertSpellBook extends UniqueSpellBook {
    public DesertSpellBook() {
        super(SpellRarity.EPIC, SpellDataRegistryHolder.of(
                new SpellDataRegistryHolder(SpellRegistries.MONOLITH_CRASH, 5),
                new SpellDataRegistryHolder(SpellRegistries.DESERT_WINDS, 3),
                new SpellDataRegistryHolder(SpellRegistries.SANDSTORM, 5)
        ), 7, () -> {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            // Nature Spell Power
            builder.put(AttributeRegistry.NATURE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon modifier", 0.20D, AttributeModifier.Operation.MULTIPLY_BASE));
            // Mana
            builder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon modifier", 200, AttributeModifier.Operation.ADDITION));
            return builder.build();
        });
    }
}
