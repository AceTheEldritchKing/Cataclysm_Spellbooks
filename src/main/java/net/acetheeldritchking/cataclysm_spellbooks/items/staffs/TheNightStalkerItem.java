package net.acetheeldritchking.cataclysm_spellbooks.items.staffs;

import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import mod.azure.azurelib.AzureLib;
import net.acetheeldritchking.cataclysm_spellbooks.items.custom.CSItemDispatcher;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.UUID;

public class TheNightStalkerItem extends ImbuableStaffItem {
    public final CSItemDispatcher dispatcher;

    public TheNightStalkerItem(SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 10.5, -3.2,
                Map.of(
                        CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE),
                        AttributeRegistry.BLOOD_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE),
                        AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .15, AttributeModifier.Operation.MULTIPLY_BASE),
                        AttributeRegistry.SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .10, AttributeModifier.Operation.MULTIPLY_BASE)
                ), spellDataRegistryHolders);
        this.dispatcher = new CSItemDispatcher();
    }

    @Override
    public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
        if (stack.getOrCreateTag().contains(AzureLib.ITEM_UUID_TAG))
        {
            if (entity instanceof Player player)
            {
                if (!level.isClientSide)
                {
                    dispatcher.idle(player, stack);
                } else if (MagicData.getPlayerMagicData(player).isCasting())
                {
                    dispatcher.casting(player, stack);
                }
            }
        }
    }
}
