package net.acetheeldritchking.cataclysm_spellbooks.items.staffs;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;

public class GauntletOfPowerModel extends GeoModel<GauntletOfPowerItem> {
    @Override
    public ResourceLocation getModelResource(GauntletOfPowerItem gauntletOfPowerItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/gauntlet_of_power.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GauntletOfPowerItem gauntletOfPowerItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/item/gauntlet_of_power.png");
    }

    @Override
    public ResourceLocation getAnimationResource(GauntletOfPowerItem gauntletOfPowerItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
