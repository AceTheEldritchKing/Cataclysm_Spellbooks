package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.spells.AbstractSummonSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import static net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils.isValidUnlockItemInInventory;

public abstract class AbstractIgnisSummonSpell extends AbstractSummonSpell {
    @Override
    public Component getLockedMessage() {
        return Component.translatable("ui.cataclysm_spellbooks.ignis_unlearned");
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean canBeCraftedBy(Player player) {
        Item burningEmbers = ItemRegistries.BURNING_MANUSCRIPT.get();
        return isValidUnlockItemInInventory(burningEmbers, player);
    }
}
