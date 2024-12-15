package net.acetheeldritchking.cataclysm_spellbooks.items.staffs;

import io.redspace.ironsspellbooks.api.item.curios.AffinityData;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
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

public class BloomStoneStaff extends StaffItem {
    public BloomStoneStaff() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 3, -3,
                Map.of(
                        AttributeRegistry.NATURE_SPELL_POWER.get(),
                        new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .15, AttributeModifier.Operation.MULTIPLY_BASE),
                        AttributeRegistry.COOLDOWN_REDUCTION.get(),
                        new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE)
                ));
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
        var affinityData = AffinityData.getAffinityData(pStack);
        var spell = affinityData.getSpell();
        if (spell != SpellRegistry.none())
        {
            AffinityData.setAffinityData(pStack, SpellRegistries.AMETHYST_PUNCTURE.get());

            pTooltipComponents.add(Component.translatable("tooltip.irons_spellbooks.enhance_spell_level",
                    spell.getDisplayName(MinecraftInstanceHelper.instance.player()).withStyle(spell.getSchoolType().getDisplayName().getStyle())).withStyle(ChatFormatting.YELLOW));
        }
    }
}
