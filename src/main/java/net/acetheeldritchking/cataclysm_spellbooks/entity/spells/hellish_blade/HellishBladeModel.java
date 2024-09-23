package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class HellishBladeModel extends AnimatedGeoModel<HellishBladeProjectile> {
    @Override
    public ResourceLocation getModelResource(HellishBladeProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/hellish_blade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HellishBladeProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/hellish_blade/hellish_blade.png");
    }

    @Override
    public ResourceLocation getAnimationResource(HellishBladeProjectile animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
