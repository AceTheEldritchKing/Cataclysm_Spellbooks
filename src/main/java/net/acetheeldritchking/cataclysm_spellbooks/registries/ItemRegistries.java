package net.acetheeldritchking.cataclysm_spellbooks.registries;

import com.google.common.collect.ImmutableMultimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.armor.UpgradeTypes;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.item.spell_books.SimpleAttributeSpellBook;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.AbyssalWarlockArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.AbyssalWarlockMaskItem;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.CSUpgradeTypes;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.items.curios.LeviathansBlessingRing;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CataclysmSpellbooks.MOD_ID);

    /***
     * Spellbooks
     */
    // Abyss Spellbook
    public static final RegistryObject<Item> ABYSS_SPELL_BOOK = ITEMS.register
            ("abyss_spell_book", () ->
            {
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 0.30, AttributeModifier.Operation.MULTIPLY_TOTAL));
                builder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 300, AttributeModifier.Operation.ADDITION));
                return new SimpleAttributeSpellBook(12, SpellRarity.LEGENDARY, builder.build(), ItemPropertiesHelper.equipment().fireResistant());
            });

    // Desert Spellbook - Dropped by ancient remnant
    public static final RegistryObject<Item> DESERT_SPELL_BOOK = ITEMS.register
            ("desert_spell_book", () -> new SimpleAttributeSpellBook
                    (10, SpellRarity.EPIC, AttributeRegistry.NATURE_SPELL_POWER.get(), 0.20, 150));

    // Ignis Spellbook
    public static final RegistryObject<Item> IGNIS_SPELL_BOOK = ITEMS.register
            ("ignis_spell_book", () ->
            {
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 0.30, AttributeModifier.Operation.MULTIPLY_TOTAL));
                builder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 300, AttributeModifier.Operation.ADDITION));
                return new SimpleAttributeSpellBook(12, SpellRarity.LEGENDARY, builder.build(), ItemPropertiesHelper.equipment().fireResistant());
            });

    /***
     * Staffs
     */
    // Bloom Stone Staff
    public static final RegistryObject<Item> BLOOM_STONE_STAFF = ITEMS.register
            ("bloom_stone_staff", () -> new StaffItem
                    (ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 3, -3,
                            Map.of(
                                    AttributeRegistry.NATURE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .15, AttributeModifier.Operation.MULTIPLY_BASE),
                                    AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE)
                            )));

    // Coral Staff
    public static final RegistryObject<Item> CORAL_STAFF = ITEMS.register
            ("coral_staff", () -> new StaffItem
                    (ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 3, -3,
                            Map.of(
                                    CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE),
                                    AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE)
                            )));

    // Fake Wadjets Staff
    public static final RegistryObject<Item> FAKE_WUDJETS_STAFF = ITEMS.register
            ("fake_wudjets_staff", () -> new StaffItem
                    (ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 3, -3,
                            Map.of(
                                    AttributeRegistry.EVOCATION_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE),
                                    AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE)
                            )));

    // Void Staff
    public static final RegistryObject<Item> VOID_STAFF = ITEMS.register
            ("void_staff", () -> new StaffItem
                    (ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 3, -3,
                            Map.of(
                                    AttributeRegistry.ENDER_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE),
                                    AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE)
                            )));

    /**
     * Curios
     */
    // Leviathan's Blessing Ring
    public static final RegistryObject<CurioBaseItem> LEVIATHANS_BLESSING = ITEMS.register("leviathans_blessing", LeviathansBlessingRing::new);

    /**
     * Items
     */
    // Abyssal Rune
    public static final RegistryObject<Item> ABYSSAL_RUNE = ITEMS.register("abyssal_rune", () -> new Item(ItemPropertiesHelper.material()));

    /***
     * Upgrade Orbs
     */
    public static final RegistryObject<Item> ABYSSAL_UPGRADE_ORB = ITEMS.register("abyssal_upgrade_orb",
            () -> new UpgradeOrbItem(CSUpgradeTypes.ABYSSAL_SPELL_POWER, ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON)));


    /***
     * Armor
     */
    // Ignis Wizard Armor
    public static final RegistryObject<Item> IGNITIUM_WIZARD_HELMET = ITEMS.register("ignis_helmet",
            () -> new IgnisWizardArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_CHESTPLATE = ITEMS.register("ignis_chestplate",
            () -> new IgnisWizardArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_LEGGINGS = ITEMS.register("ignis_leggings",
            () -> new IgnisWizardArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_BOOTS = ITEMS.register("ignis_boots",
            () -> new IgnisWizardArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));

    // Abyssal Warlock Armor
    public static final RegistryObject<Item> ABYSSAL_WARLOCK_HELMET = ITEMS.register("abyssal_warlock_helmet",
            () -> new AbyssalWarlockArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> ABYSSAL_WARLOCK_MASK = ITEMS.register("abyssal_warlock_mask",
            () -> new AbyssalWarlockMaskItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> ABYSSAL_WARLOCK_CHESTPLATE = ITEMS.register("abyssal_warlock_chestplate",
            () -> new AbyssalWarlockArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> ABYSSAL_WARLOCK_LEGGINGS = ITEMS.register("abyssal_warlock_leggings",
            () -> new AbyssalWarlockArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> ABYSSAL_WARLOCK_BOOTS = ITEMS.register("abyssal_warlock_boots",
            () -> new AbyssalWarlockArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));


    public static Collection<RegistryObject<Item>> getCSItems()
    {
        return ITEMS.getEntries();
    }

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
