package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawer;
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

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
