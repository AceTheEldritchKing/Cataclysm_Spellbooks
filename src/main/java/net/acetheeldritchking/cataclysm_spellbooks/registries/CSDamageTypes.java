package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageScaling;
import net.minecraft.world.damagesource.DamageType;

public class CSDamageTypes {
    public static ResourceKey<DamageType> register(String name)
    {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(CataclysmSpellbooks.MOD_ID, name));
    }

    public static final ResourceKey<DamageType> ABYSSAL_MAGIC = register("abyssal_magic");
    public static final ResourceKey<DamageType> TECHNOMANCY_MAGIC = register("technomancy_magic");

    public static void bootstrap(BootstapContext<DamageType> context)
    {
        context.register(ABYSSAL_MAGIC, new DamageType(ABYSSAL_MAGIC.location().getPath(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0F));
        context.register(TECHNOMANCY_MAGIC, new DamageType(TECHNOMANCY_MAGIC.location().getPath(), DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0F));
    }
}
