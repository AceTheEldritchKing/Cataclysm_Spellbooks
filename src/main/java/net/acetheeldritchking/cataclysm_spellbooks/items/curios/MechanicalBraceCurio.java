package net.acetheeldritchking.cataclysm_spellbooks.items.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class MechanicalBraceCurio extends SimpleDescriptiveCurio {
    public MechanicalBraceCurio() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC).fireResistant(), Curios.RING_SLOT);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(),
                new AttributeModifier(uuid, "Technomancy Spell Power", 0.10, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attr.put(AttributeRegistry.SPELL_POWER.get(),
                new AttributeModifier(uuid, "Cooldown Reduction", 0.20, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attr.put(AttributeRegistry.MAX_MANA.get(),
                new AttributeModifier(uuid, "Mana Regen", 150, AttributeModifier.Operation.ADDITION));

        return attr;
    }
}
