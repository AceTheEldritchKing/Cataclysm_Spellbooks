package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;

public class GlacialBlockRenderLayer extends GeoLayerRenderer<GlacialBlockEntity> {
    private static final ResourceLocation LAYER = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/glacial_block/glacial_block.png");
    private static final ResourceLocation MODEL = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/glacial_block.geo.json");

    public GlacialBlockRenderLayer(GeoEntityRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, GlacialBlockEntity entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType translucent = RenderType.entityTranslucentEmissive(LAYER, true);
        var model = getEntityModel().getModel(MODEL);
        VertexConsumer VertexConsumer = bufferIn.getBuffer(translucent);
        this.getRenderer().render(
                model, entityLivingBaseIn, partialTicks, translucent, matrixStackIn, bufferIn, VertexConsumer, 15728640, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.5f
        );
    }
}
