package net.acetheeldritchking.cataclysm_spellbooks.items.weapons;

import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.render.SpecialItemRenderer;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;

import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class MonstrousFlambergeItem extends MagicSwordItem {
    public MonstrousFlambergeItem(SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(CSWeaponTiers.MONSTROUS_FLAMBERGE, CSWeaponTiers.MONSTROUS_FLAMBERGE.getAttackDamageBonus(), CSWeaponTiers.MONSTROUS_FLAMBERGE.getSpeed(),
                spellDataRegistryHolders,
                Map.of(
                        Attributes.ARMOR, new AttributeModifier(UUID.fromString("c552273e-6669-4cd2-80b3-a703b7616336"), "Weapon Modifier", 5, AttributeModifier.Operation.ADDITION),
                        AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("c552273e-6669-4cd2-80b3-a703b7616336"), "Fire Spell Power", 0.05f, AttributeModifier.Operation.MULTIPLY_TOTAL)
                ), ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC));
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        consumer.accept(new IClientItemExtensions() {
            @Override
            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return new SpecialItemRenderer(Minecraft.getInstance().getItemRenderer(), Minecraft.getInstance().getEntityModels(), "monstrous_flamberge");
            }
        });
    }
}
