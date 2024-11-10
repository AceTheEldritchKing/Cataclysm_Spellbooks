package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedIgnitedBerserker;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedIgnitedRevenant;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedKoboldiator;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blazing_aoe.BlazingAoE;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeProjectile;
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

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
