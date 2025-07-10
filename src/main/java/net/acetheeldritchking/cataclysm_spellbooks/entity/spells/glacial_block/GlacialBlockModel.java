package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class GlacialBlockModel extends GeoModel<GlacialBlockEntity> {
    @Override
    public ResourceLocation getModelResource(GlacialBlockEntity object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/glacial_block.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GlacialBlockEntity object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/glacial_block/glacial_block_snow.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GlacialBlockEntity animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
