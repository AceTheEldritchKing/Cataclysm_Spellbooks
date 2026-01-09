package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.bullets;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MoltenBulletModel extends GeoModel<MoltenBulletProjectile> {
    @Override
    public ResourceLocation getModelResource(MoltenBulletProjectile object) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "geo/magic_bullet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MoltenBulletProjectile object) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "textures/entity/molten_bullet/molten_bullet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MoltenBulletProjectile animatable) {
        return ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
