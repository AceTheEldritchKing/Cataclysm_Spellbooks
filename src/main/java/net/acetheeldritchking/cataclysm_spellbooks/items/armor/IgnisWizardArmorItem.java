package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.IgnisWizardArmorRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Consumer;

public class IgnisWizardArmorItem extends ImbuableCataclysmArmor{
    public IgnisWizardArmorItem(ArmorItem.Type slot, Properties settings) {
        super(CSArmorMaterials.IGNITIUM_WIZARD_ARMOR, slot, settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private IgnisWizardArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null)
                    renderer = new IgnisWizardArmorRenderer();
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
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
        if (this.getEquipmentSlot() == EquipmentSlot.HEAD) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_helmet.desc").withStyle(ChatFormatting.DARK_GREEN));
        }

        if (this.getEquipmentSlot() == EquipmentSlot.CHEST) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_chestplate.desc").withStyle(ChatFormatting.DARK_GREEN));
        }

        if (this.getEquipmentSlot() == EquipmentSlot.LEGS) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_leggings.desc").withStyle(ChatFormatting.DARK_GREEN));
        }

        if (this.getEquipmentSlot() == EquipmentSlot.FEET) {
            pTooltipComponents.add(Component.translatable("item.cataclysm.ignitium_boots.desc").withStyle(ChatFormatting.DARK_GREEN));
        }
    }
}
