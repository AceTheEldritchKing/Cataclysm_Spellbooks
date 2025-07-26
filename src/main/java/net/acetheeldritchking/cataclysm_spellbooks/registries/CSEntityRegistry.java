package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.*;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blood_crystal.BloodCrystalProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.bullets.FrozenBulletProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.bullets.MoltenBulletProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.no_man_zone.NoManZoneAoE;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blazing_aoe.BlazingAoE;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.disabling_swipe.DisablingSwipeAoE;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.extended.ExtendedDeathLaserBeamEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.extended.ExtendedLaserBeamEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.flash_bang.FlashBangProjectileEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.parting_shot.PartingShotProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.quick_strike.QuickStrikeAoE;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.scorched_earth_aoe.ScorchedEarthAoE;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSEntityRegistry {
    private static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, CataclysmSpellbooks.MOD_ID);

    // Infernal Blade Projectile
    public static final RegistryObject<EntityType<InfernalBladeProjectile>> INFERNAL_BLADE_PROJECTILE =
            ENTITIES.register("infernal_blade", () -> EntityType.Builder.<InfernalBladeProjectile>of(InfernalBladeProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "infernal_blade").toString()
                    ));

    // Hellish Blade Projectile
    public static final RegistryObject<EntityType<HellishBladeProjectile>> HELLISH_BLADE_PROJECTILE =
            ENTITIES.register("hellish_blade", () -> EntityType.Builder.<HellishBladeProjectile>of(HellishBladeProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "hellish_blade").toString()
                    ));

    // Blazing AoE Entity
    public static final RegistryObject<EntityType<BlazingAoE>> BLAZING_AOE_ENTITY =
            ENTITIES.register("blazing_aoe", () -> EntityType.Builder.<BlazingAoE>of(BlazingAoE::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "blazing_aoe").toString()
                    ));

    // Summoned Ignited Revenant
    public static final RegistryObject<EntityType<SummonedIgnitedRevenant>> SUMMONED_IGNITED_REVENANT =
            ENTITIES.register("summoned_ignited_revenant", () -> EntityType.Builder.<SummonedIgnitedRevenant>of
                            (SummonedIgnitedRevenant::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_ignited_revenant").toString()
                    ));

    // Summoned Ignited Berserker
    public static final RegistryObject<EntityType<SummonedIgnitedBerserker>> SUMMONED_IGNITED_BERSERKER =
            ENTITIES.register("summoned_ignited_berserker", () -> EntityType.Builder.<SummonedIgnitedBerserker>of
                            (SummonedIgnitedBerserker::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_ignited_berserker").toString()
                    ));

    // Summoned Koboldiator
    public static final RegistryObject<EntityType<SummonedKoboldiator>> SUMMONED_KOBOLDIATOR =
            ENTITIES.register("summoned_koboldiator", () -> EntityType.Builder.<SummonedKoboldiator>of
                            (SummonedKoboldiator::new, MobCategory.MONSTER).
                    sized(2.5f, 4.5f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_koboldiator").toString()
                    ));

    // Summoned Koboleton
    public static final RegistryObject<EntityType<SummonedKoboleton>> SUMMONED_KOBOLETON =
            ENTITIES.register("summoned_koboleton", () -> EntityType.Builder.<SummonedKoboleton>of
                            (SummonedKoboleton::new, MobCategory.MONSTER).
                    sized(1f, 2f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_koboleton").toString()
                    ));

    // Summoned Draugur
    public static final RegistryObject<EntityType<SummonedDraugur>> SUMMONED_DRAUGUR =
            ENTITIES.register("summoned_draugur", () -> EntityType.Builder.<SummonedDraugur>of
                            (SummonedDraugur::new, MobCategory.MONSTER).
                    sized(1f, 2f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_draugur").toString()
                    ));

    // Summoned Royal Draugur
    public static final RegistryObject<EntityType<SummonedRoyalDraugur>> SUMMONED_ROYAL_DRAUGUR =
            ENTITIES.register("summoned_royal_draugur", () -> EntityType.Builder.<SummonedRoyalDraugur>of
                            (SummonedRoyalDraugur::new, MobCategory.MONSTER).
                    sized(1f, 2f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_royal_draugur").toString()
                    ));

    // Summoned Elite Draugur
    public static final RegistryObject<EntityType<SummonedEliteDraugur>> SUMMONED_ELITE_DRAUGUR =
            ENTITIES.register("summoned_elite_draugur", () -> EntityType.Builder.<SummonedEliteDraugur>of
                            (SummonedEliteDraugur::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_elite_draugur").toString()
                    ));

    // Summoned Aptrgangr
    public static final RegistryObject<EntityType<SummonedAptrgangr>> SUMMONED_APTRGANGR =
            ENTITIES.register("summoned_aptrgangr", () -> EntityType.Builder.<SummonedAptrgangr>of
                            (SummonedAptrgangr::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_aptrgangr").toString()
                    ));

    // Laser Beam
    public static final RegistryObject<EntityType<ExtendedLaserBeamEntity>> EXTENDED_LASER_BEAM =
            ENTITIES.register("extended_laser_beam", () -> EntityType.Builder.<ExtendedLaserBeamEntity>of
                            (ExtendedLaserBeamEntity::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "extended_laser_beam").toString()
                    ));

    // Death Laser
    public static final RegistryObject<EntityType<ExtendedDeathLaserBeamEntity>> EXTENDED_DEATH_LASER_BEAM =
            ENTITIES.register("extended_death_laser_beam", () -> EntityType.Builder.<ExtendedDeathLaserBeamEntity>of
                            (ExtendedDeathLaserBeamEntity::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "extended_death_laser_beam").toString()
                    ));

    // Summoned Watcher
    public static final RegistryObject<EntityType<SummonedWatcher>> SUMMONED_WATCHER =
            ENTITIES.register("summoned_watcher", () -> EntityType.Builder.<SummonedWatcher>of
                            (SummonedWatcher::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_watcher").toString()
                    ));

    // Summoned Prowler
    public static final RegistryObject<EntityType<SummonedProwler>> SUMMONED_PROWLER =
            ENTITIES.register("summoned_prowler", () -> EntityType.Builder.<SummonedProwler>of
                            (SummonedProwler::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_prowler").toString()
                    ));

    // Summoned Counterspell Watcher
    public static final RegistryObject<EntityType<SummonedCounterspellWatcher>> SUMMONED_COUNTERSPELL_WATCHER =
            ENTITIES.register("summoned_counterspell_watcher", () -> EntityType.Builder.<SummonedCounterspellWatcher>of
                            (SummonedCounterspellWatcher::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_counterspell_watcher").toString()
                    ));

    // Parting Shot Projectile
    public static final RegistryObject<EntityType<PartingShotProjectile>> PARTING_SHOT_PROJECTILE =
            ENTITIES.register("parting_shot", () -> EntityType.Builder.<PartingShotProjectile>of(PartingShotProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "parting_shot").toString()
                    ));

    // Parting Shot Projectile
    public static final RegistryObject<EntityType<SurveillanceDroneEntity>> SURVEILLANCE_DRONE =
            ENTITIES.register("surveillance_drone", () -> EntityType.Builder.<SurveillanceDroneEntity>of(SurveillanceDroneEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "surveillance_drone").toString()
                    ));

    // Flash Bang Projectile
    public static final RegistryObject<EntityType<FlashBangProjectileEntity>> FLASH_BANG_PROJECTILE =
            ENTITIES.register("flash_bang", () -> EntityType.Builder.<FlashBangProjectileEntity>of(FlashBangProjectileEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "flash_bang").toString()
                    ));

    // Disabling Swipe
    public static final RegistryObject<EntityType<DisablingSwipeAoE>> DISABLING_SWIPE =
            ENTITIES.register("disabling_swipe", () -> EntityType.Builder.<DisablingSwipeAoE>of(DisablingSwipeAoE::new, MobCategory.MISC)
                    .sized(5f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "disabling_swipe").toString()
                    ));

    // No-Man Zone AoE Entity
    public static final RegistryObject<EntityType<NoManZoneAoE>> NO_MAN_ZONE_AOE =
            ENTITIES.register("no_man_zone_aoe", () -> EntityType.Builder.<NoManZoneAoE>of(NoManZoneAoE::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "no_man_zone_aoe").toString()
                    ));

    // Summoned Amethyst Crab
    public static final RegistryObject<EntityType<SummonedAmethystCrab>> SUMMONED_AMETHYST_CRAB =
            ENTITIES.register("summoned_amethyst_crab", () -> EntityType.Builder.<SummonedAmethystCrab>of
                            (SummonedAmethystCrab::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_amethyst_crab").toString()
                    ));

    // Summoned Coral Golem
    public static final RegistryObject<EntityType<SummonedCoralGolem>> SUMMONED_CORAL_GOLEM =
            ENTITIES.register("summoned_coral_golem", () -> EntityType.Builder.<SummonedCoralGolem>of
                            (SummonedCoralGolem::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_coral_golem").toString()
                    ));

    // Summoned Coralssus
    public static final RegistryObject<EntityType<SummonedCoralssus>> SUMMONED_CORALSSUS =
            ENTITIES.register("summoned_coralssus", () -> EntityType.Builder.<SummonedCoralssus>of
                            (SummonedCoralssus::new, MobCategory.MONSTER).
                    sized(1f, 3f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "summoned_coralssus").toString()
                    ));

    // Phantom Ancient Remnant
    public static final RegistryObject<EntityType<PhantomAncientRemnant>> PHANTOM_ANCIENT_REMNANT =
            ENTITIES.register("phantom_ancient_remnant", () -> EntityType.Builder.<PhantomAncientRemnant>of
                            (PhantomAncientRemnant::new, MobCategory.MONSTER).
                    sized(5f, 5f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "phantom_ancient_remnant").toString()
                    ));

    // Blood Crystal
    public static final RegistryObject<EntityType<BloodCrystalProjectile>> BLOOD_CRYSTAL_PROJECTILE =
            ENTITIES.register("blood_crystal", () -> EntityType.Builder.<BloodCrystalProjectile>of(BloodCrystalProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "blood_crystal").toString()
                    ));

    // Molten Bullet
    public static final RegistryObject<EntityType<MoltenBulletProjectile>> MOLTEN_BULLET_PROJECTILE =
            ENTITIES.register("molten_bullet", () -> EntityType.Builder.<MoltenBulletProjectile>of(MoltenBulletProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "molten_bullet").toString()
                    ));

    // Scorched Earth AoE
    public static final RegistryObject<EntityType<ScorchedEarthAoE>> SCORCHED_EARTH_AOE =
            ENTITIES.register("scorched_earth_aoe", () -> EntityType.Builder.<ScorchedEarthAoE>of(ScorchedEarthAoE::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "scorched_earth_aoe").toString()
                    ));

    // Frozen Bullet
    public static final RegistryObject<EntityType<FrozenBulletProjectile>> FROZEN_BULLET_PROJECTILE =
            ENTITIES.register("frozen_bullet", () -> EntityType.Builder.<FrozenBulletProjectile>of(FrozenBulletProjectile::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "frozen_bullet").toString()
                    ));

    // Glacial Block
    public static final RegistryObject<EntityType<GlacialBlockEntity>> GLACIAL_BLOCK =
            ENTITIES.register("glacial_block", () -> EntityType.Builder.<GlacialBlockEntity>of(GlacialBlockEntity::new, MobCategory.MISC)
                    .sized(1f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "glacial_block").toString()
                    ));

    // Quick Strike
    public static final RegistryObject<EntityType<QuickStrikeAoE>> QUICK_STRIKE =
            ENTITIES.register("quick_strike", () -> EntityType.Builder.<QuickStrikeAoE>of(QuickStrikeAoE::new, MobCategory.MISC)
                    .sized(5f, 1f)
                    .clientTrackingRange(64)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "quick_strike").toString()
                    ));

    // Final Rend


    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
