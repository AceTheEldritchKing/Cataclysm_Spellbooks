package net.acetheeldritchking.cataclysm_spellbooks.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @WrapOperation(
            method = "turnPlayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V")
    )
    private void cataclysmSpellbooks$playerModifyTurnMovement(LocalPlayer player, double yRot, double xRot, Operation<Void> original)
    {
        if (player.hasEffect(CSPotionEffectRegistry.INCAPACITATED_EFFECT.get()))
        {
            // Fix camera movement to where the player is looking
            original.call(player, 0.0, 0.0);
        }
        else
        {
            // Normal camera movement here
            original.call(player, yRot, xRot);
        }
    }
}
