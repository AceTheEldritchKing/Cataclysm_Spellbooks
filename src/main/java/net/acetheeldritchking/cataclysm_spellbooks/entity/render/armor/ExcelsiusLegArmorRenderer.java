package net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import mod.azure.azurelib.util.RenderUtils;
import net.acetheeldritchking.cataclysm_spellbooks.entity.armor.ExcelsiusLegArmorModel;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusLegArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;

public class ExcelsiusLegArmorRenderer extends GeoArmorRenderer<ExcelsiusLegArmorItem> {
    public ExcelsiusLegArmorRenderer()
    {
        super(new ExcelsiusLegArmorModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    String armorLeggingTorsoLayer = "armorLeggingTorsoLayer";

    // New bone, don't use new Bone, use this.model.getBone().orElse(null) for this.
    private GeoBone armorLeggingTorsoBone()
    {
        return this.model.getBone(armorLeggingTorsoLayer).orElse(null);
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
        // First, set the bone so it is not visible.
        this.setBoneVisible(armorLeggingTorsoBone(),false);

        switch (currentSlot) {
            case HEAD -> this.setBoneVisible(getHeadBone(), true);
            case CHEST -> {
                this.setBoneVisible(getBodyBone(), true);
                this.setBoneVisible(getRightArmBone(), true);
                this.setBoneVisible(getLeftArmBone(), true);
            }
            case LEGS -> {
                // Now set the bone to be visable now that the legs slot is filled.
                this.setBoneVisible(armorLeggingTorsoBone(),true);
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

    @Override
    protected void applyBaseTransformations(HumanoidModel<?> baseModel) {
        super.applyBaseTransformations(baseModel);
        if (this.armorLeggingTorsoBone() != null)
        {
            ModelPart modelPart = baseModel.body;
            RenderUtils.matchModelPartRot(modelPart, this.armorLeggingTorsoBone());
            this.armorLeggingTorsoBone().updatePosition(modelPart.x, -modelPart.y, modelPart.z);
        }
    }
}
