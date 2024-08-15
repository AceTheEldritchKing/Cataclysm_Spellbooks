package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import mod.azure.azurelib.animatable.GeoItem;
import mod.azure.azurelib.core.animatable.instance.AnimatableInstanceCache;
import mod.azure.azurelib.core.animation.AnimatableManager;
import mod.azure.azurelib.core.animation.RawAnimation;
import mod.azure.azurelib.util.AzureLibUtil;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.item.GeoArmorItem;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Map;
import java.util.UUID;

public class CSArmorItem extends GeoArmorItem implements IAnimatable, GeoItem {
    private static final UUID[] ARMOR_ATTRIBUTE_UUID_PER_SLOT = new UUID[]
            {UUID.fromString("F7BFFA65-547A-49D2-8804-3D533070E432"),
                    UUID.fromString("B05AF2C0-5862-4CE6-860A-522C11E1571A"),
                    UUID.fromString("15C1FE6B-3596-412A-B6CF-4077CB37140F"),
                    UUID.fromString("82A575D1-366A-4BBD-91F8-25DB6B804F06")};
    private final Multimap<Attribute, AttributeModifier> ARMOR_ATTRIBUTES;
    private final AnimatableInstanceCache cache = AzureLibUtil.createInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        // Idk
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    public CSArmorItem(CSArmorMaterials materialIn, EquipmentSlot slot, Properties settings) {
        super(materialIn, slot, settings);
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        float defense = materialIn.getDefenseForSlot(slot);
        float toughness = materialIn.getToughness();
        float knockbackResistance = materialIn.getKnockbackResistance();
        UUID uuid = ARMOR_ATTRIBUTE_UUID_PER_SLOT[slot.getIndex()];
        builder.put(Attributes.ARMOR, new AttributeModifier(uuid, "Armor modifier",
                defense, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, "Armor toughness",
                toughness, AttributeModifier.Operation.ADDITION));
        if (knockbackResistance > 0)
        {
            builder.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, "Armor knockback resistance",
                    knockbackResistance, AttributeModifier.Operation.ADDITION));
        }
        for (Map.Entry<Attribute, AttributeModifier> modifierEntry : materialIn.getAdditionalAttributes().entrySet())
        {
            AttributeModifier atr = modifierEntry.getValue();
            atr = new AttributeModifier(uuid, atr.getName(), atr.getAmount(), atr.getOperation());
            builder.put(modifierEntry.getKey(), atr);
        }
        ARMOR_ATTRIBUTES = builder.build();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot pEquipmentSlot) {
        if (pEquipmentSlot == this.slot)
        {
            return ARMOR_ATTRIBUTES;
        }
        else
        {
            return ImmutableMultimap.of();
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<CSArmorItem>(this, "controller", 20, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event)
    {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", true));
        return PlayState.CONTINUE;
    }
}
