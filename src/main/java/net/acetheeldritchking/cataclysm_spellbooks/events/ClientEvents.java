package net.acetheeldritchking.cataclysm_spellbooks.events;

import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.AbyssalBlastSpell;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = CataclysmSpellbooks.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    // Client Side
    @SubscribeEvent
    public static void onSpellCastEvent(SpellOnCastEvent event)
    {
        AbyssalBlastSpell blast = new AbyssalBlastSpell();
        if (Objects.equals(ClientMagicData.getSyncedSpellData(event.getEntity()).getCastingSpellId(), blast.getSpellId()))
        {
            System.out.println("Do the thing, Client");
            float headRot = event.getEntity().getYHeadRot();
            event.getEntity().setYHeadRot(headRot);
        }
    }
}
