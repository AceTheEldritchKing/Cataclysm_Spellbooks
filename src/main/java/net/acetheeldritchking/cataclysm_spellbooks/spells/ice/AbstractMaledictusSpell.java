package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public abstract class AbstractMaledictusSpell extends AbstractSpell {

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean canBeCraftedBy(Player player) {
        // Will change this later to overhaul how crafting will be done
        Item cursium = ModItems.CURSIUM_INGOT.get();
        return player.getMainHandItem().is(cursium);
    }
}
