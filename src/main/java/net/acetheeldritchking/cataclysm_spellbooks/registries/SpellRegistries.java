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

    //        //
    // Spells //
    //        //

    // ABYSSAL //
    // Void Beam
    public static final RegistryObject<AbstractSpell> VOID_BEAM = registerSpell(new VoidBeamSpell());

    // Abyssal Blast (Summon Leviathan death beam)

    // Dimensional Rift (Summon a rift)


    // ENDER //
    // Void Rune (Ender)
    public static final RegistryObject<AbstractSpell> VOID_RUNE = registerSpell(new VoidRuneSpell());

    // Void Bulwark (Summon void rune shield in a semi circle around caster)

    // Gravity Storm (Ender)


    // EVOCATION //
    // Summon Koboleton (Evocation)


    // FIRE //
    // Sandstorm (Like Ancient Remnant) (Fire?)

    // Incineration (Fire) (Summon Fire runes in row)

    // Infernal Strike (Summon mini Incinerator)

    // Summon Ignited Revenant (Just as the name says


    // LIGHTNING
    // EMP (Cast an emp blast?)

    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
