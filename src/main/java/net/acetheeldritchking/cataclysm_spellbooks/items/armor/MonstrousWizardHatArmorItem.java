package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MonstrousWizardHatArmorItem extends ImbuableCataclysmArmor {
	public MonstrousWizardHatArmorItem(Type slot, Properties settings) {
		super(CSArmorMaterials.MONSTROUS_WIZARD_ARMOR, slot, settings);
	}

	// Durability
	@Override
	public boolean isDamageable(ItemStack stack) {
		return false;
	}

	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents,
			TooltipFlag pIsAdvanced) {
		pTooltipComponents.add(Component.translatable("item.cataclysm.monstrous_helm.desc")
				.withStyle(ChatFormatting.DARK_GREEN));
		pTooltipComponents.add(Component.translatable("item.cataclysm.monstrous_helm2.desc")
				.withStyle(ChatFormatting.DARK_GREEN));
	}
}
