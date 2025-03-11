package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusLegArmorItem;
import net.minecraft.resources.ResourceLocation;

public class ExcelsiusLegArmorModel extends GeoModel<ExcelsiusLegArmorItem> {
    @Override
    public ResourceLocation getModelResource(ExcelsiusLegArmorItem excelsiusLegArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/excelsius_legs.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ExcelsiusLegArmorItem excelsiusLegArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/excelsius_legs.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ExcelsiusLegArmorItem excelsiusLegArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
