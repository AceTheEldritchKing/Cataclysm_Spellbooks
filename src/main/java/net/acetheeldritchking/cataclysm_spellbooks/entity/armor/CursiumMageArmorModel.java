package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.CursiumMageArmorItem;
import net.minecraft.resources.ResourceLocation;

public class CursiumMageArmorModel extends GeoModel<CursiumMageArmorItem> {
    @Override
    public ResourceLocation getModelResource(CursiumMageArmorItem cursiumMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/cursium_mage.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CursiumMageArmorItem cursiumMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/cursium_mage_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CursiumMageArmorItem cursiumMageArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
