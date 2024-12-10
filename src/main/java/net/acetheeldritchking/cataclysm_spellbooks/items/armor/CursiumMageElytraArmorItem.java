package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.config.CMConfig;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.*;
import mod.azure.azurelib.core.object.PlayState;
import mod.azure.azurelib.util.AzureLibUtil;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.CursiumMageElytraArmorRenderer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ElytraItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class CursiumMageElytraArmorItem extends ImbuableCataclysmArmor {
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    public CursiumMageElytraArmorItem(EquipmentSlot slot, Properties settings) {
        super(CSArmorMaterials.CURSIUM_WARLOCK_ARMOR, slot, settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private CursiumMageElytraArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null)
                    renderer = new CursiumMageElytraArmorRenderer();
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
        if (this.slot == EquipmentSlot.CHEST) {
            tooltip.add(Component.translatable("item.cataclysm.cursium_chestplate.desc").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm.cursium_chestplate.desc2").withStyle(ChatFormatting.DARK_GREEN));
            tooltip.add(Component.translatable("item.cataclysm.cursium_chestplate.desc3").withStyle(ChatFormatting.DARK_GREEN));
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
    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    // Animated armor based on conditions
    // Thank you Noah for showing me how to do this all those months back <3
    private PlayState wings(AnimationState animationState)
    {
        Player player = Minecraft.getInstance().player;

        // Flight
        if (player != null && (player.getAbilities().flying || player.isFallFlying() && !player.isOnGround()))
        {
            //System.out.println("Flight");
            animationState.getController().setAnimation(RawAnimation.begin().then("flying", Animation.LoopType.LOOP));
        }
        // Idle
        else if (player != null && !(player.getAbilities().flying || player.isFallFlying() && player.isOnGround()))
        {
            //System.out.println("Idle");
            animationState.getController().setAnimation(RawAnimation.begin().then("idle", Animation.LoopType.LOOP));
        }
        // Sneaking
        else if (player != null && player.isCrouching())
        {
            //System.out.println("Sneaking");
            animationState.getController().setAnimation(RawAnimation.begin().then("fall_fly", Animation.LoopType.LOOP));
        }

        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(this, "cursium_elytra_armor", 10, this::wings));
    }
}
