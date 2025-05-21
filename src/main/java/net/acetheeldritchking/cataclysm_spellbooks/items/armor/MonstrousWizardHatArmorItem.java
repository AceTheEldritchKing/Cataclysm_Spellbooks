package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonstrousWizardHatArmorItem extends ImbuableCataclysmArmor {
    public MonstrousWizardHatArmorItem(EquipmentSlot slot, Properties settings) {
        super(CSArmorMaterials.MONSTROUS_WIZARD_ARMOR, slot, settings);
    }

    // Durability
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

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.cataclysm.monstrous_helm.desc").withStyle(ChatFormatting.DARK_GREEN));
        pTooltipComponents.add(Component.translatable("item.cataclysm.monstrous_helm2.desc").withStyle(ChatFormatting.DARK_GREEN));
    }
}
