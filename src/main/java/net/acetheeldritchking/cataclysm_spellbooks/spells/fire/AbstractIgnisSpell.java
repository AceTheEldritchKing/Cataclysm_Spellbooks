package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

import static net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils.isValidUnlockItemInInventory;

public abstract class AbstractIgnisSpell extends AbstractSpell {

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
