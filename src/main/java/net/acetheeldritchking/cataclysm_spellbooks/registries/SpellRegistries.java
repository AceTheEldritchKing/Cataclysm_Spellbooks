package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.VoidRuneSpell;
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
    public static final RegistryObject<AbstractSpell> ABYSSAL_BLAST = registerSpell(new AbyssalBlastSpell());

    // Dimensional Rift (Summon a rift)
    public static final RegistryObject<AbstractSpell> DIMENSIONAL_RIFT = registerSpell(new DimensionalRiftSpell());

    // Depth Charge (Summon mines)
    public static final RegistryObject<AbstractSpell> DEPTH_CHARGE = registerSpell(new DepthChargeSpell());

    // Abyssal Predator (Buffs while underwater)
    public static final RegistryObject<AbstractSpell> ABYSSAL_PREDATOR = registerSpell(new AbyssalPredatorSpell());

    // Tidal Tear (Melee attack which ends in a shockwave)
    public static final RegistryObject<AbstractSpell> ABYSSAL_SLASH = registerSpell(new AbyssalSlashSpell());

    // Tidal Claw (Summons Tidal Claw that grabs target)

    // Conjure Abyssal Gnawers (Summon a swarm of Abyssal Gnawer fish
    public static final RegistryObject<AbstractSpell> CONJURE_ABYSSAL_GNAWERS = registerSpell(new ConjureAbyssalGnawerSpell());


    // ENDER //
    // Void Rune (Ender)
    public static final RegistryObject<AbstractSpell> VOID_RUNE = registerSpell(new VoidRuneSpell());

    // Void Bulwark (Summon void rune shield in a semi circle around caster)

    // Gravity Storm (Ender)


    // EVOCATION //
    // Summon Koboleton (Evocation)

    // Disarm (Disarm the target's current weapon - Koboldiator attack)


    // FIRE //
    // Incineration (Fire) (Summon Fire runes in row)

    // Infernal Strike (Summon mini Incinerator)

    // Summon Ignited Revenant (Just as the name says)

    // Hellish Blade (Summon a large Incinerator from the ground to strike and lock in a target for a short amount of time, preventing movement)


    // LIGHTNING //
    // EMP (Cast an emp blast?)


    // ICE //
    // Malevolent Battlefield (Summon Maledictus' halberd field)

    // Forgone Rage (Apply Wrath effect to the user. As the user attacks, it fills a rage meter. Each level is an additional 10% extra damage)

    // Conjure Thrall (Summons the ice undead warriors)

    // Arrow Spray (Release a barrage of icy arrows. Damage increases if the user is holding a bow/crossbow)


    // NATURE //
    // Sandstorm (Summon desert tornados around the user)

    // Desert Winds (Strike the ground, causing a damaging quake around the user. Stronger than earthquake)

    // Grave Tremor (Strike the ground, causing a damaging quake around the user. Stronger than earthquake)


    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
