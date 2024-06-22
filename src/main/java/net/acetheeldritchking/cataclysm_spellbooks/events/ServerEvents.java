package net.acetheeldritchking.cataclysm_spellbooks.events;

import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.AbyssalBlastSpell;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@Mod.EventBusSubscriber
public class ServerEvents {

    // Server Side
    @SubscribeEvent
    public static void onSpellCastEvent(SpellOnCastEvent event)
    {
        AbyssalBlastSpell blast = new AbyssalBlastSpell();
        if (Objects.equals(MagicData.getPlayerMagicData(event.getEntity()).getSyncedData().getCastingSpellId(), blast.getSpellId()))
        {
            System.out.println("Do the thing, Server");
            float headRot = event.getEntity().getYHeadRot();
            event.getEntity().setYHeadRot(headRot);
        }
    }
}
