package net.acetheeldritchking.cataclysm_spellbooks.events;

import com.github.L_Ender.cataclysm.client.render.entity.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs.PhantomAncientRemnantRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs.SurveillanceDroneRenderer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells.*;
import net.acetheeldritchking.cataclysm_spellbooks.particle.TargetParticle;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSParticleRegistry;
import net.minecraft.client.renderer.entity.NoopRenderer;
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

        event.registerEntityRenderer(CSEntityRegistry.INFERNAL_BLADE_PROJECTILE.get(), InfernalBladeRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.HELLISH_BLADE_PROJECTILE.get(), HellishBladeRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.BLAZING_AOE_ENTITY.get(), NoopRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.PARTING_SHOT_PROJECTILE.get(), PartingShotRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SURVEILLANCE_DRONE.get(), SurveillanceDroneRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.FLASH_BANG_PROJECTILE.get(), FlashBangRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.DISABLING_SWIPE.get(), DisablingSwipeAoERenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.NO_MAN_ZONE_AOE.get(), NoopRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.BLOOD_CRYSTAL_PROJECTILE.get(), BloodCrystalRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.MOLTEN_BULLET_PROJECTILE.get(), MoltenBulletRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.SCORCHED_EARTH_AOE.get(), NoopRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.FROZEN_BULLET_PROJECTILE.get(), FrozenBulletRenderer::new);
        event.registerEntityRenderer(CSEntityRegistry.GLACIAL_BLOCK.get(), GlacialBlockRenderer::new);
    }

    @SubscribeEvent
    public static void registerParticles(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(CSParticleRegistry.TARGET_PARTICLE.get(), TargetParticle.Provider::new);
    }
}
