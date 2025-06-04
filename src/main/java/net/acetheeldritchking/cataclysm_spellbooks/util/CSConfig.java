package net.acetheeldritchking.cataclysm_spellbooks.util;

import net.minecraftforge.common.ForgeConfigSpec;

public class CSConfig {
    private static final ForgeConfigSpec.Builder configBuilder = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec SPEC;

    // Config values
    public static ForgeConfigSpec.BooleanValue bossAttributes;
    public static ForgeConfigSpec.BooleanValue shutdownSpellCasting;
    public static ForgeConfigSpec.BooleanValue ipsPlayerCounterspellImmune;
    public static ForgeConfigSpec.BooleanValue ipsProjectileImmunity;

    static
    {
        bossAttributes = configBuilder.worldRestart().define("Should Cataclysm bosses have ISS attributes (default value is True): ", true);
        shutdownSpellCasting = configBuilder.worldRestart().define("Should Shutdown spell prevent target from spell casting (default value is True): ", true);
        //ipsPlayerCounterspellImmune = configBuilder.worldRestart().define("Should Intrusion Prevent System prevent the player themselves from being unable to be counterspelled (default value is False): ", false);
        ipsProjectileImmunity = configBuilder.worldRestart().define("Should Intrusion Prevent System prevent the entities from receiving projectile damage (default is True): ", true);

        SPEC = configBuilder.build();
    }

}
