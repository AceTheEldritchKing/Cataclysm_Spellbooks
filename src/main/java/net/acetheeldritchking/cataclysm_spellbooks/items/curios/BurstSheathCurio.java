package net.acetheeldritchking.cataclysm_spellbooks.items.curios;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

@Mod.EventBusSubscriber
public class BurstSheathCurio extends SheathCurioItem {
    public static final int COOLDOWN = 15 * 20;

    public BurstSheathCurio() {
        super(ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), null);
    }

    protected int getCooldownTicks() {
        return COOLDOWN;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(),
                new AttributeModifier(uuid, "Technomancy Spell Power", 0.10, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attr.put(AttributeRegistry.BLOOD_SPELL_POWER.get(),
                new AttributeModifier(uuid, "Blood Spell Power", 0.15, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attr.put(AttributeRegistry.SPELL_POWER.get(),
                new AttributeModifier(uuid, "Spell Power", 0.05, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attr.put(AttributeRegistry.COOLDOWN_REDUCTION.get(),
                new AttributeModifier(uuid, "Cooldown Reduction", 0.10, AttributeModifier.Operation.MULTIPLY_TOTAL));
        attr.put(AttributeRegistry.MANA_REGEN.get(),
                new AttributeModifier(uuid, "Mana Regen", 0.10, AttributeModifier.Operation.MULTIPLY_TOTAL));

        return attr;
    }

    // Honestly kinda backporting passive ability curios for this...
    public boolean tryProcCooldown(Player player) {
        if (player.getCooldowns().isOnCooldown(this)) {
            return false;
        } else {
            player.getCooldowns().addCooldown(this, getCooldownTicks(player));
            return true;
        }
    }

    public int getCooldownTicks(@Nullable LivingEntity livingEntity) {
        return this.getCooldownTicks();
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("item.cataclysm_spellbooks.burst_sheath.desc").withStyle(ChatFormatting.DARK_GREEN));
    }

    @SubscribeEvent
    public static void handleAbility(LivingDamageEvent event)
    {
        var sheath = ((BurstSheathCurio) ItemRegistries.BURST_SHEATH.get());
        Entity entity = event.getSource().getEntity();

        if (entity instanceof ServerPlayer player)
        {
            if (sheath.isEquippedBy(player))
            {
                if (sheath.tryProcCooldown(player))
                {
                    float baseAmount = event.getAmount();
                    event.setAmount(baseAmount * 2);
                    //CataclysmSpellbooks.LOGGER.debug("Damage: " + event.getAmount());
                }
            }
        }
    }
}
