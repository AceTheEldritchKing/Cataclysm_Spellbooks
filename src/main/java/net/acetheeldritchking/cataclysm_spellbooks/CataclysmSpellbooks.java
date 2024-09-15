package net.acetheeldritchking.cataclysm_spellbooks;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs.SummonedAbyssalGnawersRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells.HellishBladeRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells.InfernalBladeRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.events.ServerEvents;
import net.acetheeldritchking.cataclysm_spellbooks.loot.CSLootModifiers;
import net.acetheeldritchking.cataclysm_spellbooks.registries.*;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
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

            // Entity Rendering
            // Abyssal Gnawers
            EntityRenderers.register(CSEntityRegistry.ABYSSAL_GNAWERS.get(), SummonedAbyssalGnawersRenderer::new);
            // Infernal Blade
            EntityRenderers.register(CSEntityRegistry.INFERNAL_BLADE_PROJECTILE.get(), InfernalBladeRenderer::new);
            // Hellish Blade
            EntityRenderers.register(CSEntityRegistry.HELLISH_BLADE_PROJECTILE.get(), HellishBladeRenderer::new);
            // Blazing AoE
            EntityRenderers.register(CSEntityRegistry.BLAZING_AOE_ENTITY.get(), NoopRenderer::new);
        }
    }

    public static ResourceLocation id(@NotNull String path) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, path);
    }
}
