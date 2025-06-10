package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.parting_shot;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PartingShotModel extends AnimatedGeoModel<PartingShotProjectile> {
    @Override
    public ResourceLocation getModelResource(PartingShotProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/parting_shot.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PartingShotProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/parting_shot/parting_shot.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PartingShotProjectile animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
