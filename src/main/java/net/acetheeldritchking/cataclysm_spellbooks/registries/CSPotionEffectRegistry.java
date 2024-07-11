package net.acetheeldritchking.cataclysm_spellbooks.registries;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.AbyssalPredatorPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.VoidRunePotionEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSPotionEffectRegistry {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, CataclysmSpellbooks.MOD_ID);

    public static final RegistryObject<MobEffect> SUMMON_VOID_RUNE =
            MOB_EFFECTS.register("summon_void_rune", VoidRunePotionEffect::new);

    public static final RegistryObject<MobEffect> ABYSSAL_PREDATOR_EFFECT =
            MOB_EFFECTS.register("abyssal_predator_effect", AbyssalPredatorPotionEffect::new);

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
