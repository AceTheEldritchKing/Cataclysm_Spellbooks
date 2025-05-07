package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import io.redspace.ironsspellbooks.item.armor.UpgradeType;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;

import java.util.Optional;
import java.util.function.Supplier;

public enum CSUpgradeTypes implements UpgradeType {
    ABYSSAL_SPELL_POWER("abyssal_power", ItemRegistries.ABYSSAL_UPGRADE_ORB ,CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get(), AttributeModifier.Operation.MULTIPLY_BASE, 0.05f),
    TECHNOMANCY_SPELL_POWER("technomancy_power", ItemRegistries.ABYSSAL_UPGRADE_ORB ,CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), AttributeModifier.Operation.MULTIPLY_BASE, 0.05f),
    ;

    final Attribute attribute;
    final AttributeModifier.Operation operation;
    final float amountPerUpgrade;
    final ResourceLocation id;
    final Optional<Supplier<Item>> containerItem;

    CSUpgradeTypes(String key, Supplier<Item> containerItem, Attribute attribute, AttributeModifier.Operation operation, float amountPerUpgrade) {
        this(key, Optional.of(containerItem), attribute, operation, amountPerUpgrade);
    }

    CSUpgradeTypes(String key, Optional<Supplier<Item>> containerItem, Attribute attribute, AttributeModifier.Operation operation, float amountPerUpgrade) {
        this.id = CataclysmSpellbooks.id(key);
        this.attribute = attribute;
        this.operation = operation;
        this.amountPerUpgrade = amountPerUpgrade;
        this.containerItem = containerItem;
        UpgradeType.registerUpgrade(this);
    }

    @Override
    public Attribute getAttribute() {
        return attribute;
    }

    @Override
    public AttributeModifier.Operation getOperation() {
        return operation;
    }

    @Override
    public float getAmountPerUpgrade() {
        return amountPerUpgrade;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public Optional<Supplier<Item>> getContainerItem() {
        return containerItem;
    }
}
