package net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor;

import io.redspace.ironsspellbooks.util.MinecraftInstanceHelper;
import mod.azure.azurelib.rewrite.model.AzBakedModel;
import mod.azure.azurelib.rewrite.model.AzBone;
import mod.azure.azurelib.rewrite.render.AzRendererConfig;
import mod.azure.azurelib.rewrite.render.AzRendererPipeline;
import mod.azure.azurelib.rewrite.render.AzRendererPipelineContext;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererConfig;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererPipeline;
import mod.azure.azurelib.rewrite.render.armor.AzArmorRendererPipelineContext;
import mod.azure.azurelib.rewrite.render.armor.bone.AzArmorBoneContext;
import mod.azure.azurelib.rewrite.render.armor.bone.AzArmorBoneProvider;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import mod.azure.azurelib.util.RenderUtils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusPowerArmorItem;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.Objects;

public class ExcelsiusPowerArmorRenderer extends AzArmorRenderer {
    private static final ResourceLocation GEO = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "geo/excelsius_attack.geo.json"
    );

    private static final ResourceLocation TEX = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "textures/models/armor/excelsius_spell_power.png"
    );

    private static final ResourceLocation TEX_CHARGED = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "textures/models/armor/excelsius_spell_power_overcharged.png"
    );

    public static ResourceLocation TEXTURE()
    {
        //ExcelsiusPowerArmorItem.IsOvercharged = true;
        ItemStack itemStack = Objects.requireNonNull(MinecraftInstanceHelper.getPlayer()).getItemBySlot(EquipmentSlot.CHEST);

        if (ExcelsiusPowerArmorItem.IsOvercharged == true)
        {
            return TEX_CHARGED;
        }
        else
        {
            return TEX;
        }
    }

    public ExcelsiusPowerArmorRenderer() {
        super(
                AzArmorRendererConfig.builder(GEO, TEXTURE())
                        //.setAnimatorProvider(CursiumMageElytraAnimator::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .build()
        );
    }

    @Override
    protected AzArmorRendererPipeline createPipeline(AzRendererConfig config) {
        return new AzArmorRendererPipeline(config, this){
            @Override
            protected AzRendererPipelineContext<ItemStack> createContext(AzRendererPipeline<ItemStack> rendererPipeline) {
                return  new AzArmorRendererPipelineContext(rendererPipeline){
                    @Override
                    public AzArmorBoneContext boneContext() {
                        return new AzArmorBoneContext(){
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
                };
            }
        };
    }
}
