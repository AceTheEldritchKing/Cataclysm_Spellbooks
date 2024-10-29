package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.GravitationPullSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.GravityStormSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.VoidRuneBulwarkSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.VoidRuneSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.fire.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ice.MalevolentBattlefieldSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.AmethystPunctureSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.DesertWindsSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.MonolithCrashSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.SandstormSpell;
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

    // Tidal Claw (Summons Tidal Claw that grabs target) - Requires Tidal Claw
    public static final RegistryObject<AbstractSpell> TIDAL_GRAB = registerSpell(new TidalGrabSpell());

    // Conjure Abyssal Gnawers (Summon a swarm of Abyssal Gnawer fish)
    public static final RegistryObject<AbstractSpell> CONJURE_ABYSSAL_GNAWERS = registerSpell(new ConjureAbyssalGnawerSpell());

    // Summon Leviathan (April Fools spell)


    // ENDER //
    // Void Rune (Ender)
    public static final RegistryObject<AbstractSpell> VOID_RUNE = registerSpell(new VoidRuneSpell());

    // Void Bulwark (Summon void rune shield around the caster)
    public static final RegistryObject<AbstractSpell> VOID_BULWARK = registerSpell(new VoidRuneBulwarkSpell());

    // Gravity Storm (Ender)
    public static final RegistryObject<AbstractSpell> GRAVITY_STORM = registerSpell(new GravityStormSpell());

    // Gravitational Pull (Pulls entities in like Gauntlet of Guard)
    public static final RegistryObject<AbstractSpell> GRAVITATION_PULL = registerSpell(new GravitationPullSpell());

    // Summon End Guardian (April Fools spell)


    // EVOCATION //
    // Summon Koboleton (Evocation) (NOT READY YET)

    // Disarm (Disarm the target's current weapon - Koboldiator attack)

    // Steal (Steals target's mainhand item)


    // FIRE //
    // Incineration (Fire) (Summon Fire runes in row) - Requires Burning Ashes
    public static final RegistryObject<AbstractSpell> INCINERATION = registerSpell(new IncinerationSpell());

    // Infernal Strike (Summon mini Incinerator, inflicts blazing brand) - Requires Burning Ashes
    public static final RegistryObject<AbstractSpell> INFERNAL_STRIKE = registerSpell(new InfernalStrikeSpell());

    // Summon Ignited Revenant (Just as the name says) - Requires Burning Ashes (I AM NOT READY AT ALL TO DO THIS SPELL)
    //public static final RegistryObject<AbstractSpell> CONJURE_IGNITED_REINFORCEMENT = registerSpell(new ConjureIgnitedReinforcement());

    // Hellish Blade (Summon a large Incinerator from the ground to strike and lock in a target for a short amount of time, preventing movement) - Requires Burning Ashes
    public static final RegistryObject<AbstractSpell> HELLISH_BLADE = registerSpell(new HellishBladeSpell());

    // Bone Storm (Sprays out blazing bones in all directions like the Revernant)
    public static final RegistryObject<AbstractSpell> BONE_STORM = registerSpell(new BoneStormSpell());

    // Blazing Bone Spit (Shoots out a single blazing bone)
    public static final RegistryObject<AbstractSpell> BONE_PIERCE = registerSpell(new BonePierceSpell());

    // Ashen Breath (Spews out ash breath in front of the caster)
    public static final RegistryObject<AbstractSpell> ASHEN_BREATH = registerSpell(new AshenBreathSpell());

    // Abyss Fireball (Gurl even I don't know wtf it does)
    public static final RegistryObject<AbstractSpell> ABYSS_FIREBALL = registerSpell(new AbyssFireballSpell());

    // Summon Ignis (April Fools spell)


    // LIGHTNING //
    // EMP (Cast an emp blast?)

    // Lock-on (Summon a target particle above the entity's head, stuns and incapacitates them for a few seconds

    // Summon Harbinger (April Fools spell)


    // ICE //
    // Malevolent Battlefield (Summon Maledictus' halberd field)
    public static final RegistryObject<AbstractSpell> MALEVOLENT_BATTLEFIELD = registerSpell(new MalevolentBattlefieldSpell());

    // Forgone Rage (Apply Wrath effect to the user. As the user attacks, it fills a rage meter. Each level is an additional 10% extra damage)

    // Conjure Thrall (Summons the ice undead warriors)

    // Arrow Spray (Release a barrage of icy arrows. Damage increases if the user is holding a bow/crossbow)

    // Rush (Mini boss rush attack?)

    // Phantom Blade (Mini boss blade attack)

    // Malicious Curse (Summons halberds that fall ontop of the target)

    // Summon Maledictus (April Fools spell)


    // NATURE //
    // Sandstorm (Summon desert tornadoes around the user)
    public static final RegistryObject<AbstractSpell> SANDSTORM = registerSpell(new SandstormSpell());

    // Desert Winds (Throw a desert tornado in a path in front of the user. This damages blocks)
    public static final RegistryObject<AbstractSpell> DESERT_WINDS = registerSpell(new DesertWindsSpell());

    // Monolith Crash (Crashes down sandstone monoliths around the caster)
    public static final RegistryObject<AbstractSpell> MONOLITH_CRASH = registerSpell(new MonolithCrashSpell());

    // Amethyst Puncture (Shoots out an amethyst spike)
    public static final RegistryObject<AbstractSpell> AMETHYST_PUNCTURE = registerSpell(new AmethystPunctureSpell());

    // Summon Ancient Remnant (April Fools spell)


    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
