package net.acetheeldritchking.cataclysm_spellbooks.mixins.client;

import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Objects;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    private void cataclysmSpellbooks$playerMoveCameraEvent(LocalPlayer player, double v, double d)
    {
        // Help I hate mixins
    }
}
