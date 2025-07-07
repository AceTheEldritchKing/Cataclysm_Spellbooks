package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.bullets;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FrozenBulletModel extends AnimatedGeoModel<FrozenBulletProjectile> {
    @Override
    public ResourceLocation getModelResource(FrozenBulletProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/magic_bullet.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(FrozenBulletProjectile object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/frozen_bullet/frozen_bullet.png");
    }

    @Override
    public ResourceLocation getAnimationResource(FrozenBulletProjectile animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/hellish_blade.animation.json");
    }
}
