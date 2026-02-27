package net.acetheeldritchking.cataclysm_spellbooks.items.custom;

import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BurningManuscriptItem extends Item {
    public BurningManuscriptItem() {
        super(ItemPropertiesHelper.material().stacksTo(1));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        if (Screen.hasShiftDown())
        {
            pTooltipComponents.add(Component.translatable("item.cataclysm_spellbooks.burning_manuscript_description").
                    withStyle(ChatFormatting.GOLD));
            pTooltipComponents.add(Component.translatable("item.cataclysm_spellbooks.burning_manuscript_description_2").
                    withStyle(ChatFormatting.GOLD));
        } else
        {
            pTooltipComponents.add(Component.translatable("item.cataclysm_spellbooks.more_details").withStyle(ChatFormatting.GRAY));
        }
    }
}
