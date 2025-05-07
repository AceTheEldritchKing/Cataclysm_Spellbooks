package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.AbyssalWarlockArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.EngineerMageArmorItem;
import net.minecraft.resources.ResourceLocation;

public class EngineerMageArmorModel extends GeoModel<EngineerMageArmorItem> {
    @Override
    public ResourceLocation getModelResource(EngineerMageArmorItem engineerMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/engineer_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(EngineerMageArmorItem engineerMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/engineer_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(EngineerMageArmorItem engineerMageArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
