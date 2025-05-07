package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusPowerArmorItem;
import net.minecraft.resources.ResourceLocation;

public class ExcelsiusPowerArmorModel extends GeoModel<ExcelsiusPowerArmorItem> {
    @Override
    public ResourceLocation getModelResource(ExcelsiusPowerArmorItem excelsiusPowerArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/excelsius_attack.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(ExcelsiusPowerArmorItem excelsiusPowerArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/excelsius_spell_power.png");
    }

    @Override
    public ResourceLocation getAnimationResource(ExcelsiusPowerArmorItem excelsiusPowerArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
