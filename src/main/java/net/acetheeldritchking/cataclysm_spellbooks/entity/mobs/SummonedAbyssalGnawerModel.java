package net.acetheeldritchking.cataclysm_spellbooks.entity.mobs;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class SummonedAbyssalGnawerModel extends GeoModel<SummonedAbyssalGnawer> {
    @Override
    public ResourceLocation getModelResource(SummonedAbyssalGnawer object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/abyssal_gnawers.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SummonedAbyssalGnawer object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/abyssal_gnawers/abyssal_gnawers.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SummonedAbyssalGnawer animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/abyssal_gnawers.animation.json");
    }
}
