package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class InfernalBladeModel extends GeoModel<InfernalBladeProjectile> {

    @Override
    public ResourceLocation getModelResource(InfernalBladeProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/infernal_blade_small.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(InfernalBladeProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/infernal_blade_small/infernal_blade_small.png");
    }

    @Override
    public ResourceLocation getAnimationResource(InfernalBladeProjectile animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/infernal_blade_small.animation.json");
    }
}
