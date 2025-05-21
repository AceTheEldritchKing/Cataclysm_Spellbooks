package net.acetheeldritchking.cataclysm_spellbooks.items.custom;

import mod.azure.azurelib.rewrite.animation.dispatch.command.AzCommand;
import mod.azure.azurelib.rewrite.animation.play_behavior.AzPlayBehaviors;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class CSItemDispatcher {
    private static final AzCommand EQUIP_COMMAND = AzCommand.create(
            "base_controller",
            "equipping",
            AzPlayBehaviors.PLAY_ONCE
    );

    private static final AzCommand IDLE_COMMAND = AzCommand.create(
            "base_controller",
            "idle",
            AzPlayBehaviors.LOOP
    );

    private static final AzCommand ELYTRA_FLIGHT_COMMAND = AzCommand.create(
            "base_controller",
            "flying",
            AzPlayBehaviors.LOOP
    );

    private static final AzCommand CASTING_SPELL_COMMAND = AzCommand.create(
            "base_controller",
            "casting",
            AzPlayBehaviors.LOOP
    );

    public void equip(Entity entity, ItemStack itemStack) {
        EQUIP_COMMAND.sendForItem(entity, itemStack);
    }

    public void idle(Entity entity, ItemStack itemStack) {
        IDLE_COMMAND.sendForItem(entity, itemStack);
    }

    public void flight(Entity entity, ItemStack itemStack) {
        ELYTRA_FLIGHT_COMMAND.sendForItem(entity, itemStack);
    }

    public void casting(Entity entity, ItemStack itemStack) {
        CASTING_SPELL_COMMAND.sendForItem(entity, itemStack);
    }
}
