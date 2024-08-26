package net.acetheeldritchking.cataclysm_spellbooks.events;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.FogType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.event.ViewportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = CataclysmSpellbooks.MOD_ID, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public void onFogDensity(ViewportEvent.RenderFog event)
    {
        // Lava vision for Ignis helmet
        FogType fogType = event.getCamera().getFluidInCamera();
        ItemStack itemStack = Minecraft.getInstance().player.getInventory().getArmor(3);
        if (itemStack.is(ItemRegistries.IGNITIUM_WIZARD_HELMET.get()) && fogType == FogType.LAVA)
        {
            event.setNearPlaneDistance(0);
            event.setFarPlaneDistance(event.getRenderer().getRenderDistance() * 0.5f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void renderBlockScreenEvent(RenderBlockScreenEffectEvent event)
    {
        ItemStack itemStack = Minecraft.getInstance().player.getInventory().getArmor(3);
        if (itemStack.is(ItemRegistries.IGNITIUM_WIZARD_HELMET.get()) && Minecraft.getInstance().player.isInLava())
        {
            if (event.getOverlayType() == RenderBlockScreenEffectEvent.OverlayType.FIRE)
            {
                event.setCanceled(true);
            }
        }
    }
}
