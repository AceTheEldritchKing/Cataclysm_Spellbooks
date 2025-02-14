package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.config.CMConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.message.MessageArmorKey;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.BloomStoneMageArmorRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class BloomStoneMageArmorItem extends ImbuableCataclysmArmor {
    public BloomStoneMageArmorItem(EquipmentSlot slot, Properties settings) {
        super(CSArmorMaterials.BOULDER_BLOSSOM_ARMOR, slot, settings);
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            private BloomStoneMageArmorRenderer renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if (renderer == null)
                    renderer = new BloomStoneMageArmorRenderer();
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public void inventoryTick(ItemStack pStack, Level pLevel, Entity pEntity, int pSlotId, boolean pIsSelected) {
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);

        if (pEntity instanceof Player player)
        {
            if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.BLOOM_STONE_CHESTPLATE.get())
            {
                if (pLevel.isClientSide)
                {
                    if (Cataclysm.PROXY.getClientSidePlayer() == pEntity && Cataclysm.PROXY.isKeyDown(6))
                    {
                        Cataclysm.sendMSGToServer(new MessageArmorKey(EquipmentSlot.CHEST.ordinal(), player.getId(), 6));
                        onKeyPressedPacket(player, pStack, 6);
                    }
                }
            }
        }
    }

    public void onKeyPressedPacket(Player player, ItemStack itemStack, int type)
    {
        if (player != null && !player.getCooldowns().isOnCooldown(this))
        {
            for (int i = 0; i < 8; i++)
            {
                float angle = i * Mth.PI/4F;
                double sX = player.getX() + (Mth.cos(angle) * 1);
                double sY = player.getX() + (player.getBbHeight() * 0.5D);
                double sZ = player.getX() + (Mth.sin(angle) * 1);

                double vX = Mth.cos(angle);
                double vY = 0 + player.getRandom().nextFloat() * 0.3F;
                double vZ = Mth.sin(angle);
                double v3 = Mth.sqrt((float) (vX * vY * vZ));

                Amethyst_Cluster_Projectile_Entity amethystClusterProjectile =
                        new Amethyst_Cluster_Projectile_Entity(ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), player.level, player, (float) CMConfig.AmethystClusterdamage);

                amethystClusterProjectile.moveTo(sX, sY, sZ, i * 11.25F, player.getXRot());
                float speed = 0.8F;
                amethystClusterProjectile.shoot(vX, vY + v3 * 0.2D, vZ, speed, 1.0F);

                player.level.addFreshEntity(amethystClusterProjectile);
            }

            player.getCooldowns().addCooldown(ItemRegistries.BLOOM_STONE_CHESTPLATE.get(), 240);
        }
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
}
