package net.acetheeldritchking.cataclysm_spellbooks.util;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CSTags {
    /***
     * Items
     */
    // Abyssal School Focus
    public static final TagKey<Item> ABYSSAL_FOCUS = ItemTags.create(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "abyssal_focus"));

    // Technomancy School Focus
    public static final TagKey<Item> TECHNOMANCY_FOCUS = ItemTags.create(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "technomancy_focus"));

    // Armor Items for Idle
    public static final TagKey<Item> ARMORS_FOR_IDLE = ItemTags.create(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "armors_for_idle"));

    // Armor Items for Flight
    public static final TagKey<Item> ARMORS_FOR_FLIGHT = ItemTags.create(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "armors_for_flight"));
}
