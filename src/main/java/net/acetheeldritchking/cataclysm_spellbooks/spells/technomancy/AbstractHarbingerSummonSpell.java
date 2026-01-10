package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.spells.AbstractSummonSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import static net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils.hasCurio;
import static net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils.isValidUnlockItemInInventory;

public abstract class AbstractHarbingerSummonSpell extends AbstractSummonSpell {
    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean canBeCraftedBy(Player player) {
        Item disc = ItemRegistries.STRANGE_DISC.get();
        Item discDriver = ItemRegistries.DISC_DRIVER.get();

        // Unlock on two conditions
        return isValidUnlockItemInInventory(disc, player) || hasCurio(player, discDriver);
    }
}
