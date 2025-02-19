package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IgnisWizardArmorModel extends GeoModel<IgnisWizardArmorItem> {
    @Override
    public ResourceLocation getModelResource(IgnisWizardArmorItem ignisWizardArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/ignis_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IgnisWizardArmorItem ignisWizardArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/ignis_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IgnisWizardArmorItem ignisWizardArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
