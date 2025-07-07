package net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.glacial_block.GlacialBlockModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class GlacialBlockRenderer extends GeoEntityRenderer<GlacialBlockEntity> {
    public GlacialBlockRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GlacialBlockModel());
    }

    @Override
    public void renderEarly(GlacialBlockEntity animatable, PoseStack poseStack, float partialTick, MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float partialTicks) {
        var frozen = animatable.getFirstPassenger();

        if (frozen != null)
        {
            float scale = frozen.getBbWidth() / 0.6F;
            poseStack.scale(scale, scale, scale);
        }

        super.renderEarly(animatable, poseStack, partialTick, bufferSource, buffer, packedLight, packedOverlay, red, green, blue, partialTicks);
    }

    @Override
    public RenderType getRenderType(GlacialBlockEntity animatable, float partialTick, PoseStack poseStack, @Nullable MultiBufferSource bufferSource, @Nullable VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityTranslucentEmissive(texture);
    }
}
