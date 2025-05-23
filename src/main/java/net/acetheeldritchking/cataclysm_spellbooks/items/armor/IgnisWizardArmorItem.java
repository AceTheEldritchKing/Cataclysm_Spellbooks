package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IgnisWizardArmorItem extends ImbuableCataclysmArmor{
    public IgnisWizardArmorItem(EquipmentSlot slot, Properties settings) {
        super(CSArmorMaterials.IGNITIUM_WIZARD_ARMOR, slot, settings);
    }

    // Ignis Wizard Armor should have durability compat with Cataclysm?
    @Override
    public void setDamage(ItemStack stack, int damage) {
        if (CMConfig.Armor_Infinity_Durability)
        {
            super.setDamage(stack, 0);
        }
        else
        {
            super.setDamage(stack, damage);
        }
    }

    // Using the same stuff as Cataclysm for tooltips
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
        if (this.slot == EquipmentSlot.HEAD) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_helmet.desc").withStyle(ChatFormatting.DARK_GREEN));
        }

        if (this.slot == EquipmentSlot.CHEST) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_chestplate.desc").withStyle(ChatFormatting.DARK_GREEN));
        }

        if (this.slot == EquipmentSlot.LEGS) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_leggings.desc").withStyle(ChatFormatting.DARK_GREEN));
        }

        if (this.slot == EquipmentSlot.FEET) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_boots.desc").withStyle(ChatFormatting.DARK_GREEN));
        }
    }
}
