package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusResistArmorItem;
import net.minecraft.resources.ResourceLocation;

public class ExcelsiusResistArmorModel extends GeoModel<ExcelsiusResistArmorItem> {
    @Override
    public ResourceLocation getModelResource(ExcelsiusResistArmorItem excelsiusResistArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/excelsius_defense.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ExcelsiusResistArmorItem excelsiusResistArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/excelsius_spell_resist.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ExcelsiusResistArmorItem excelsiusResistArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
