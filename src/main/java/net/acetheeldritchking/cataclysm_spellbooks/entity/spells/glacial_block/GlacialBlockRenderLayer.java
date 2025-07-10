package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

public class GlacialBlockRenderLayer extends GeoRenderLayer<GlacialBlockEntity> {
    private static final ResourceLocation LAYER = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/glacial_block/glacial_block.png");
    private static final ResourceLocation MODEL = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/glacial_block.geo.json");

    public GlacialBlockRenderLayer(GeoEntityRenderer renderer) {
        super(renderer);
    }

    @Override
    public void render(PoseStack poseStack, GlacialBlockEntity animatable, BakedGeoModel bakedModel, RenderType renderType, MultiBufferSource bufferSource, VertexConsumer buffer, float partialTick, int packedLight, int packedOverlay) {
        RenderType translucent = RenderType.entityTranslucentEmissive(LAYER, true);
        BakedGeoModel model = this.getGeoModel().getBakedModel(MODEL);
        VertexConsumer VertexConsumer = bufferSource.getBuffer(translucent);
        this.getRenderer().actuallyRender(
                poseStack, animatable, model, renderType, bufferSource, VertexConsumer, true, partialTick, 15728880, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 0.5f
        );
    }
}
