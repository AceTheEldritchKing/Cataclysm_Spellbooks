package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.AbyssalWarlockArmorRenderer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AbyssalWarlockArmorItem extends ImbuableCataclysmArmor{
    public AbyssalWarlockArmorItem(ArmorItem.Type slot, Properties settings) {
        super(CSArmorMaterials.ABYSSAL_WARLOCK_ARMOR, slot, settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private AbyssalWarlockArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null)
                    renderer = new AbyssalWarlockArmorRenderer();
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }
}
