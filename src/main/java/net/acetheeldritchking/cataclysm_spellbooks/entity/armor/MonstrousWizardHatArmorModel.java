package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.MonstrousWizardHatArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class MonstrousWizardHatArmorModel extends GeoModel<MonstrousWizardHatArmorItem> {
    @Override
    public ResourceLocation getModelResource(MonstrousWizardHatArmorItem monstrousWizardHatArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/monstrous_wizard_hat.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(MonstrousWizardHatArmorItem monstrousWizardHatArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/monstrous_wizard_hat.png");
    }

    @Override
    public ResourceLocation getAnimationResource(MonstrousWizardHatArmorItem monstrousWizardHatArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
