package net.acetheeldritchking.cataclysm_spellbooks.items.weapons;

import com.github.L_Ender.cataclysm.config.CMConfig;
import io.redspace.ironsspellbooks.api.item.weapons.MagicSwordItem;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.item.UniqueItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MurasamaBladeItem extends MagicSwordItem implements UniqueItem {
    public MurasamaBladeItem(SpellDataRegistryHolder[] spellDataRegistryHolders) {
        super(CSWeaponTiers.MURASAMA, CSWeaponTiers.MURASAMA.getAttackDamageBonus(), CSWeaponTiers.MURASAMA.getSpeed(),
                spellDataRegistryHolders,
                Map.of(
                        CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier(UUID.fromString("8de758f0-bfbf-4ff9-a2eb-b14da9ed1309"), "Technomancy Spell Power", 0.20f, AttributeModifier.Operation.MULTIPLY_TOTAL),
                        AttributeRegistry.BLOOD_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("c0af0076-6028-4521-a9f5-66f7501cc758"), "Blood Spell Power", 0.15f, AttributeModifier.Operation.MULTIPLY_TOTAL),
                        AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("fc7c1cba-586a-4ebc-a189-f5378747ab1b"), "Cooldown Reduction", 0.10f, AttributeModifier.Operation.MULTIPLY_TOTAL)
                ), ItemPropertiesHelper.equipment(1).rarity(Rarity.EPIC));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> tooltip, TooltipFlag pIsAdvanced) {
        tooltip.add(Component.translatable("item.cataclysm_spellbooks.murasama_blade.desc").withStyle(ChatFormatting.DARK_RED).withStyle(ChatFormatting.ITALIC));
    }

    // Durability
    @Override
    public boolean isDamageable(ItemStack stack) {
        return false;
    }
}
