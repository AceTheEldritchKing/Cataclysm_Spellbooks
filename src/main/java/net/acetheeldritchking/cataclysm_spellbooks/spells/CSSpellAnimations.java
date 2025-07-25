package net.acetheeldritchking.cataclysm_spellbooks.spells;

import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;

public class CSSpellAnimations {
    public static ResourceLocation ANIMATION_RESOURCE = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animation");

    public static final AnimationHolder ANIMATION_MALEVOLENT_HAND_SIGN = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":malevolent_hand_sign", true);

    public static final AnimationHolder ANIMATION_CHARGE_GUN = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":charge_gun", true);

    public static final AnimationHolder ANIMATION_CHARGE_GUN_RELEASE = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":charged_gun_release", true);

    public static final AnimationHolder ANIMATION_CHARGE_GUN_FULL = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":full_gun_shoot", true);

    public static final AnimationHolder ANIMATION_SIMPLE_SHOOT = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":simple_shoot", true);

    public static final AnimationHolder ANIMATION_CHARGED_GROUND_SLAM = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":charged_ground_slam", true);

    public static final AnimationHolder ANIMATION_CONSTRUCT_SUMMON = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":construct_summon", true);

    public static final AnimationHolder ANIMATION_WRATH_ROAR = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":wrathful_roar", true);

    public static final AnimationHolder DEFENSIVE_SWORD_STANCE_START = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":sword_stance_start", true);

    public static final AnimationHolder DEFENSIVE_SWORD_STANCE_FINISH = new AnimationHolder(CataclysmSpellbooks.MOD_ID + ":sword_stance_finish", true);
}
