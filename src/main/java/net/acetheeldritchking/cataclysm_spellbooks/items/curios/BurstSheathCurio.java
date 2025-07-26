package net.acetheeldritchking.cataclysm_spellbooks.items.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class BurstSheathCurio extends SheathCurioItem {
    public BurstSheathCurio() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), null);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(),
                new AttributeModifier(uuid, "Technomancy Spell Power", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attr.put(AttributeRegistry.COOLDOWN_REDUCTION.get(),
                new AttributeModifier(uuid, "Cooldown Reduction", 0.10, AttributeModifier.Operation.MULTIPLY_TOTAL));

        return attr;
    }
}
