package net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor;

import mod.azure.azurelib.model.AzBakedModel;
import mod.azure.azurelib.model.AzBone;
import mod.azure.azurelib.render.AzRendererPipeline;
import mod.azure.azurelib.render.armor.AzArmorRendererPipelineContext;
import mod.azure.azurelib.render.armor.bone.AzArmorBoneContext;
import mod.azure.azurelib.render.armor.bone.AzArmorBoneProvider;
import mod.azure.azurelib.util.client.RenderUtils;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class AzArmorLeggingTorsoLayerPipeline extends AzArmorRendererPipelineContext {
    public AzArmorLeggingTorsoLayerPipeline(AzRendererPipeline<UUID, ItemStack> rendererPipeline) {
        super(rendererPipeline);
    }

    @Override
    public AzArmorBoneContext boneContext() {
        return new AzArmorBoneContext() {
            protected AzBone armorLeggingTorsoBone;

            public AzBone getArmorLeggingTorsoBone(AzBakedModel model) {
                return model.getBone("armorLeggingTorsoLayer").orElse(null);
            }

            @Override
            public void grabRelevantBones(AzBakedModel model, AzArmorBoneProvider boneProvider) {
                super.grabRelevantBones(model, boneProvider);
                this.armorLeggingTorsoBone = this.getArmorLeggingTorsoBone(model) ;
            }

            @Override
            public void applyBoneVisibilityBySlot(EquipmentSlot currentSlot) {
                setAllVisible(false);

                // Hide the legging torso bone initially
                this.setBoneVisible(this.armorLeggingTorsoBone, false);

                switch (currentSlot) {
                    case HEAD -> setBoneVisible(this.head, true);
                    case CHEST -> {
                        setBoneVisible(this.body, true);
                        setBoneVisible(this.rightArm, true);
                        setBoneVisible(this.leftArm, true);
                    }
                    case LEGS -> {
                        // Make the legging torso bone visible when the legging armor is equiped
                        this.setBoneVisible(this.armorLeggingTorsoBone, true);
                        setBoneVisible(this.rightLeg, true);
                        setBoneVisible(this.leftLeg, true);
                    }
                    case FEET -> {
                        setBoneVisible(this.rightBoot, true);
                        setBoneVisible(this.leftBoot, true);
                    }
                    case MAINHAND, OFFHAND -> { /* NO-OP */ }
                }
            }

            @Override
            public void applyBaseTransformations(HumanoidModel<?> baseModel)
            {
                super.applyBaseTransformations(baseModel);
                if (this.armorLeggingTorsoBone != null)
                {
                    ModelPart modelPart = baseModel.body;
                    RenderUtils.matchModelPartRot(modelPart, this.armorLeggingTorsoBone);
                    this.armorLeggingTorsoBone.updatePosition(modelPart.x, -modelPart.y, modelPart.z);
                }
            }
        };
    }
}
