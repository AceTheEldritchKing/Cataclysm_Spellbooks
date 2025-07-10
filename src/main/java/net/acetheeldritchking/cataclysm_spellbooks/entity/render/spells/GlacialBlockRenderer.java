package net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockModel;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockRenderLayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GlacialBlockRenderer extends GeoEntityRenderer<GlacialBlockEntity> {
    public GlacialBlockRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GlacialBlockModel());
        this.addRenderLayer(new GlacialBlockRenderLayer(this));
    }

    @Override
    public void preRender(PoseStack poseStack, GlacialBlockEntity animatable, BakedGeoModel model, MultiBufferSource bufferSource, VertexConsumer buffer, boolean isReRender, float partialTick, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        var frozen = animatable.getFirstPassenger();

        if (frozen != null)
        {
            // I'm reducing the scale since the ice block is already scaled to the player
            float scale = frozen.getBbWidth() / 1.5F;
            poseStack.scale(scale, scale, scale);
        }

        super.preRender(poseStack, animatable, model, bufferSource, buffer, isReRender, partialTick, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public RenderType getRenderType(GlacialBlockEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return RenderType.entityTranslucentEmissive(texture);
    }
}
