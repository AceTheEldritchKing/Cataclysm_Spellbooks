package net.acetheeldritchking.cataclysm_spellbooks.events;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.*;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockEntity;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CataclysmSpellbooks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event)
    {
        event.put(CSEntityRegistry.SUMMONED_IGNITED_REVENANT.get(), SummonedIgnitedRevenant.ignited_revenant().build());
        event.put(CSEntityRegistry.SUMMONED_IGNITED_BERSERKER.get(), SummonedIgnitedBerserker.ignited_berserker().build());
        event.put(CSEntityRegistry.SUMMONED_KOBOLDIATOR.get(), SummonedKoboldiator.kobolediator().build());
        event.put(CSEntityRegistry.SUMMONED_KOBOLETON.get(), SummonedKoboleton.koboleton().build());
        event.put(CSEntityRegistry.SUMMONED_DRAUGUR.get(), SummonedDraugur.draugr().build());
        event.put(CSEntityRegistry.SUMMONED_ROYAL_DRAUGUR.get(), SummonedRoyalDraugur.royal_draugr().build());
        event.put(CSEntityRegistry.SUMMONED_ELITE_DRAUGUR.get(), SummonedEliteDraugur.elite_draugr().build());
        event.put(CSEntityRegistry.SUMMONED_APTRGANGR.get(), SummonedAptrgangr.aptrgangr().build());
        event.put(CSEntityRegistry.SUMMONED_WATCHER.get(), SummonedWatcher.the_watcher().build());
        event.put(CSEntityRegistry.SUMMONED_COUNTERSPELL_WATCHER.get(), SummonedCounterspellWatcher.the_watcher().build());
        event.put(CSEntityRegistry.SUMMONED_PROWLER.get(), SummonedProwler.the_prowler().build());
        event.put(CSEntityRegistry.SURVEILLANCE_DRONE.get(), SurveillanceDroneEntity.setAttributes());
        event.put(CSEntityRegistry.SUMMONED_AMETHYST_CRAB.get(), SummonedAmethystCrab.buildAttributes());
        event.put(CSEntityRegistry.SUMMONED_CORAL_GOLEM.get(), SummonedCoralGolem.buildAttributes());
        event.put(CSEntityRegistry.SUMMONED_CORALSSUS.get(), SummonedCoralssus.buildAttributes());
        event.put(CSEntityRegistry.PHANTOM_ANCIENT_REMNANT.get(), PhantomAncientRemnant.maledictus().build());
        event.put(CSEntityRegistry.GLACIAL_BLOCK.get(), GlacialBlockEntity.createLivingAttributes().build());
    }
}
