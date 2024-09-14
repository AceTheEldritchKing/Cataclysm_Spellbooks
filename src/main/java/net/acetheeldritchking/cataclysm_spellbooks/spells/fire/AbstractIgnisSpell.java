package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.AbstractAbyssalSpell;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;

public abstract class AbstractIgnisSpell extends AbstractAbyssalSpell {

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean canBeCraftedBy(Player player) {
        Item burningEmbers = ModItems.BURNING_ASHES.get();
        return player.getMainHandItem().is(burningEmbers);
    }
}
