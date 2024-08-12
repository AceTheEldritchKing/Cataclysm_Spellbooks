package net.acetheeldritchking.cataclysm_spellbooks.mixins.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.AbyssalBlastSpell;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Objects;

@Mixin(MouseHandler.class)
public class MouseMixin {
    @WrapOperation(
            method = "turnPlayer",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V")
    )
    private void cataclysmSpellbooks$playerModifyTurnMovement(LocalPlayer player, double yRot, double xRot, Operation<Void> original)
    {
        // Help I hate mixins
        AbyssalBlastSpell blast = new AbyssalBlastSpell();
        /*if (!isChanneling(player)) { // or whatever
            original.call(player, yRot, xRot);
        } else {
            original.call(player, 0.0, 0.0);
        }*/
        if (Objects.equals(ClientMagicData.getSyncedSpellData(player).getCastingSpellId(), blast.getSpellId())
                && ClientMagicData.getSyncedSpellData(player).isCasting()
                || player.hasEffect(CSPotionEffectRegistry.INCAPACITATED_EFFECT.get()))
        {
            original.call(player, 0.0, 0.0);
        }
        else
        {
            //original.call(player, 0.0, 0.0);
            original.call(player, yRot, xRot);
        }
    }
}
