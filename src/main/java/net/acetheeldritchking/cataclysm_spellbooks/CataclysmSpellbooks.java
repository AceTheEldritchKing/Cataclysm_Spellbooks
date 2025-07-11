package net.acetheeldritchking.cataclysm_spellbooks;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import mod.azure.azurelib.rewrite.animation.cache.AzIdentityRegistry;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererRegistry;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor.*;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.items.*;
import net.acetheeldritchking.cataclysm_spellbooks.events.ServerEvents;
import net.acetheeldritchking.cataclysm_spellbooks.loot.CSLootModifiers;
import net.acetheeldritchking.cataclysm_spellbooks.registries.*;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CataclysmSpellbooks.MOD_ID)
public class CataclysmSpellbooks
{
    public static final String MOD_ID = "cataclysm_spellbooks";
    private static final Logger LOGGER = LogUtils.getLogger();

    public CataclysmSpellbooks()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Event Handlers
        MinecraftForge.EVENT_BUS.register(new ServerEvents());
        // Creative Menu
        CSCreativeTabRegistry.register(modEventBus);
        // Items
        ItemRegistries.register(modEventBus);
        // Loot Tables
        CSLootModifiers.register(modEventBus);
        // Schools
        CSSchoolRegistry.register(modEventBus);
        // Attributes
        CSAttributeRegistry.register(modEventBus);
        // Effects
        CSPotionEffectRegistry.register(modEventBus);
        // Entities
        CSEntityRegistry.register(modEventBus);
        // Spells
        SpellRegistries.register(modEventBus);
        // Particles
        CSParticleRegistry.register(modEventBus);

        // Config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CSConfig.SPEC, "cataclysm_spellbooks_config.toml");

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        // Animation Registry
        AzIdentityRegistry.register(
                ItemRegistries.IGNITIUM_WIZARD_CHESTPLATE_ELYTRA.get(),
                ItemRegistries.CURSIUM_MAGE_CHESTPLATE_ELYTRA.get(),
                ItemRegistries.DISC_DRIVER.get(),
                ItemRegistries.GAUNTLET_OF_GATTLING.get(),
                ItemRegistries.THE_NIGHTSTALKER.get(),
                ItemRegistries.THE_BERSERKER.get(),
                ItemRegistries.THE_COMBUSTER.get(),
                ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get(),
                ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get(),
                ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get()
        );
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        // Rendering armor
        @SubscribeEvent
        public static void registerRenderers(final EntityRenderersEvent.AddLayers event)
        {
            //GeoArmorRenderer.registerArmorRenderer(IgnisWizardArmorItem.class, () -> new GenericCustomArmorRenderer(new IgnisWizardArmorModel()));
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // curios
            event.enqueueWork(() -> {
                ItemRegistries.getCSItems().stream().filter(item -> item.get() instanceof SpellBook).forEach((item) -> CuriosRendererRegistry.register(item.get(), SpellBookCurioRenderer::new));
            });

            // Entity Rendering - MOVING THIS
            // Infernal Blade
            /*EntityRenderers.register(CSEntityRegistry.INFERNAL_BLADE_PROJECTILE.get(), InfernalBladeRenderer::new);
            // Hellish Blade
            EntityRenderers.register(CSEntityRegistry.HELLISH_BLADE_PROJECTILE.get(), HellishBladeRenderer::new);
            // Blazing AoE
            EntityRenderers.register(CSEntityRegistry.BLAZING_AOE_ENTITY.get(), NoopRenderer::new);
            // Parting Shot
            EntityRenderers.register(CSEntityRegistry.PARTING_SHOT_PROJECTILE.get(), PartingShotRenderer::new);
            // Surveillance Drone
            EntityRenderers.register(CSEntityRegistry.SURVEILLANCE_DRONE.get(), SurveillanceDroneRenderer::new);
            // Flash Bang
            EntityRenderers.register(CSEntityRegistry.FLASH_BANG_PROJECTILE.get(), FlashBangRenderer::new);
            // Disabling Swipe
            EntityRenderers.register(CSEntityRegistry.DISABLING_SWIPE.get(), DisablingSwipeAoERenderer::new);
            // No-Man's Zone AoE
            EntityRenderers.register(CSEntityRegistry.NO_MAN_ZONE_AOE.get(), NoopRenderer::new);
            // Blood Crystal
            EntityRenderers.register(CSEntityRegistry.BLOOD_CRYSTAL_PROJECTILE.get(), BloodCrystalRenderer::new);
            // Molten Bullet
            EntityRenderers.register(CSEntityRegistry.MOLTEN_BULLET_PROJECTILE.get(), MoltenBulletRenderer::new);
            // Scorched Earth AoE
            EntityRenderers.register(CSEntityRegistry.SCORCHED_EARTH_AOE.get(), NoopRenderer::new);
            // Frozen Bullet
            EntityRenderers.register(CSEntityRegistry.FROZEN_BULLET_PROJECTILE.get(), FrozenBulletRenderer::new);
            // Glacial Block
            EntityRenderers.register(CSEntityRegistry.GLACIAL_BLOCK.get(), GlacialBlockRenderer::new);*/


            // Armor Rendering Registry
            AzArmorRendererRegistry.register(AbyssalWarlockArmorRenderer::new,
                    ItemRegistries.ABYSSAL_WARLOCK_HELMET.get(),
                    ItemRegistries.ABYSSAL_WARLOCK_CHESTPLATE.get(),
                    ItemRegistries.ABYSSAL_WARLOCK_LEGGINGS.get(),
                    ItemRegistries.ABYSSAL_WARLOCK_BOOTS.get());
            AzArmorRendererRegistry.register(AbyssalWarlockMaskRenderer::new, ItemRegistries.ABYSSAL_WARLOCK_MASK.get());

            AzArmorRendererRegistry.register(IgnisWizardArmorRenderer::new,
                    ItemRegistries.IGNITIUM_WIZARD_HELMET.get(),
                    ItemRegistries.IGNITIUM_WIZARD_CHESTPLATE.get(),
                    ItemRegistries.IGNITIUM_WIZARD_LEGGINGS.get(),
                    ItemRegistries.IGNITIUM_WIZARD_BOOTS.get());
            AzArmorRendererRegistry.register(IgnisWizardElytraArmorRenderer::new, ItemRegistries.IGNITIUM_WIZARD_CHESTPLATE_ELYTRA.get());

            AzArmorRendererRegistry.register(CursiumMageArmorRenderer::new,
                    ItemRegistries.CURSIUM_MAGE_HELMET.get(),
                    ItemRegistries.CURSIUM_MAGE_CHESTPLATE.get(),
                    ItemRegistries.CURSIUM_MAGE_LEGGINGS.get(),
                    ItemRegistries.CURSIUM_MAGE_BOOTS.get());
            AzArmorRendererRegistry.register(CursiumMageElytraArmorRenderer::new, ItemRegistries.CURSIUM_MAGE_CHESTPLATE_ELYTRA.get());

            AzArmorRendererRegistry.register(PharaohMageArmorRenderer::new,
                    ItemRegistries.PHARAOH_MAGE_HELMET.get(),
                    ItemRegistries.PHARAOH_MAGE_CHESTPLATE.get(),
                    ItemRegistries.PHARAOH_MAGE_LEGGINGS.get(),
                    ItemRegistries.PHARAOH_MAGE_BOOTS.get());

            AzArmorRendererRegistry.register(BloomStoneMageArmorRenderer::new,
                    ItemRegistries.BLOOM_STONE_HAT.get(),
                    ItemRegistries.BLOOM_STONE_CHESTPLATE.get(),
                    ItemRegistries.BLOOM_STONE_SKIRT.get(),
                    ItemRegistries.BLOOM_STONE_GREAVES.get());

            AzArmorRendererRegistry.register(MonstrousWizardHatArmorRenderer::new, ItemRegistries.MONSTROUS_WIZARD_HAT.get());

            AzArmorRendererRegistry.register(EngineerMageArmorRenderer::new,
                    ItemRegistries.ENGINEER_MAGE_HOOD.get(),
                    ItemRegistries.ENGINEER_MAGE_SUIT.get(),
                    ItemRegistries.ENGINEER_MAGE_LEGGINGS.get(),
                    ItemRegistries.ENGINEER_MAGE_BOOTS.get());

            AzArmorRendererRegistry.register(ExcelsiusCooldownArmorRenderer::new,
                    ItemRegistries.EXCELSIUS_SPEED_HELMET.get(),
                    ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get());

            AzArmorRendererRegistry.register(ExcelsiusPowerArmorRenderer::new,
                    ItemRegistries.EXCELSIUS_POWER_HELMET.get(),
                    ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get());

            AzArmorRendererRegistry.register(ExcelsiusResistArmorRenderer::new,
                    ItemRegistries.EXCELSIUS_RESIST_HELMET.get(),
                    ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get());

            AzArmorRendererRegistry.register(ExcelsiusLegArmorRenderer::new,
                    ItemRegistries.EXCELSIUS_WARLOCK_LEGGINGS.get(),
                    ItemRegistries.EXCELSIUS_WARLOCK_BOOTS.get());

            // Item Rendering Registry
            AzItemRendererRegistry.register(CodexOfMaliceSpellBookRenderer::new, ItemRegistries.CODEX_OF_MALICE.get());
            AzItemRendererRegistry.register(DiscDriverSpellbookRenderer::new, ItemRegistries.DISC_DRIVER.get());
            AzItemRendererRegistry.register(SpiritSundererStaffRenderer::new, ItemRegistries.SPIRIT_SUNDERER_STAFF.get());
            AzItemRendererRegistry.register(GauntletOfPowerRenderer::new, ItemRegistries.GAUNTLET_OF_POWER.get());
            AzItemRendererRegistry.register(GauntletOfGattlingRenderer::new, ItemRegistries.GAUNTLET_OF_GATTLING.get());
            AzItemRendererRegistry.register(TheNightStalkerRenderer::new, ItemRegistries.THE_NIGHTSTALKER.get());
            AzItemRendererRegistry.register(TheBerserkerRenderer::new, ItemRegistries.THE_BERSERKER.get());
            AzItemRendererRegistry.register(TheCombusterRenderer::new, ItemRegistries.THE_COMBUSTER.get());
        }
    }

    public static ResourceLocation id(@NotNull String path) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, path);
    }
}
