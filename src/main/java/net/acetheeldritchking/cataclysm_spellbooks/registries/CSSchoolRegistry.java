package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.SchoolType;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSTags;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CSSchoolRegistry extends SchoolRegistry {
    private static final DeferredRegister<SchoolType> CATACLYSM_SCHOOLS = DeferredRegister.create(SCHOOL_REGISTRY_KEY, CataclysmSpellbooks.MOD_ID);

    public static void register(IEventBus eventBus)
    {
        CATACLYSM_SCHOOLS.register(eventBus);
    }

    private static RegistryObject<SchoolType> registerSchool(SchoolType type)
    {
        return CATACLYSM_SCHOOLS.register(type.getId().getPath(), () -> type);
    }

    public static final ResourceLocation ABYSSAL_RESOURCE = CataclysmSpellbooks.id("abyssal");

    /*public static final RegistryObject<SchoolType> ABYSSAL = registerSchool(new SchoolType
            (
                    ABYSSAL_RESOURCE,
                    CSTags.ABYSSAL_FOCUS,
                    Component.translatable("school.cataclysm_spellbooks.abyssal")
            ));*/
}
