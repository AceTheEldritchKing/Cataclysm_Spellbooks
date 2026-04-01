package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class IgnisWizardElytraArmorItem extends ImbuableCataclysmArmor {

	public IgnisWizardElytraArmorItem(Type slot, Properties settings) {
		super(CSArmorMaterials.IGNITIUM_WIZARD_ARMOR, slot, settings);
	}

	// Durability
	@Override
	public boolean isDamageable(ItemStack stack) {
		return false;
	}

	// Using the same stuff as Cataclysm for tooltips
	@Override
	public void appendHoverText(ItemStack pStack, @Nullable Level pLevel,
			@NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
		if (this.type == Type.HELMET) {
			pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_helmet.desc")
					.withStyle(ChatFormatting.DARK_GREEN));
		}

		if (this.type == Type.LEGGINGS) {
			pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_leggings.desc")
					.withStyle(ChatFormatting.DARK_GREEN));
		}

		if (this.type == Type.BOOTS) {
			pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_boots.desc")
					.withStyle(ChatFormatting.DARK_GREEN));
		}
	}

	@Override
	public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
		return true;
	}

	@Override
	public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
		return ElytraItem.isFlyEnabled(stack);
	}

	// Azurelib
}
