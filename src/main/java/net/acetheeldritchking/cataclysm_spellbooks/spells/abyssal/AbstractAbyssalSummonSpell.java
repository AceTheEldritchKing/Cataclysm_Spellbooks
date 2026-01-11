package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;

import net.acetheeldritchking.cataclysm_spellbooks.spells.AbstractSummonSpell;

public abstract class AbstractAbyssalSummonSpell extends AbstractSummonSpell {
    // Yeah this is pretty much it
    @Override
    public boolean allowLooting() {
        return false;
    }
}
