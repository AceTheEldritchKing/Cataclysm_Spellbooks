package net.acetheeldritchking.cataclysm_spellbooks.events;

import com.github.L_Ender.cataclysm.client.render.entity.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs.PhantomAncientRemnantRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.particle.TargetParticle;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSParticleRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterParticleProvidersEvent;
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
        event.registerEntityRenderer(CSEntityRegistry.EXTENDED_DEATH_LASER_BEAM.get(), Death_Laser_beam_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_WATCHER.get(), The_Watcher_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_COUNTERSPELL_WATCHER.get(), The_Watcher_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_PROWLER.get(), The_Prowler_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_AMETHYST_CRAB.get(), Amethyst_Crab_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_CORAL_GOLEM.get(), Coral_Golem_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SUMMONED_CORALSSUS.get(), Coralssus_Renderer::new);
        event.registerEntityRenderer(CSEntityRegistry.PHANTOM_ANCIENT_REMNANT.get(), PhantomAncientRemnantRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CSParticleRegistry.TARGET_PARTICLE.get(), TargetParticle.Provider::new);
    }
}
