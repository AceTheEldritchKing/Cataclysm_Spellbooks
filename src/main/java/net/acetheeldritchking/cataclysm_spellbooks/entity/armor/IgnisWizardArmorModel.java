package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class IgnisWizardArmorModel extends AnimatedGeoModel<IgnisWizardArmorItem> {
    @Override
    public ResourceLocation getModelResource(IgnisWizardArmorItem object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/ignis_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IgnisWizardArmorItem object) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/ignis_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IgnisWizardArmorItem animatable) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
