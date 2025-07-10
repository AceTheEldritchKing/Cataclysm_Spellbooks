package net.acetheeldritchking.cataclysm_spellbooks.items.armor;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.item.armor.IronsExtendedArmorMaterial;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.minecraft.Util;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Supplier;

public enum CSArmorMaterials implements IronsExtendedArmorMaterial {
    // Ignis Wizard Armor
    IGNITIUM_WIZARD_ARMOR("ignis_armor", 45, warlockArmorMap(), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.IGNITIUM_INGOT.get()), Map.of(
                    AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
                    AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier("Fire power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
                    AttributeRegistry.FIRE_MAGIC_RESIST.get(), new AttributeModifier("Fire resist", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    // Abyssal Warlock Armor
    ABYSSAL_WARLOCK_ARMOR("abyssal_warlock_armor", 35, warlockArmorMap(), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 2.0F, 0.0F,
            () -> Ingredient.of(ItemRegistry.MAGIC_CLOTH.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get(), new AttributeModifier("Abyssal power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.ABYSSAL_MAGIC_RESIST.get(), new AttributeModifier("Abyssal resist", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    // Cursium Warlock Armor
    CURSIUM_WARLOCK_ARMOR("cursium_mage_armor", 45, warlockArmorMap(), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.CURSIUM_INGOT.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier("Ice power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.ICE_MAGIC_RESIST.get(), new AttributeModifier("Ice resist", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    // Pharaoh Mage Armor
    PHARAOH_MAGE_ARMOR("pharaoh_mage_armor", 45, warlockArmorMap(), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.ANCIENT_METAL_INGOT.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.NATURE_SPELL_POWER.get(), new AttributeModifier("Nature power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.HOLY_SPELL_POWER.get(), new AttributeModifier("Holy power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    // Boulder Blossom Armor
    BOULDER_BLOSSOM_ARMOR("boulder_blossom_armor", 45, warlockArmorMap(), 15, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.AMETHYST_CRAB_SHELL.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.NATURE_SPELL_POWER.get(), new AttributeModifier("Nature power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.NATURE_MAGIC_RESIST.get(), new AttributeModifier("Nature resist", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    // Monstrous Wizard Hat
    MONSTROUS_WIZARD_ARMOR("monstrous_wizard", 45, baseArmorMap(), 15, SoundEvents.ARMOR_EQUIP_LEATHER, 3.0F, 0.2F,
            () -> Ingredient.of(ModItems.MONSTROUS_HORN.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 100, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier("Fire power", 0.15, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.SPELL_RESIST.get(), new AttributeModifier("Spell resist", 0.05, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    // Technomancer Mage Armor
    ENGINEER_ARMOR("engineer_armor", 35, baseArmorMap(), 20, SoundEvents.ARMOR_EQUIP_LEATHER, 0.0F, 0.0F,
            () -> Ingredient.of(ItemRegistry.MAGIC_CLOTH.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 125, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier("Technomancy power", 0.1, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    // Excelsius Warlock Armor
    EXCELSIUS_WARLOCK_ARMOR("excelsius_warlock_armor", 45, warlockArmorMap(), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.WITHERITE_INGOT.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier("Technomancy power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    EXCELSIUS_COOLDOWN_ARMOR("excelsius_cooldown_warlock", 45, warlockArmorMap(), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.WITHERITE_INGOT.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier("Technomancy power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier("Cooldown", 0.15, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    EXCELSIUS_POWER_ARMOR("excelsius_power_warlock", 45, warlockArmorMap(), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.WITHERITE_INGOT.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 200, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.10, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier("Technomancy power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE)
    )),

    EXCELSIUS_RESIST_ARMOR("excelsius_resist_warlock", 45, warlockArmorMap(), 20, SoundEvents.ARMOR_EQUIP_NETHERITE, 3.0F, 0.1F,
            () -> Ingredient.of(ModItems.WITHERITE_INGOT.get()), Map.of(
            AttributeRegistry.MAX_MANA.get(), new AttributeModifier("Max mana", 150, AttributeModifier.Operation.ADDITION),
            AttributeRegistry.SPELL_POWER.get(), new AttributeModifier("Spell power", 0.05, AttributeModifier.Operation.MULTIPLY_BASE),
            CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier("Technomancy power", 0.2, AttributeModifier.Operation.MULTIPLY_BASE),
            AttributeRegistry.SPELL_RESIST.get(), new AttributeModifier("Spell resist", 0.15, AttributeModifier.Operation.MULTIPLY_BASE)
    ));

    private static final int[] HEALTH_PER_SLOT = new int[]{13, 15, 16, 11};
    private final String name;
    private final int durabilityMultiplier;
    private final EnumMap<ArmorItem.Type, Integer> protectionFunctionForType;
    private final int enchantmentValue;
    private final SoundEvent sound;
    private final float toughness;
    private final float knockbackResistance;
    private final LazyLoadedValue<Ingredient> repairIngredient;
    private final Map<Attribute, AttributeModifier> additionalAttributes;

    private CSArmorMaterials(String pName, int pDurabilityMultiplier, EnumMap<ArmorItem.Type, Integer> protectionMap, int pEnchantmentValue, SoundEvent pSound, float pToughness, float pKnockbackResistance, Supplier<Ingredient> pRepairIngredient, Map<Attribute, AttributeModifier> additionalAttributes)
    {
        this.name = pName;
        this.durabilityMultiplier = pDurabilityMultiplier;
        this.protectionFunctionForType = protectionMap;
        this.enchantmentValue = pEnchantmentValue;
        this.sound = pSound;
        this.toughness = pToughness;
        this.knockbackResistance = pKnockbackResistance;
        this.repairIngredient = new LazyLoadedValue<>(pRepairIngredient);
        this.additionalAttributes = additionalAttributes;
    }

    public static EnumMap<ArmorItem.Type, Integer> warlockArmorMap()
    {
        return makeArmorMap(5, 8, 10, 5);
    }

    public static EnumMap<ArmorItem.Type, Integer> baseArmorMap()
    {
        return makeArmorMap(3, 6, 8, 3);
    }

    public static EnumMap<ArmorItem.Type, Integer> makeArmorMap(int helmet, int chestplate, int leggings, int boots)
    {
        return Util.make(new EnumMap<>(ArmorItem.Type.class), (typeIntegerEnumMap) -> {
            typeIntegerEnumMap.put(ArmorItem.Type.HELMET, helmet);
            typeIntegerEnumMap.put(ArmorItem.Type.CHESTPLATE, chestplate);
            typeIntegerEnumMap.put(ArmorItem.Type.LEGGINGS, leggings);
            typeIntegerEnumMap.put(ArmorItem.Type.BOOTS, boots);
        });
    }

    public int getDurabilityForType(ArmorItem.Type p_266745_) {
        return HEALTH_FUNCTION_FOR_TYPE.get(p_266745_) * this.durabilityMultiplier;
    }

    private static EnumMap<ArmorItem.Type, Integer> HEALTH_FUNCTION_FOR_TYPE =
            Util.make(new EnumMap<>(ArmorItem.Type.class), (p_266653_) -> {
                p_266653_.put(ArmorItem.Type.BOOTS, 13);
                p_266653_.put(ArmorItem.Type.LEGGINGS, 15);
                p_266653_.put(ArmorItem.Type.CHESTPLATE, 16);
                p_266653_.put(ArmorItem.Type.HELMET, 11);
            });

    public int getDefenseForType(ArmorItem.Type p_266752_) {
        return this.protectionFunctionForType.get(p_266752_);
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
