package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;

public class DiscDriverSpellbookModel extends GeoModel<DiscDriverSpellbook> {
    @Override
    public ResourceLocation getModelResource(DiscDriverSpellbook discDriverSpellbook) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/disc_driver.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(DiscDriverSpellbook discDriverSpellbook) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/item/spell_books/disc_driver_model.png");
    }

    @Override
    public ResourceLocation getAnimationResource(DiscDriverSpellbook discDriverSpellbook) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/disc_driver.animation.json");
    }
}
