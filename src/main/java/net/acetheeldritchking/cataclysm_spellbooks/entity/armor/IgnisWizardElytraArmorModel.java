package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardElytraArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class IgnisWizardElytraArmorModel extends GeoModel<IgnisWizardElytraArmorItem> {
    @Override
    public ResourceLocation getModelResource(IgnisWizardElytraArmorItem ignisWizardElytraArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/ignis_armor_winged.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(IgnisWizardElytraArmorItem ignisWizardElytraArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/ignis_armor_winged.png");
    }

    @Override
    public ResourceLocation getAnimationResource(IgnisWizardElytraArmorItem ignisWizardElytraArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "animations/entity/ignis_armor_winged.animation.json");
    }
}
