package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.effect.SummonTimer;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
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

    public static final RegistryObject<MobEffect> INCAPACITATED_EFFECT =
            MOB_EFFECTS.register("incapacitated_effect", IncapacitatedPotionEffect::new);

    public static final RegistryObject<SummonTimer> ABYSSAL_GNAWER_TIMER =
            MOB_EFFECTS.register("abyssal_gnawer_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 0xbea925));

    public static final RegistryObject<SummonTimer> IGNITED_TIMER =
            MOB_EFFECTS.register("ignited_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 16734003));

    public static final RegistryObject<MobEffect> WRATHFUL =
            MOB_EFFECTS.register("wrathful_effect", WrathfulPotionEffect::new);

    public static final RegistryObject<SummonTimer> KOBOLDIATOR_TIMER =
            MOB_EFFECTS.register("koboldiator_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 16443474));

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
