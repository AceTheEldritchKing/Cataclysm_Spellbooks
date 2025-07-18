package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.blood.HemorrhagingImpactSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.GravitationPullSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.GravityStormSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.VoidRuneBulwarkSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ender.VoidRuneSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.evocation.PilferSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.fire.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.holy.ConjureKoboldiatorSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.holy.ConjureKoboletonSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.holy.ThothsWitnessSpell;
import net.acetheeldritchking.cataclysm_spellbooks.spells.ice.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.*;
import net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy.*;
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

    /***
     * Abyssal
     */
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

    // Summon Coral Golem (Summons a few coral golem guys)
    public static final RegistryObject<AbstractSpell> CONJURE_CORAL_GOLEM = registerSpell(new ConjureCoralGolemSpell());

    // Summon Corallusus (Summons a singular Corallusus for you to ride)
    public static final RegistryObject<AbstractSpell> CONJURE_CORALSSUS = registerSpell(new ConjureCoralssusSpell());

    // Summon Lionfish Swarm (Summons a bunch of lionfish, can only be casted while in the water)

    // Wrath of The Abyss (Apply an effect to yourself that prevents your death. This applies an effect that boosts your spell power but reduces your max health)

    //
    // THESE ARE FOR 1.21.1 ONLY - I JUST HAVE THESE HERE FOR PLANNING //
    //

    // Delta Pressure [ABYSSAL]: Evaluate the pressure between the caster and the target. If the target has less pressure than the caster, equalize the pressure and deal damage to the target based on the difference in pressure. If the caster has more pressure than the target, equalize and debuff the caster. Pressure is influenced by the gravitational attributes of the target and caster, whether the caster is in water or not, and whether the caster has the Pressurized effect.

    // Pressurize [ABYSSAL]: Increases the caster's gravity outside of water and apply the pressurized status effect.

    // Aquatic Lungs [ABYSSAL]: Grant the caster the ability to breathe underwater for a short time.

    // Whalefall [ABYSSAL]: Apply the Whalefall effect on the targeted entity. When that entity dies, creates an explosion that heals the caster and nearby entities.

    // Thermal Adaptation [ABYSSAL]: Boil the water around you, granting you increased resistance against fire spells. Can only be casted while underwater, effect clears once the caster leaves the water.

    // Abyssal Prey [ABYSSAL]: Target an entity; you deal more damage to that specific entity, however, you will deal less damage to any other entity.

    // Midnight Zone [ABYSSAL]: In a radius around the caster, inflict darkness and blindness to all entities within that radius. Can only be casted while underwater. All entities in range take more damage from Abyssal spells.

    // Aquatic Snow [ABYSSAL]: Summon aquatic snow around the caster, heals a small amount per time spent in the snow.

    // Bio-Disorient [ABYSSAL]: The caster turns transparent, gains bioluminescent, and creates copies of itself to distract targets. Can only be casted while under the water.

    // Submerge [ABYSSAL]: Greatly increase the target's pressure and gravity, increasing even more while the target is in the water.

    // Downpour/Deluge (Summons a rainstorm around the caster, entities within become conductive)

    // Riptide (Drag nearby entities into you, dealing damage to them. Only casted in water)

    // Undercurrent (Dash in the direction you are looking in. Increased power when underwater)

    // Saltwater Spray (Spray saltwater, entities caught in it become conductive)

    // Schooling (Based on the amount of fish entities near you, your abyssal spell power is increased for X amount of spells. Only works underwater)

    // Alkaline Waters (Clears all effects from the caster, negative and positive)

    // Squid Launcher (Unleash an empowered squid in the direction the caster is looking in - Apparition Maw exclusive)

    // Tide Turn (Hold a defensive stance, increasing spell resistance and clearing negative effects. After the spell finishes, spell power is increased - Lacerator exclusive)

    /***
     * Blood
     */
    // Hemorrhaging Impact (Shoot out crystallized blood followed by several blood needles. The blood crystals, on impact, inflict bleeding & hemophilia)
    public static final RegistryObject<AbstractSpell> HEMORRHAGING_IMPACT = registerSpell(new HemorrhagingImpactSpell());


    /***
     * Ender
     */
    // Void Rune (Ender)
    public static final RegistryObject<AbstractSpell> VOID_RUNE = registerSpell(new VoidRuneSpell());

    // Void Bulwark (Summon void rune shield around the caster)
    public static final RegistryObject<AbstractSpell> VOID_BULWARK = registerSpell(new VoidRuneBulwarkSpell());

    // Gravity Storm (Ender)
    public static final RegistryObject<AbstractSpell> GRAVITY_STORM = registerSpell(new GravityStormSpell());

    // Gravitational Pull (Pulls entities in like Gauntlet of Guard)
    public static final RegistryObject<AbstractSpell> GRAVITATION_PULL = registerSpell(new GravitationPullSpell());

    // Shell Smash (Envelop yourself in a thick shell, reducing damage to yourself. After X amount of hits, the shell breaks and you briefly gain speed and extra spell power for X number of attacks)


    /***
     * Evocation
     */
    // Steal (Steals target's mainhand item)
    public static final RegistryObject<AbstractSpell> PILFER = registerSpell(new PilferSpell());


    /***
     * Holy
     */
    // Summon Koboldiator
    public static final RegistryObject<AbstractSpell> CONJURE_KOBOLDIATOR = registerSpell(new ConjureKoboldiatorSpell());

    // Summon Koboleton (Summon Koboletons)
    public static final RegistryObject<AbstractSpell> CONJURE_KOBOLETON = registerSpell(new ConjureKoboletonSpell());

    // Thoth's Witness (For less than a minute, summon the ghost of The Ancient Remnant to fight for you.)
    public static final RegistryObject<AbstractSpell> THOTHS_WITNESS = registerSpell(new ThothsWitnessSpell());


    /***
     * Fire
     */
    // Incineration (Fire) (Summon Fire runes in row) - Requires Burning Ashes
    public static final RegistryObject<AbstractSpell> INCINERATION = registerSpell(new IncinerationSpell());

    // Infernal Strike (Summon mini Incinerator, inflicts blazing brand) - Requires Burning Ashes
    public static final RegistryObject<AbstractSpell> INFERNAL_STRIKE = registerSpell(new InfernalStrikeSpell());

    // Summon Ignited Revenant (Just as the name says) - Requires Burning Ashes (I AM NOT READY AT ALL TO DO THIS SPELL)
    public static final RegistryObject<AbstractSpell> CONJURE_IGNITED_REINFORCEMENT = registerSpell(new ConjureIgnitedReinforcement());

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

    // Tectonic Tremble
    public static final RegistryObject<AbstractSpell> TECTONIC_TREMBLE = registerSpell(new TectonicTrembleSpell());

    // Meteor Spew (Ejects Monstrosity big lava bomb)
    public static final RegistryObject<AbstractSpell> METEOR_SPEW = registerSpell(new MeteorSpewSpell());

    // Comet Shower (Shoots out the Monstrosity little fire bombs)
    public static final RegistryObject<AbstractSpell> COMET_SHOWER = registerSpell(new CometShowerSpell());

    // Avatar of Flame (Ignite yourself on fire, at half health you turn into soul fire and your fire spell power is increased but every other element is decreased. Prevents you from being placed on fire, all your attacks ignite opponents. Buffs to Ignis spells)

    // Infernal Inhalation (Absorb any lava blocks near you, healing you for how much lava is around you)

    // Scorched Earth (Shoot out a barrage of molten bullets that combust on the ground, igniting entities caught within)
    public static final RegistryObject<AbstractSpell> SCORCHED_EARTH = registerSpell(new ScorchedEarthSpell());


    /***
     * Lightning
     */


    /***
     * Ice
     */
    // Malevolent Battlefield (Summon Maledictus' halberd field)
    public static final RegistryObject<AbstractSpell> MALEVOLENT_BATTLEFIELD = registerSpell(new MalevolentBattlefieldSpell());

    // Forgone Rage (Apply Wrath effect to the user. As the user attacks, it fills a rage meter. Each level is an additional 10% extra damage)
    public static final RegistryObject<AbstractSpell> FORGONE_RAGE = registerSpell(new ForgoneRageSpell());

    // Conjure Thrall (Summons the ice undead warriors)
    public static final RegistryObject<AbstractSpell> CONJURE_THRALL = registerSpell(new ConjureThrallsSpell());

    // Arrow Spray (Release a barrage of icy arrows. Damage increases if the user is holding a bow/crossbow)

    // Cursed Rush (Mini boss rush attack + Soul Render charge)
    public static final RegistryObject<AbstractSpell> CURSED_RUSH = registerSpell(new CursedRushSpell());

    // Phantom Blade (Mini boss blade attack)
    public static final RegistryObject<AbstractSpell> PHANTASMAL_BLADE = registerSpell(new PhantasmalBladeSpell());

    // Malicious Curse (Summons halberds that fall ontop of the target)

    // Phantasmal Slam (Summons either a custom winged entity or Maledictus to grab and slam the target)

    // Echoed Whirlwind/Snow Squall (Summons Wrath of The Desert sandstorms)

    // Cryopiercer (Shoot a blast of freezing cold energy, spawning a circle of ice spikes on impact. Entities hit are encased in ice which shatters after a few seconds)
    public static final RegistryObject<AbstractSpell> CRYOPIERCER = registerSpell(new CryopiercerSpell());


    /***
     * Nature
     */
    // Sandstorm (Summon desert tornadoes around the user)
    public static final RegistryObject<AbstractSpell> SANDSTORM = registerSpell(new SandstormSpell());

    // Desert Winds (Throw a desert tornado in a path in front of the user. This damages blocks)
    public static final RegistryObject<AbstractSpell> DESERT_WINDS = registerSpell(new DesertWindsSpell());

    // Monolith Crash (Crashes down sandstone monoliths around the caster)
    public static final RegistryObject<AbstractSpell> MONOLITH_CRASH = registerSpell(new MonolithCrashSpell());

    // Amethyst Puncture (Shoots out an amethyst spike)
    public static final RegistryObject<AbstractSpell> AMETHYST_PUNCTURE = registerSpell(new AmethystPunctureSpell());

    // Summon Amethyst Crab (Summons The Crab:tm:, can ride it)
    public static final RegistryObject<AbstractSpell> CONJURE_AMETHYST_CRAB = registerSpell(new ConjureAmethystCrabSpell());

    // Pharaoh's Wrath (Every hit you take increases your wrath. At max wrath, all of your attacks inflict the Desert's Curse effect, and summons several sandstorms around you. You gain strong debuffs afterwards)
    public static final RegistryObject<AbstractSpell> PHARAOHS_WRATH = registerSpell(new PharaohsWrathSpell());

    // Diamond Storm (Rain down amethyst chunks around the caster. Radius scales with level)


    /***
     * Technomancy
     */
    // EMP (Cast an emp blast?)
    public static final RegistryObject<AbstractSpell> EMP_BLAST = registerSpell(new EMPSpell());

    // Lock-on (Summon a target particle above the entity's head, stuns and incapacitates them for a few seconds)
    public static final RegistryObject<AbstractSpell> LOCK_ON = registerSpell(new LockOnSpell());

    // Hijack (Steals a target's summons for yourself - 1.21.1)

    // Laserbolt (Shoots out the little Harbinger small laser)
    public static final RegistryObject<AbstractSpell> LASERBOLT = registerSpell(new LaserboltSpell());

    // Atomic Laser (Harbinger big laser blast)
    public static final RegistryObject<AbstractSpell> ATOMIC_LASER = registerSpell(new AtomicLaserSpell());

    // DoS Swarm (Summons a swarm of Watchers that act as a counterspell projectiles - 1.21.1)
    public static final RegistryObject<AbstractSpell> DOS_SWARM = registerSpell(new DoSSwarmSpell());

    // Missile Launch (Shoots out a missile)
    public static final RegistryObject<AbstractSpell> MISSILE_LAUNCH = registerSpell(new MissileLaunchSpell());

    // Construct: Watchers (Summons a group of Watchers)
    public static final RegistryObject<AbstractSpell> CONSTRUCT_WATCHERS = registerSpell(new ConstructWatchersSpell());

    // Construct: Prowler (Summons a Prowler)
    public static final RegistryObject<AbstractSpell> CONSTRUCT_PROWLER = registerSpell(new ConstructProwlerSpell());

    // DDoS (AoE counterspell)
    public static final RegistryObject<AbstractSpell> DDOS = registerSpell(new DDoSSpell());

    // Shutdown (Prevent the target from attacking or using items. Does not stack with Lock-on)
    public static final RegistryObject<AbstractSpell> SHUTDOWN = registerSpell(new ShutdownSpell());

    // Rewire (Buff selected summons' speed and damage, reducing their armor and armor toughness)
    public static final RegistryObject<AbstractSpell> REWIRE = registerSpell(new RewireSpell());

    // Hardware Update (Increases your damage and armor, does not stack with charge/clears it)
    public static final RegistryObject<AbstractSpell> HARDWARE_UPDATE = registerSpell(new HardwareUpdateSpell());

    // Software Update (Increases your speed and cooldown, does not stack with charge and haste/clears it)
    public static final RegistryObject<AbstractSpell> SOFTWARE_UPDATE = registerSpell(new SoftwareUpdateSpell());

    // Bothearder (AoE summon steal - 1.21.1)

    // Flash Bang (Throw a live grenade, blinding nearby entities within the blast zone)
    public static final RegistryObject<AbstractSpell> FLASH_BANG = registerSpell(new FlashBangSpell());

    // Aerial Assault (Summon various missiles down from the sky)
    public static final RegistryObject<AbstractSpell> AERIAL_ASSAULT = registerSpell(new AerialAssaultSpell());

    // Intrusion Prevention System (Reduce all incoming projectile damage, prevent summons from being counterspelled)
    public static final RegistryObject<AbstractSpell> INTRUSION_PREVENTION_SYSTEM = registerSpell(new IntrusionPreventionSystemSpell());

    // Overclock (Special imbue on Excelsius armor, overclock the caster which unlocks more capabilities and stats from the armor)
    public static final RegistryObject<AbstractSpell> OVERCHARGED = registerSpell(new OverchargedSpell());

    // Parting Shot (Exclusive to The Subjugator, shoots out two shots that inflict Wither)
    public static final RegistryObject<AbstractSpell> PARTING_SHOT = registerSpell(new PartingShotSpell());

    // Disabling Swipe (Charge up a slash attack, the swipe prevents hit entities from healing)
    public static final RegistryObject<AbstractSpell> DISABLING_SWIPE = registerSpell(new DisablingSwipeSpell());

    // Gear Shift (A multicast movement spell, dash forwards when standing and upwards when crouching)
    public static final RegistryObject<AbstractSpell> GEAR_SHIFT = registerSpell(new GearShiftSpell());

    // Reboot (Heals the caster and any nearby summons)
    public static final RegistryObject<AbstractSpell> REBOOT = registerSpell(new RebootSpell());

    // Surveillance Drone (Summons a drone on top of your selected summon, healing nearby summons and clearing any negative status effects)
    public static final RegistryObject<AbstractSpell> SURVEILLANCE_DRONE = registerSpell(new SurveillanceDroneSpell());


    public static void register(IEventBus eventBus)
    {
        SPELLS.register(eventBus);
    }
}
