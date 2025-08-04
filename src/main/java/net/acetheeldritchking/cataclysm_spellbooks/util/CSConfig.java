package net.acetheeldritchking.cataclysm_spellbooks.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class CSConfig {
    private static final ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SPEC;

    // Config values
    public static ForgeConfigSpec.BooleanValue bossAttributes;
    public static ForgeConfigSpec.BooleanValue shutdownSpellCasting;
    //public static ForgeConfigSpec.BooleanValue ipsPlayerCounterspellImmune;
    public static ForgeConfigSpec.BooleanValue ipsProjectileImmunity;
    public static ForgeConfigSpec.BooleanValue doSpellGriefing;
    public static ForgeConfigSpec.BooleanValue finalRendDamageImmunity;
    public static ForgeConfigSpec.BooleanValue enableMurasamaLifesteal;
    public static ForgeConfigSpec.BooleanValue enableMurasamaManasteal;
    public static ForgeConfigSpec.ConfigValue<Double> murasamaLifestealAmount;
    public static ForgeConfigSpec.ConfigValue<Double> murasamaManastealAmount;

    static
    {
        bossAttributes = configBuilder.worldRestart().define("Should Cataclysm bosses have ISS attributes (default value is True): ", true);
        shutdownSpellCasting = configBuilder.worldRestart().define("Should Shutdown spell prevent target from spell casting (default value is True): ", true);
        //ipsPlayerCounterspellImmune = configBuilder.worldRestart().define("Should Intrusion Prevent System prevent the player themselves from being unable to be counterspelled (default value is False): ", false);
        ipsProjectileImmunity = configBuilder.worldRestart().define("Should Intrusion Prevent System prevent the entities from receiving projectile damage (default is True): ", true);
        doSpellGriefing = configBuilder.worldRestart().define("Should Cataclysm: Spellbooks spells cause griefing (default is True): ", true);
        finalRendDamageImmunity = configBuilder.worldRestart().define("Should Final Rend cancel damage towards the caster when charging up the spell (default is True): ", true);
        enableMurasamaLifesteal = configBuilder.worldRestart().define("Should the Murasama lifesteal ability be enabled (default is True): ", true);
        enableMurasamaManasteal = configBuilder.worldRestart().define("Should the Murasama mana steal ability be enabled (default is True): ", true);
        murasamaLifestealAmount = configBuilder.worldRestart().define("Percentage of health that should be regained for lifesteal based on max health (default is 0.25 aka 25%): ", 0.25D);
        murasamaManastealAmount = configBuilder.worldRestart().define("Percentage of mana that should be regained based on damage dealt for manasteal (default is 0.25 aka 25%): ", 0.25D);

        SPEC = configBuilder.build();
    }

}
