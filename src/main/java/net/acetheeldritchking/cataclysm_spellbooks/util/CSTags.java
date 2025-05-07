package net.acetheeldritchking.cataclysm_spellbooks.util;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CSTags {
    public static final TagKey<Item> ABYSSAL_FOCUS = ItemTags.create(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "abyssal_focus"));

    public static final TagKey<Item> TECHNOMANCY_FOCUS = ItemTags.create(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "technomancy_focus"));
}
