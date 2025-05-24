package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.spell_books.SimpleAttributeSpellBook;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import mod.azure.azurelib.AzureLib;
import net.acetheeldritchking.cataclysm_spellbooks.items.custom.CSItemDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DiscDriverSpellbook extends SimpleAttributeSpellBook {
    public final CSItemDispatcher dispatcher;

    public DiscDriverSpellbook(Multimap<Attribute, AttributeModifier> defaultModifiers) {
        super(12, SpellRarity.LEGENDARY, defaultModifiers, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1));
        this.dispatcher = new CSItemDispatcher();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (stack.getOrCreateTag().contains(AzureLib.ITEM_UUID_TAG))
        {
            if (!level.isClientSide && entity instanceof Player player )
            {
                dispatcher.idle(player, stack);
            }
        }
    }
}
