package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.CursiumMageElytraArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CursiumMageElytraArmorModel extends GeoModel<CursiumMageElytraArmorItem> {
    @Override
    public ResourceLocation getModelResource(CursiumMageElytraArmorItem cursiumMageElytraArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/cursium_mage_elytra.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CursiumMageElytraArmorItem cursiumMageElytraArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/cursium_mage_armor_elytra.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CursiumMageElytraArmorItem cursiumMageElytraArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/cursium_mage.animation.json");
    }
}
