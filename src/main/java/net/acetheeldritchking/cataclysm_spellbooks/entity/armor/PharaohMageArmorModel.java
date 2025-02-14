package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.PharaohMageArmorItem;
import net.minecraft.resources.ResourceLocation;

public class PharaohMageArmorModel extends GeoModel<PharaohMageArmorItem> {
    @Override
    public ResourceLocation getModelResource(PharaohMageArmorItem pharaohMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/pharaoh_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(PharaohMageArmorItem pharaohMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/pharaoh_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(PharaohMageArmorItem pharaohMageArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
