package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.flash_bang;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class FlashBangModel extends GeoModel<FlashBangProjectileEntity> {
    @Override
    public ResourceLocation getModelResource(FlashBangProjectileEntity object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/flash_bang.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FlashBangProjectileEntity object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/flash_bang/flash_bang.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FlashBangProjectileEntity animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
