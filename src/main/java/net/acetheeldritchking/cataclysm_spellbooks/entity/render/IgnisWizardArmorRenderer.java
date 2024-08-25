package net.acetheeldritchking.cataclysm_spellbooks.entity.render;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.armor.IgnisWizardArmorModel;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.IgnisWizardArmorItem;
import net.minecraft.world.entity.EquipmentSlot;
import org.jetbrains.annotations.Nullable;

public class IgnisWizardArmorRenderer extends GeoArmorRenderer<IgnisWizardArmorItem> {
    public IgnisWizardArmorRenderer()
    {
        super(new IgnisWizardArmorModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(IgnisWizardArmorRenderer.this));
    }

    // Do I look like I know what I'm doing? No <3
    String head = "armorHead";
    String body = "armorBody";
    String rightArm = "armorRightArm";
    String leftArm = "armorLeftArm";
    String rightLeg = "armorRightLeg";
    String leftLeg = "armorLeftLeg";
    String armorLeggingTorsoLayer = "armorLeggingTorsoLayer";
    String righBoot = "armorRightBoot";
    String leftBoot = "armorLeftBoot";

    @Override
    protected void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
        this.setAllVisible(false);
        switch (currentSlot) {
            case HEAD -> this.setBoneVisible(this.getHeadBone(), true);
            case CHEST -> {
                this.setBoneVisible(this.getBodyBone(), true);
                this.setBoneVisible(this.getRightArmBone(), true);
                this.setBoneVisible(this.getLeftArmBone(), true);
            }
            case LEGS -> {
                this.setBoneVisible(this.armorLeggingTorsoBone(), true);
                this.setBoneVisible(this.getRightLegBone(), true);
                this.setBoneVisible(this.getLeftLegBone(), true);
            }
            case FEET -> {
                this.setBoneVisible(this.getRightBootBone(), true);
                this.setBoneVisible(this.getLeftBootBone(), true);
            }
        }
    }

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

    private GeoBone armorLeggingTorsoBone()
    {
        return new GeoBone(this.getBodyBone(), armorLeggingTorsoLayer, true, null, true, true);
    }
}
