package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.item.spell_books.SimpleAttributeSpellBook;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistries {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, CataclysmSpellbooks.MOD_ID);

    //             //
    // SPELL BOOKS //
    //             //

    /*
        NOTE: The cooldown stuff for some of the books are just a placeholder for now
    */
    // Abyss Spellbook
    public static final RegistryObject<Item> ABYSS_SPELL_BOOK = ITEMS.register
            ("abyss_spell_book", () -> new SimpleAttributeSpellBook
                    (10, SpellRarity.LEGENDARY, AttributeRegistry.COOLDOWN_REDUCTION.get(), 0.30));

    // Desert Spellbook
    public static final RegistryObject<Item> DESERT_SPELL_BOOK = ITEMS.register
            ("desert_spell_book", () -> new SimpleAttributeSpellBook
                    (8, SpellRarity.EPIC, AttributeRegistry.COOLDOWN_REDUCTION.get(), 0.20));

    // Ignis Spellbook
    public static final RegistryObject<Item> IGNIS_SPELL_BOOK = ITEMS.register
            ("ignis_spell_book", () -> new SimpleAttributeSpellBook
                    (10, SpellRarity.LEGENDARY, AttributeRegistry.FIRE_SPELL_POWER.get(), 0.25));

    //       //
    // ARMOR //
    //       //

    public static void register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
