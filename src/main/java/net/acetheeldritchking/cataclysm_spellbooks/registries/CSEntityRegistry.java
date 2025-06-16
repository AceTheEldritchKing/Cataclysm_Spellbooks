package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.*;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blazing_aoe.BlazingAoE;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.extended.ExtendedDeathLaserBeamEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.extended.ExtendedLaserBeamEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.flash_bang.FlashBangProjectileEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.parting_shot.PartingShotProjectile;
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

    // Abyssal Gnawers Entity
    public static final RegistryObject<EntityType<SummonedAbyssalGnawer>> ABYSSAL_GNAWERS =
            ENTITIES.register("abyssal_gnawers", () -> EntityType.Builder.<SummonedAbyssalGnawer>of
                    (SummonedAbyssalGnawer::new, MobCategory.MONSTER).
                    sized(0.9f, 1f)
                    .build(
                            new ResourceLocation(CataclysmSpellbooks.MOD_ID, "abyssal_gnawers").toString()
                    ));

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


    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
