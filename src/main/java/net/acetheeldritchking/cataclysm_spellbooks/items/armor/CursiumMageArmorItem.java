package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import com.github.L_Ender.cataclysm.items.KeybindUsingArmor;
import com.github.L_Ender.cataclysm.message.MessageArmorKey;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.CursiumMageArmorRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class CursiumMageArmorItem extends ImbuableCataclysmArmor implements KeybindUsingArmor {
    public CursiumMageArmorItem(ArmorItem.Type slot, Properties settings) {
        super(CSArmorMaterials.CURSIUM_WARLOCK_ARMOR, slot, settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private CursiumMageArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null)
                    renderer = new CursiumMageArmorRenderer();
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    // Compat with Cataclysm
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
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (this.getEquipmentSlot() == EquipmentSlot.HEAD) {
            tooltip.add(Component.translatable("item.cataclysm.cursium_helmet.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm.cursium_helmet.desc2", ModKeybind.HELMET_KEY_ABILITY.getTranslatedKeyMessage()).withStyle(ChatFormatting.DARK_GREEN));
        }
        if (this.getEquipmentSlot() == EquipmentSlot.CHEST) {
            tooltip.add(Component.translatable("item.cataclysm.cursium_chestplate.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm.cursium_chestplate.desc2").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm.cursium_chestplate.desc3").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm_spellbooks.cursium_chestplate.desc4").withStyle(ChatFormatting.DARK_GREEN));
        }
        if (this.getEquipmentSlot() == EquipmentSlot.LEGS) {
            tooltip.add(Component.translatable("item.cataclysm.cursium_leggings.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm.cursium_leggings.desc2").withStyle(ChatFormatting.DARK_GREEN));
        }
        if (this.getEquipmentSlot() ==  EquipmentSlot.FEET) {
            tooltip.add(Component.translatable("item.cataclysm.cursium_boots.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm.cursium_boots.desc2",ModKeybind.BOOTS_KEY_ABILITY.getTranslatedKeyMessage()).withStyle(ChatFormatting.DARK_GREEN));
        }
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pEntity instanceof Player player)
        {
            if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.CURSIUM_MAGE_HELMET.get())
            {
                if (pLevel.isClientSide())
                {
                    if (Cataclysm.PROXY.getClientSidePlayer() == pEntity && Cataclysm.PROXY.isKeyDown(5))
                    {
                        Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.HEAD.ordinal(), player.getId(), 5));
                        onKeyPacket(player, pStack, 5);
                    }
                }
            }
            if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistries.CURSIUM_MAGE_BOOTS.get())
            {
                if (pLevel.isClientSide())
                {
                    if (Cataclysm.PROXY.getClientSidePlayer() == pEntity && Cataclysm.PROXY.isKeyDown(7))
                    {
                        Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.FEET.ordinal(), player.getId(), 7));
                        onKeyPacket(player, pStack, 7);
                    }
                }
            }
        }
    }

    // Keybind stuff
    @Override
    public void onKeyPacket(Player player, ItemStack itemStack, int i) {
        if (i == 5)
        {
            if (player != null && !player.getCooldowns().isOnCooldown(ItemRegistries.CURSIUM_MAGE_HELMET.get()))
            {
                boolean flag = false;
                List<Entity> list = player.level().getEntities(player, player.getBoundingBox().inflate(24));
                for (Entity entities : list)
                {
                    if (entities instanceof LivingEntity livingEntity)
                    {
                        flag = true;
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.GLOWING, 160));
                    }
                }

                if (flag)
                {
                    player.getCooldowns().addCooldown(ItemRegistries.CURSIUM_MAGE_HELMET.get(), 200);
                }
            }
        }
        if (i == 7)
        {
            if (player != null && !player.getCooldowns().isOnCooldown(ItemRegistries.CURSIUM_MAGE_BOOTS.get()))
            {
                float speed = -1.8F;
                float yaw = (float) Math.toRadians(player.getYRot() + 90);
                Vec3 vec3 = player.getDeltaMovement().add(speed * Math.cos(yaw), 0, speed * Math.sin(yaw));

                player.setDeltaMovement(vec3.x, 0.4, vec3.z);
                player.getCooldowns().addCooldown(ItemRegistries.CURSIUM_MAGE_BOOTS.get(), 200);
            }
        }
    }
}
