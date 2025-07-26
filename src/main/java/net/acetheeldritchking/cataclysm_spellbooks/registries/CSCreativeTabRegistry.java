package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.registries.CreativeTabRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber
public class CSCreativeTabRegistry {
    private static final DeferredRegister<CreativeModeTab> CREATIVE_MOD_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CataclysmSpellbooks.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CS_ITEMS_TAB = CREATIVE_MOD_TAB.
            register(CataclysmSpellbooks.MOD_ID, () -> CreativeModeTab.builder()
                    .title(Component.translatable("creative_tab.cataclysm_spellbooks.items"))
                    .icon(() -> new ItemStack(ItemRegistries.ABYSSAL_RUNE.get()))
                    .displayItems(((pParameters, output) -> {
                        // Materials
                        output.accept(ItemRegistries.ABYSSAL_RUNE.get());
                        output.accept(ItemRegistries.ABYSSAL_UPGRADE_ORB.get());
                        output.accept(ItemRegistries.TECHNOMANCY_RUNE.get());
                        output.accept(ItemRegistries.TECHNOMANCY_UPGRADE_ORB.get());
                        output.accept(ItemRegistries.MECHANICAL_SCRAP.get());
                        output.accept(ItemRegistries.EXCEL_MANA_UPGRADE.get());
                        output.accept(ItemRegistries.EXCEL_SPELL_RES_UPGRADE.get());
                        output.accept(ItemRegistries.EXCEL_COOLDOWN_UPGRADE.get());
                        output.accept(ItemRegistries.BURNING_KNOWLEDGE_FRAGMENT.get());
                        output.accept(ItemRegistries.BURNING_MANUSCRIPT.get());
                        output.accept(ItemRegistries.FROZEN_KNOWLEDGE_FRAGMENT.get());
                        output.accept(ItemRegistries.FROZEN_TABLET.get());
                        output.accept(ItemRegistries.STRANGE_DISC.get());
                        // Curios
                        output.accept(ItemRegistries.LEVIATHANS_BLESSING.get());
                        output.accept(ItemRegistries.BURST_SHEATH.get());
                        // Spellbooks
                        output.accept(ItemRegistries.ABYSS_SPELL_BOOK.get());
                        output.accept(ItemRegistries.IGNIS_SPELL_BOOK.get());
                        output.accept(ItemRegistries.DESERT_SPELL_BOOK.get());
                        output.accept(ItemRegistries.CODEX_OF_MALICE.get());
                        output.accept(ItemRegistries.DISC_DRIVER.get());
                        // Staves
                        output.accept(ItemRegistries.BLOOM_STONE_STAFF.get());
                        output.accept(ItemRegistries.CORAL_STAFF.get());
                        output.accept(ItemRegistries.FAKE_WUDJETS_STAFF.get());
                        output.accept(ItemRegistries.VOID_STAFF.get());
                        output.accept(ItemRegistries.SPIRIT_SUNDERER_STAFF.get());
                        output.accept(ItemRegistries.SOUL_BRAZIER_STAFF.get());
                        output.accept(ItemRegistries.ENGINEERS_POWER_GLOVE.get());
                        output.accept(ItemRegistries.GAUNTLET_OF_POWER.get());
                        output.accept(ItemRegistries.GAUNTLET_OF_GATTLING.get());
                        output.accept(ItemRegistries.THE_NIGHTSTALKER.get());
                        output.accept(ItemRegistries.THE_BERSERKER.get());
                        output.accept(ItemRegistries.THE_COMBUSTER.get());
                        // Weapons
                        output.accept(ItemRegistries.MONSTROUS_FLAMBERGE.get());
                        output.accept(ItemRegistries.MURASAMA.get());
                        // Armor
                        output.accept(ItemRegistries.IGNITIUM_WIZARD_HELMET.get());
                        output.accept(ItemRegistries.IGNITIUM_WIZARD_CHESTPLATE.get());
                        output.accept(ItemRegistries.IGNITIUM_WIZARD_CHESTPLATE_ELYTRA.get());
                        output.accept(ItemRegistries.IGNITIUM_WIZARD_LEGGINGS.get());
                        output.accept(ItemRegistries.IGNITIUM_WIZARD_BOOTS.get());

                        output.accept(ItemRegistries.ABYSSAL_WARLOCK_HELMET.get());
                        output.accept(ItemRegistries.ABYSSAL_WARLOCK_MASK.get());
                        output.accept(ItemRegistries.ABYSSAL_WARLOCK_CHESTPLATE.get());
                        output.accept(ItemRegistries.ABYSSAL_WARLOCK_LEGGINGS.get());
                        output.accept(ItemRegistries.ABYSSAL_WARLOCK_BOOTS.get());

                        output.accept(ItemRegistries.CURSIUM_MAGE_HELMET.get());
                        output.accept(ItemRegistries.CURSIUM_MAGE_CHESTPLATE.get());
                        output.accept(ItemRegistries.CURSIUM_MAGE_CHESTPLATE_ELYTRA.get());
                        output.accept(ItemRegistries.CURSIUM_MAGE_LEGGINGS.get());
                        output.accept(ItemRegistries.CURSIUM_MAGE_BOOTS.get());

                        output.accept(ItemRegistries.BLOOM_STONE_HAT.get());
                        output.accept(ItemRegistries.BLOOM_STONE_CHESTPLATE.get());
                        output.accept(ItemRegistries.BLOOM_STONE_SKIRT.get());
                        output.accept(ItemRegistries.BLOOM_STONE_GREAVES.get());

                        output.accept(ItemRegistries.PHARAOH_MAGE_HELMET.get());
                        output.accept(ItemRegistries.PHARAOH_MAGE_CHESTPLATE.get());
                        output.accept(ItemRegistries.PHARAOH_MAGE_LEGGINGS.get());
                        output.accept(ItemRegistries.PHARAOH_MAGE_BOOTS.get());

                        output.accept(ItemRegistries.MONSTROUS_WIZARD_HAT.get());

                        output.accept(ItemRegistries.ENGINEER_MAGE_HOOD.get());
                        output.accept(ItemRegistries.ENGINEER_MAGE_SUIT.get());
                        output.accept(ItemRegistries.ENGINEER_MAGE_LEGGINGS.get());
                        output.accept(ItemRegistries.ENGINEER_MAGE_BOOTS.get());

                        output.accept(ItemRegistries.EXCELSIUS_SPEED_HELMET.get());
                        output.accept(ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get());
                        output.accept(ItemRegistries.EXCELSIUS_POWER_HELMET.get());
                        output.accept(ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get());
                        output.accept(ItemRegistries.EXCELSIUS_RESIST_HELMET.get());
                        output.accept(ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get());
                        output.accept(ItemRegistries.EXCELSIUS_WARLOCK_LEGGINGS.get());
                        output.accept(ItemRegistries.EXCELSIUS_WARLOCK_BOOTS.get());
                    }))
                    .withTabsBefore(CreativeTabRegistry.EQUIPMENT_TAB.getId())
                    .build());

    public static void register(IEventBus eventBus)
    {
        CREATIVE_MOD_TAB.register(eventBus);
    }
}
