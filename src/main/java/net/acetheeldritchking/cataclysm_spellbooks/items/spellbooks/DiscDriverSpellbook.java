package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.item.weapons.AttributeContainer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import mod.azure.azurelib.AzureLib;
import net.acetheeldritchking.cataclysm_spellbooks.items.custom.CSItemDispatcher;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class DiscDriverSpellbook extends SpellBook {
    public final CSItemDispatcher dispatcher;

    public DiscDriverSpellbook() {
        //super(12, SpellRarity.LEGENDARY, defaultModifiers, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1));
        super(15, ItemPropertiesHelper.equipment().fireResistant().stacksTo(1));
        withSpellbookAttributes(
                new AttributeContainer(AttributeRegistry.MAX_MANA, 300, AttributeModifier.Operation.ADDITION),
                new AttributeContainer(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER, 0.30F, AttributeModifier.Operation.MULTIPLY_BASE)
        );
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        pTooltipComponents.add(Component.translatable("item.cataclysm_spellbooks.disc_driver_description").
                withStyle(ChatFormatting.GOLD));
    }
}
