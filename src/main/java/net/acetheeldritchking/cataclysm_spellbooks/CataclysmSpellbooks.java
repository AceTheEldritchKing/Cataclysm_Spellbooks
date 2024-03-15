package net.acetheeldritchking.cataclysm_spellbooks;

import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import io.redspace.ironsspellbooks.item.SpellBook;
import io.redspace.ironsspellbooks.render.SpellBookCurioRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.armor.IgnisWizardArmorModel;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.registries.*;
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
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;
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

        // Items
        ItemRegistries.register(modEventBus);
        // Schools
        CSSchoolRegistry.register(modEventBus);
        // Attributes
        CSAttributeRegistry.register(modEventBus);
        // Effects
        CSPotionEffectRegistry.register(modEventBus);
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
            GeoArmorRenderer.registerArmorRenderer(IgnisWizardArmorItem.class, () -> new GenericCustomArmorRenderer(new IgnisWizardArmorModel()));
        }

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // curios
            event.enqueueWork(() -> {
                ItemRegistries.getCSItems().stream().filter(item -> item.get() instanceof SpellBook).forEach((item) -> CuriosRendererRegistry.register(item.get(), SpellBookCurioRenderer::new));
            });
        }
    }

    public static ResourceLocation id(@NotNull String path) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, path);
    }
}
