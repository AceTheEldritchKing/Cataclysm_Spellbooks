package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Map;
import java.util.function.Supplier;

public enum CSArmorMaterials implements ArmorMaterial {
    // Ignis Wizard Armor
    IGNITIUM_WIZARD_ARMOR("ignis_armor", 45, new int[]{5, 8, 10, 5}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.IGNITIUM_INGOT.get()), Map.of(
                    AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
                    AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier("Fire power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
                    AttributeRegistry.FIRE_MAGIC_RESIST.get(), new AttributeModifier("Fire resist", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
                    )),

    // Abyssal Warlock Armor
    ABYSSAL_WARLOCK_ARMOR("abyssal_warlock_armor", 35, new int[]{5, 8, 10, 5}, 20, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F,
            () -> Ingredient.of(ItemRegistry.MAGIC_CLOTH.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get(), new AttributeModifier("Abyssal power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.ABYSSAL_MAGIC_RESIST.get(), new AttributeModifier("Abyssal resist", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
                    )),

    // Cursium Warlock Armor
    CURSIUM_WARLOCK_ARMOR("cursium_mage_armor", 45, new int[]{5, 8, 10, 5}, 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.CURSIUM_INGOT.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier("Ice power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.ICE_MAGIC_RESIST.get(), new AttributeModifier("Ice resist", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    ));

    // Pharaoh Mage Armor

    // Boulder Blossom Armor

    // Monstrous Wizard Hat

    // Technomancer Mage Armor

    // Excelsius Warlock Armor

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final int[] slotProtections;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;
    private final Map<Attribute, AttributeModifier> additionalAttributes;

    private CSArmorMaterials(String pName, int pDurabilityMultiplier, int[] pSlotProtections, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient, Map<Attribute, AttributeModifier> additionalAttributes)
    {
        this.name = pName;
        this.durabilityMultiplier = pDurabilityMultiplier;
        this.slotProtections = pSlotProtections;
        this.enchantmentValue = pEnchantmentValue;
        this.sound = pSound;
        this.toughness = pToughness;
        this.knockbackResistance = pKnockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
        this.additionalAttributes = additionalAttributes;
    }

    @Override
    public int getDurabilityForSlot(EquipmentSlot pSlot) {
        return HEALTH_PER_SLOT[pSlot.getIndex()] * this.durabilityMultiplier;
    }

    @Override
    public int getDefenseForSlot(EquipmentSlot pSlot) {
        return this.slotProtections[pSlot.getIndex()];
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.sound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    public Map<Attribute, AttributeModifier> getAdditionalAttributes() {
        return additionalAttributes;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
