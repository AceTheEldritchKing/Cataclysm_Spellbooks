package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawer;
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

    public static void register(IEventBus eventBus)
    {
        ENTITIES.register(eventBus);
    }
}
