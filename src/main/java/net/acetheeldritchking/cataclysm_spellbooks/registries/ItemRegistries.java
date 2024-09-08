package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.UpgradeOrbItem;
import io.redspace.ironsspellbooks.item.armor.UpgradeTypes;
import io.redspace.ironsspellbooks.item.curios.CurioBaseItem;
import io.redspace.ironsspellbooks.item.spell_books.SimpleAttributeSpellBook;
import io.redspace.ironsspellbooks.item.weapons.StaffItem;
import io.redspace.ironsspellbooks.util.ItemPropertiesHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.CSUpgradeTypes;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.items.curios.LeviathansBlessingRing;
import net.minecraft.world.entity.EquipmentSlot;
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
            ("abyss_spell_book", () -> new SimpleAttributeSpellBook
                    (12, SpellRarity.LEGENDARY, CSAttributeRegistry.ABYSSAL_MAGIC_POWER.get(), 0.30, 300));

    // Desert Spellbook - Dropped by ancient remnant
    public static final RegistryObject<Item> DESERT_SPELL_BOOK = ITEMS.register
            ("desert_spell_book", () -> new SimpleAttributeSpellBook
                    (10, SpellRarity.EPIC, AttributeRegistry.NATURE_SPELL_POWER.get(), 0.20, 150));

    // Ignis Spellbook
    public static final RegistryObject<Item> IGNIS_SPELL_BOOK = ITEMS.register
            ("ignis_spell_book", () -> new SimpleAttributeSpellBook
                    (12, SpellRarity.LEGENDARY, AttributeRegistry.FIRE_SPELL_POWER.get(), 0.30, 300));

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

    /***
     * Upgrade Orbs
     */
    public static final RegistryObject<Item> ABYSSAL_UPGRADE_ORB = ITEMS.register("abyssal_upgrade_orb",
            () -> new UpgradeOrbItem(CSUpgradeTypes.ABYSSAL_SPELL_POWER, ItemPropertiesHelper.material().rarity(Rarity.UNCOMMON)));


    /***
     * Armor
     */

    // Ignis Wizard Armor
    // TEMPORARILY DISABLED SINCE THE RENDERING IS BROKEN
    public static final RegistryObject<Item> IGNITIUM_WIZARD_HELMET = ITEMS.register("ignis_helmet",
            () -> new IgnisWizardArmorItem(EquipmentSlot.HEAD, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_CHESTPLATE = ITEMS.register("ignis_chestplate",
            () -> new IgnisWizardArmorItem(EquipmentSlot.CHEST, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_LEGGINGS = ITEMS.register("ignis_leggings",
            () -> new IgnisWizardArmorItem(EquipmentSlot.LEGS, ItemPropertiesHelper.equipment().fireResistant()));
    public static final RegistryObject<Item> IGNITIUM_WIZARD_BOOTS = ITEMS.register("ignis_boots",
            () -> new IgnisWizardArmorItem(EquipmentSlot.FEET, ItemPropertiesHelper.equipment().fireResistant()));


    public static Collection<RegistryObject<Item>> getCSItems()
    {
        return ITEMS.getEntries();
    }

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
