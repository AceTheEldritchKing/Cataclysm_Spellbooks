package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import static net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils.isValidUnlockItemInInventory;

public abstract class AbstractMaledictusSpell extends AbstractSpell {

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
