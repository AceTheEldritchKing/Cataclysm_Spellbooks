package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.VoidBeamSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.VoidRuneSpell;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class SpellRegistries {
    public static final DeferredRegister<AbstractSpell> SPELLS = DeferredRegister.create(SpellRegistry.SPELL_REGISTRY_KEY, CataclysmSpellbooks.MOD_ID);

    public static RegistryObject<AbstractSpell> registerSpell(AbstractSpell spell)
    {
        return SPELLS.register(spell.getSpellName(), () -> spell);
    }

    // Spells

    // Void Beam
    public static final RegistryObject<AbstractSpell> VOID_BEAM = registerSpell(new VoidBeamSpell());

    // Sandstorm (Like Ancient Remnant) (Fire?)

    // Summon Koboleton (Evocation)

    // Void Rune (Ender)
    public static final RegistryObject<AbstractSpell> VOID_RUNE = registerSpell(new VoidRuneSpell());

    // Gravity Storm (Ender/Eldritch?)

    // Incineration (Fire) (Summon Fire runes in row)

    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
