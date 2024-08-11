package net.acetheeldritchking.cataclysm_spellbooks.events;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = CataclysmSpellbooks.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    // Client Side

    /*@SubscribeEvent
    public static void onSpellCastEvent(SpellOnCastEvent event)
    {
        AbyssalBlastSpell blast = new AbyssalBlastSpell();
        while (Objects.equals(ClientMagicData.getSyncedSpellData(event.getEntity()).getCastingSpellId(), blast.getSpellId()))
        {
            System.out.println("Do the thing, Client");
            LocalPlayer player = (LocalPlayer) event.getEntity();
            //player.lerpHeadTo(0, 0);

            //ScreenEvent.MouseDragged dragged = player;
            //dragged.setCanceled(true);
        }
    }*/
}
