package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.spells.AbstractSummonSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import static net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils.isValidUnlockItemInInventory;

public abstract class AbstractMaledictusSummonSpell extends AbstractSummonSpell {
    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean canBeCraftedBy(Player player) {
        Item cursium = ItemRegistries.FROZEN_TABLET.get();
        return isValidUnlockItemInInventory(cursium, player);
    }
}
