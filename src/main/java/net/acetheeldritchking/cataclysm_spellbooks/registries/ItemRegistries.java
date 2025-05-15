package net.acetheeldritchking.cataclysm_spellbooks.registries;

import com.google.common.collect.ImmutableMultimap;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.registry.SpellDataRegistryHolder;
import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.armor.UpgradeTypes;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.item.spell_books.SimpleAttributeSpellBook;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.*;
import net.acetheeldritchking.cataclysm_spellbooks.items.curios.LeviathansBlessingRing;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.CodexOfMaliceSpellBook;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.DesertSpellBook;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.DiscDriverSpellbook;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.BloomStoneStaff;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.FakeWadjetStaff;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.GauntletOfPowerItem;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.SpiritSundererStaff;
import net.acetheeldritchking.cataclysm_spellbooks.items.weapons.MonstrousFlambergeItem;
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
                return new SimpleAttributeSpellBook(12, SpellRarity.LEGENDARY, builder.build(), ItemPropertiesHelper.equipment().fireResistant().stacksTo(1));
            });

    // Desert Spellbook - Dropped by ancient remnant
    public static final RegistryObject<Item> DESERT_SPELL_BOOK = ITEMS.register
            ("desert_spell_book", DesertSpellBook::new);

    // Ignis Spellbook
    public static final RegistryObject<Item> IGNIS_SPELL_BOOK = ITEMS.register
            ("ignis_spell_book", () ->
            {
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 0.30, AttributeModifier.Operation.MULTIPLY_TOTAL));
                builder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 300, AttributeModifier.Operation.ADDITION));
                return new SimpleAttributeSpellBook(12, SpellRarity.LEGENDARY, builder.build(), ItemPropertiesHelper.equipment().fireResistant().stacksTo(1));
            });

    // Codex of Malice
    public static final RegistryObject<Item> CODEX_OF_MALICE = ITEMS.register
            ("codex_of_malice_spell_book", () ->
            {
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 0.30, AttributeModifier.Operation.MULTIPLY_TOTAL));
                builder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 300, AttributeModifier.Operation.ADDITION));
                // Yeah, this is weird, I know
                return new CodexOfMaliceSpellBook(builder.build());
            });

    // Disc Driver
    public static final RegistryObject<Item> DISC_DRIVER = ITEMS.register
            ("disc_driver", () ->
            {
                ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
                builder.put(CSAttributeRegistry.TECHNOMANCY_MAGIC_POWER.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 0.30, AttributeModifier.Operation.MULTIPLY_TOTAL));
                builder.put(AttributeRegistry.MAX_MANA.get(), new AttributeModifier(UUID.fromString("58a54c84-1aae-4cf6-83c8-d85d32807e31"), "Weapon Modifier", 300, AttributeModifier.Operation.ADDITION));
                return new DiscDriverSpellbook(builder.build());
            });


    /***
     * Staffs
     */
    // Bloom Stone Staff
    public static final RegistryObject<Item> BLOOM_STONE_STAFF = ITEMS.register
            ("bloom_stone_staff", BloomStoneStaff::new);

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
            ("fake_wudjets_staff", FakeWadjetStaff::new);

    // Void Staff
    public static final RegistryObject<Item> VOID_STAFF = ITEMS.register
            ("void_staff", () -> new StaffItem
                    (ItemPropertiesHelper.equipment().stacksTo(1).rarity(Rarity.EPIC), 3, -3,
                            Map.of(
                                    AttributeRegistry.ENDER_SPELL_POWER.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE),
                                    AttributeRegistry.COOLDOWN_REDUCTION.get(), new AttributeModifier(UUID.fromString("667ad88f-901d-4691-b2a2-3664e42026d3"), "Weapon modifier", .25, AttributeModifier.Operation.MULTIPLY_BASE)
                            )));

    // Spirit Sunderer Staff
    public static final RegistryObject<Item> SPIRIT_SUNDERER_STAFF = ITEMS.register
            ("spirit_sunderer", SpiritSundererStaff::new);

    // Gauntlet of Power
    public static final RegistryObject<Item> GAUNTLET_OF_POWER = ITEMS.register
            ("gauntlet_of_power", GauntletOfPowerItem::new);


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

    // Technomancy Rune
    public static final RegistryObject<Item> TECHNOMANCY_RUNE = ITEMS.register("technomancy_rune", () -> new Item(ItemPropertiesHelper.material()));


    /**
     * Weapons
     */
    // Monstrous Flamberge
    public static final RegistryObject<Item> MONSTROUS_FLAMBERGE = ITEMS.register("monstrous_flamberge", () -> new MonstrousFlambergeItem(SpellDataRegistryHolder.of(
            new SpellDataRegistryHolder(SpellRegistries.TECTONIC_TREMBLE, 1))));

    // Spellstealer - Only planned for 1.21.1


    /***
     * Upgrade Orbs
     */
    // Abyssal Upgrade Orb
    public static final RegistryObject<Item> ABYSSAL_UPGRADE_ORB = ITEMS.register("abyssal_upgrade_orb",
            () -> new UpgradeOrbItem(CSUpgradeTypes.ABYSSAL_SPELL_POWER, ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON)));

    // Technomancy Upgrade Orb
    public static final RegistryObject<Item> TECHNOMANCY_UPGRADE_ORB = ITEMS.register("technomancy_upgrade_orb",
            () -> new UpgradeOrbItem(CSUpgradeTypes.TECHNOMANCY_SPELL_POWER, ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON)));


    /***
     * Armor
     */
    // Ignis Wizard Armor
    public static final RegistryObject<Item> IGNITIUM_WIZARD_HELMET = ITEMS.register("ignis_helmet",
            () -> new IgnisWizardArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_CHESTPLATE = ITEMS.register("ignis_chestplate",
            () -> new IgnisWizardArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_CHESTPLATE_ELYTRA = ITEMS.register("ignis_chestplate_elytra",
            () -> new IgnisWizardElytraArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
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

    // Cursium Mage Set
    public static final RegistryObject<Item> CURSIUM_MAGE_HELMET = ITEMS.register("cursium_mage_circlet",
            () -> new CursiumMageArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> CURSIUM_MAGE_CHESTPLATE = ITEMS.register("cursium_mage_chestplate",
            () -> new CursiumMageArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> CURSIUM_MAGE_CHESTPLATE_ELYTRA = ITEMS.register("cursium_mage_elytra",
            () -> new CursiumMageElytraArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> CURSIUM_MAGE_LEGGINGS = ITEMS.register("cursium_mage_skirt",
            () -> new CursiumMageArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> CURSIUM_MAGE_BOOTS = ITEMS.register("cursium_mage_boots",
            () -> new CursiumMageArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));

    // Pharaoh Mage Set
    public static final RegistryObject<Item> PHARAOH_MAGE_HELMET = ITEMS.register("pharaoh_helmet",
            () -> new PharaohMageArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> PHARAOH_MAGE_CHESTPLATE = ITEMS.register("pharaoh_chestplate",
            () -> new PharaohMageArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> PHARAOH_MAGE_LEGGINGS = ITEMS.register("pharaoh_leggings",
            () -> new PharaohMageArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> PHARAOH_MAGE_BOOTS = ITEMS.register("pharaoh_greaves",
            () -> new PharaohMageArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));

    // Bloom Stone Mage Set
    public static final RegistryObject<Item> BLOOM_STONE_HAT = ITEMS.register("bloom_stone_hat",
            () -> new BloomStoneMageArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> BLOOM_STONE_CHESTPLATE = ITEMS.register("bloom_stone_chestplate",
            () -> new BloomStoneMageArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> BLOOM_STONE_SKIRT = ITEMS.register("bloom_stone_skirt",
            () -> new BloomStoneMageArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> BLOOM_STONE_GREAVES = ITEMS.register("bloom_stone_greaves",
            () -> new BloomStoneMageArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));

    // Monstrous Wizard Hat
    public static final RegistryObject<Item> MONSTROUS_WIZARD_HAT = ITEMS.register("monstrous_wizard_hat",
            () -> new MonstrousWizardHatArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));

    // Technomancer Mage set
    public static final RegistryObject<Item> ENGINEER_MAGE_HOOD = ITEMS.register("engineer_hood",
            () -> new EngineerMageArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> ENGINEER_MAGE_SUIT = ITEMS.register("engineer_suit",
            () -> new EngineerMageArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> ENGINEER_MAGE_LEGGINGS = ITEMS.register("engineer_leggings",
            () -> new EngineerMageArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> ENGINEER_MAGE_BOOTS = ITEMS.register("engineer_boots",
            () -> new EngineerMageArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));

    // Excelsius Mage Set
    public static final RegistryObject<Item> EXCELSIUS_SPEED_HELMET = ITEMS.register("excelsius_speed_visors",
            () -> new ExcelsiusCooldownArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> EXCELSIUS_SPEED_CHESTPLATE = ITEMS.register("excelsius_speed_chestplate",
            () -> new ExcelsiusCooldownArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> EXCELSIUS_POWER_HELMET = ITEMS.register("excelsius_power_visors",
            () -> new ExcelsiusPowerArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> EXCELSIUS_POWER_CHESTPLATE = ITEMS.register("excelsius_power_chestplate",
            () -> new ExcelsiusPowerArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> EXCELSIUS_RESIST_HELMET = ITEMS.register("excelsius_resist_visors",
            () -> new ExcelsiusResistArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> EXCELSIUS_RESIST_CHESTPLATE = ITEMS.register("excelsius_resist_chestplate",
            () -> new ExcelsiusResistArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> EXCELSIUS_WARLOCK_LEGGINGS = ITEMS.register("excelsius_leggings",
            () -> new ExcelsiusLegArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> EXCELSIUS_WARLOCK_BOOTS = ITEMS.register("excelsius_greaves",
            () -> new ExcelsiusLegArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));


    public static Collection<RegistryObject<Item>> getCSItems()
    {
        return ITEMS.getEntries();
    }

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
