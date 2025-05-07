package net.acetheeldritchking.cataclysm_spellbooks.events;

import com.github.L_Ender.cataclysm.client.render.entity.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CataclysmSpellbooks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ClientSetup {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event)
    {
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_IGNITED_REVENANT.get(), Ignited_Revenant_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_IGNITED_BERSERKER.get(), Ignited_Berserker_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_KOBOLDIATOR.get(), Kobolediator_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_KOBOLETON.get(), Koboleton_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_DRAUGUR.get(), Draugr_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_ROYAL_DRAUGUR.get(), Royal_Draugr_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_ELITE_DRAUGUR.get(), Elite_Draugr_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_APTRGANGR.get(), Aptrgangr_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.EXTENDED_LASER_BEAM.get(), Laser_Beam_Renderer::new);
    }
}
