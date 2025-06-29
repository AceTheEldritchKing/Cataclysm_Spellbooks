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

    public static final RegistryObject<SummonTimer> KOBOLDETON_TIMER =
            MOB_EFFECTS.register("koboleton_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 16443474));

    public static final RegistryObject<MobEffect> CURSED_FRENZY =
            MOB_EFFECTS.register("cursed_frenzy", CursedFrenzyEffect::new);

    public static final RegistryObject<SummonTimer> DRAUGUR_TIMER =
            MOB_EFFECTS.register("draugur_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 4583645));

    public static final RegistryObject<SummonTimer> WATCHER_TIMER =
            MOB_EFFECTS.register("watcher_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 4583645));

    public static final RegistryObject<SummonTimer> PROWLER_TIMER =
            MOB_EFFECTS.register("prowler_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 4583645));

    public static final RegistryObject<MobEffect> SHUTDOWN_EFFECT =
            MOB_EFFECTS.register("shutdown_effect", ShutdownPotionEffect::new);

    public static final RegistryObject<MobEffect> REWIRED_EFFECT =
            MOB_EFFECTS.register("rewired_effect", RewirePotionEffect::new);

    public static final RegistryObject<MobEffect> HARDWARE_UPDATE_EFFECT =
            MOB_EFFECTS.register("hardware_update_effect", HardwireUpdatePotionEffect::new);

    public static final RegistryObject<MobEffect> SOFTWARE_UPDATE_EFFECT =
            MOB_EFFECTS.register("software_update_effect", SoftwareUpdatePotionEffect::new);

    public static final RegistryObject<MobEffect> IPS_POTION_EFFECT =
            MOB_EFFECTS.register("intrusion_defense_system_software", IPSPotionEffect::new);

    public static final RegistryObject<MobEffect> MANA_OVERCHARGED_EFFECT =
            MOB_EFFECTS.register("mana_overcharged_effect", ManaOverchargedPotionEffect::new);

    public static final RegistryObject<MobEffect> DISABLED_EFFECT =
            MOB_EFFECTS.register("disabled_effect", DisabledPotionEffect::new);

    public static final RegistryObject<MobEffect> SPELL_RES_OVERCHARGED_EFFECT =
            MOB_EFFECTS.register("spell_res_overcharged_effect", SpellResOverchargedPotionEffect::new);

    public static final RegistryObject<MobEffect> COOLDOWN_OVERCHARGED_EFFECT =
            MOB_EFFECTS.register("cooldown_overcharged_effect", CooldownOverchargedPotionEffect::new);

    public static final RegistryObject<MobEffect> BASE_OVERCHARGED_EFFECT =
            MOB_EFFECTS.register("base_overcharged_effect", BaseOverchargedPotionEffect::new);

    public static final RegistryObject<SummonTimer> CRAB_TIMER  =
            MOB_EFFECTS.register("crab_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 0xfa87fa));

    public static final RegistryObject<SummonTimer> CORAL_GOLEM_TIMER =
            MOB_EFFECTS.register("coral_golem_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 0xf5ff76));

    public static final RegistryObject<SummonTimer> CORALSSUS_TIMER =
            MOB_EFFECTS.register("coralssus_timer", () -> new SummonTimer(MobEffectCategory.BENEFICIAL, 0xffea66));

    public static final RegistryObject<SummonTimer> REMNANT_TIMER =
            MOB_EFFECTS.register("remnant_timer", RemnantTimerPotionEffect::new);

    public static final RegistryObject<MobEffect> KINGS_WRATH_EFFECT =
            MOB_EFFECTS.register("kings_wrath", KingsWrathPotionEffect::new);

    public static void register(IEventBus eventBus)
    {
        MOB_EFFECTS.register(eventBus);
    }
}
