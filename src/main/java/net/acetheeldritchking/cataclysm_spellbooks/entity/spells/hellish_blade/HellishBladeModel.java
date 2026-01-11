package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class HellishBladeModel extends GeoModel<HellishBladeProjectile> {
    @Override
    public ResourceLocation getModelResource(HellishBladeProjectile object) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "geo/hellish_blade.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(HellishBladeProjectile object) {
        if (object.getIsSoul())
        {
            return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "textures/entity/hellish_blade/hellish_blade_soul.png");
        } else
        {
            return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "textures/entity/hellish_blade/hellish_blade.png");
        }
    }

    @Override
    public ResourceLocation getAnimationResource(HellishBladeProjectile animatable) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
