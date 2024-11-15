package net.acetheeldritchking.cataclysm_spellbooks.events;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.*;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CataclysmSpellbooks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSetup {
    @SubscribeEvent
    public static void entityAttributeEvent(EntityAttributeCreationEvent event)
    {
        event.put(CSEntityRegistry.ABYSSAL_GNAWERS.get(), SummonedAbyssalGnawer.setAttributes());
        event.put(CSEntityRegistry.SUMMONED_IGNITED_REVENANT.get(), SummonedIgnitedRevenant.ignited_revenant().build());
        event.put(CSEntityRegistry.SUMMONED_IGNITED_BERSERKER.get(), SummonedIgnitedBerserker.ignited_berserker().build());
        event.put(CSEntityRegistry.SUMMONED_KOBOLDIATOR.get(), SummonedKoboldiator.kobolediator().build());
        event.put(CSEntityRegistry.SUMMONED_KOBOLETON.get(), SummonedKoboleton.koboleton().build());
    }
}
