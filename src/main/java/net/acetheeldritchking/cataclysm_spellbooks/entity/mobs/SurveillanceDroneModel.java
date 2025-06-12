package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SurveillanceDroneModel extends AnimatedGeoModel<SurveillanceDroneEntity> {
    @Override
    public ResourceLocation getModelResource(SurveillanceDroneEntity object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/surveillance_drone.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SurveillanceDroneEntity object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/surveillance_drone/surveillance_drone.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SurveillanceDroneEntity animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/surveillance_drone.animation.json");
    }
}
