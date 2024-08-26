package net.acetheeldritchking.cataclysm_spellbooks.entity.render;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.armor.IgnisWizardArmorModel;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import net.minecraft.world.entity.EquipmentSlot;

public class IgnisWizardArmorRenderer extends GeoArmorRenderer<IgnisWizardArmorItem> {
    public IgnisWizardArmorRenderer()
    {
        super(new IgnisWizardArmorModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(IgnisWizardArmorRenderer.this));
    }

    String armorLeggingTorsoLayer = "armorLeggingTorsoLayer";

    // New bone
    private GeoBone armorLeggingTorsoBone()
    {
        return new GeoBone(this.getBodyBone(), armorLeggingTorsoLayer, true, null, true, true);
    }

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        this.setBoneVisible(getHeadBone(),false);
        this.setBoneVisible(getBodyBone(),false);
        this.setBoneVisible(getRightArmBone(),false);
        this.setBoneVisible(getLeftArmBone(),false);
        this.setBoneVisible(getRightLegBone(),false);
        this.setBoneVisible(getLeftLegBone(),false);
        this.setBoneVisible(getRightBootBone(),false);
        this.setBoneVisible(getLeftBootBone(),false);
        this.setBoneVisible(armorLeggingTorsoBone(),false);

        switch (currentSlot) {
            case HEAD -> this.setBoneVisible(getHeadBone(), true);
            case CHEST -> {
                this.setBoneVisible(getBodyBone(), true);
                this.setBoneVisible(getRightArmBone(), true);
                this.setBoneVisible(getLeftArmBone(), true);
            }
            case LEGS -> {
                this.setBoneVisible(getRightLegBone(), true);
                this.setBoneVisible(getLeftLegBone(), true);
                this.setBoneVisible(armorLeggingTorsoBone(), true);
            }
            case FEET -> {
                this.setBoneVisible(getRightBootBone(), true);
                this.setBoneVisible(getLeftBootBone(), true);
            }
        }
    }
}