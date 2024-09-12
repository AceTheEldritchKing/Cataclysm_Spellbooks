package net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor;

import mod.azure.azurelib.cache.object.GeoBone;
import mod.azure.azurelib.renderer.GeoArmorRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.armor.AbyssalWarlockArmorModel;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.AbyssalWarlockArmorItem;
import net.minecraft.world.entity.EquipmentSlot;

public class AbyssalWarlockArmorRenderer extends GeoArmorRenderer<AbyssalWarlockArmorItem> {
    public AbyssalWarlockArmorRenderer()
    {
        super(new AbyssalWarlockArmorModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(AbyssalWarlockArmorRenderer.this));
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
}
