package net.acetheeldritchking.cataclysm_spellbooks.entity.render;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.armor.IgnisWizardArmorModel;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import org.jetbrains.annotations.Nullable;

public class IgnisWizardArmorRenderer extends GeoArmorRenderer<IgnisWizardArmorItem> {
    public IgnisWizardArmorRenderer()
    {
        super(new IgnisWizardArmorModel());
        //addRenderLayer(new AutoGlowingGeoLayer<>(IgnisWizardArmorRenderer.this));
    }

    // Do I look like I know what I'm doing? No <3
    String head = "armorHead";
    String body = "armorBody";
    String rightArm = "armorRightArm";
    String leftArm = "armorLeftArm";
    String rightLeg = "armorLeftLeg";
    String leftLeg = "armorLeftLeg";
    String armorLeggingTorsoLayer = "armorLeggingTorsoLayer";
    String righBoot = "armorLeftBoot";
    String leftBoot = "armorLeftBoot";

    @Nullable
    @Override
    public GeoBone getHeadBone() {
        return model.getBone(head).orElse(null);
    }

    @Nullable
    @Override
    public GeoBone getBodyBone() {
        return model.getBone(body).orElse(null);
    }

    @Nullable
    @Override
    public GeoBone getLeftArmBone() {
        return model.getBone(leftArm).orElse(null);
    }

    @Nullable
    @Override
    public GeoBone getRightArmBone() {
        return model.getBone(rightArm).orElse(null);
    }

    @Nullable
    @Override
    public GeoBone getLeftLegBone() {
        return model.getBone(leftLeg).orElse(null);
    }

    @Nullable
    @Override
    public GeoBone getRightLegBone() {
        return model.getBone(rightLeg).orElse(null);
    }

    @Nullable
    @Override
    public GeoBone getLeftBootBone() {
        return model.getBone(leftBoot).orElse(null);
    }

    @Nullable
    @Override
    public GeoBone getRightBootBone() {
        return model.getBone(righBoot).orElse(null);
    }
}
