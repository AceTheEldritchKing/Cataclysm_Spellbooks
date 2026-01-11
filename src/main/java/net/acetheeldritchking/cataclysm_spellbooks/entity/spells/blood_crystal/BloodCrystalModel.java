package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blood_crystal;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class BloodCrystalModel extends GeoModel<BloodCrystalProjectile> {
    @Override
    public ResourceLocation getModelResource(BloodCrystalProjectile object) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "geo/blood_spear.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BloodCrystalProjectile object) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "textures/entity/blood_spear/blood_spear.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BloodCrystalProjectile animatable) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
