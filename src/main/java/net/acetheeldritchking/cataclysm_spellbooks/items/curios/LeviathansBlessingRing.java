package net.acetheeldritchking.cataclysm_spellbooks.items.curios;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.redspace.ironsspellbooks.compat.Curios;
import io.redspace.ironsspellbooks.item.curios.SimpleDescriptiveCurio;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

import java.util.UUID;

public class LeviathansBlessingRing extends SimpleDescriptiveCurio {
    public LeviathansBlessingRing() {
        super(ItemPropertiesHelper.equipment().stacksTo(1), Curios.RING_SLOT);
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attr = LinkedHashMultimap.create();
        attr.put(CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get(),
                new AttributeModifier(uuid, "Abyssal Spell Power", 0.2, AttributeModifier.Operation.MULTIPLY_TOTAL));

        return attr;
    }

    @Override
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        super.curioTick(slotContext, stack);
        slotContext.entity().removeEffect(ModEffect.EFFECTABYSSAL_FEAR.get());
        slotContext.entity().removeEffect(ModEffect.EFFECTABYSSAL_BURN.get());
        slotContext.entity().removeEffect(ModEffect.EFFECTABYSSAL_CURSE.get());
    }
}
