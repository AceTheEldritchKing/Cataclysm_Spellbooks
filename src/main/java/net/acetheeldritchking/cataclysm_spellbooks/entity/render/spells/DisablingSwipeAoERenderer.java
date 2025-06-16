package net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.disabling_swipe.DisablingSwipeAoE;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class DisablingSwipeAoERenderer extends EntityRenderer<DisablingSwipeAoE> {
    private static final ResourceLocation[] TEXTURES = {
            CataclysmSpellbooks.id("textures/entity/disabling_swipe/disabling_swipe_1.png"),
            CataclysmSpellbooks.id("textures/entity/disabling_swipe/disabling_swipe_2.png"),
            CataclysmSpellbooks.id("textures/entity/disabling_swipe/disabling_swipe_3.png"),
            CataclysmSpellbooks.id("textures/entity/disabling_swipe/disabling_swipe_4.png")
    };

    public DisablingSwipeAoERenderer(EntityRendererProvider.Context pContext) {
        super(pContext);
    }

    @Override
    public void render(DisablingSwipeAoE pEntity, float pEntityYaw, float pPartialTick, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        PoseStack.Pose pose = pPoseStack.last();
        pPoseStack.mulPose(Vector3f.YP.rotationDegrees(90 - pEntity.getYRot()));
        pPoseStack.mulPose(Vector3f.XP.rotationDegrees(-pEntity.getXRot()));

        drawSlash(pose, pEntity, pBuffer, pEntity.getBbWidth() * 1.5F, pEntity.isMirrored());

        pPoseStack.popPose();

        super.render(pEntity, pEntityYaw, pPartialTick, pPoseStack, pBuffer, pPackedLight);
    }

    private void drawSlash(PoseStack.Pose pose, DisablingSwipeAoE entity, MultiBufferSource bufferSource, float width, boolean mirrored)
    {
        Matrix4f poseMatrix = pose.pose();
        Matrix3f normalMatrix = pose.normal();

        VertexConsumer consumer = bufferSource.getBuffer(RenderType.entityCutoutNoCull(getTextureLocation(entity)));
        float halfWidth = width * 0.5F;
        float height = entity.getBbHeight() * 0.5F;

        consumer.vertex(poseMatrix, -halfWidth, height, -halfWidth).color(255, 255, 255, 255).uv(0f, mirrored ? 0f : 1f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, halfWidth, height, -halfWidth).color(255, 255, 255, 255).uv(1f, mirrored ? 0f : 1f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, halfWidth, height, halfWidth).color(255, 255, 255, 255).uv(1f, mirrored ? 1f : 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
        consumer.vertex(poseMatrix, -halfWidth, height, halfWidth).color(255, 255, 255, 255).uv(0f, mirrored ? 1f : 0f).overlayCoords(OverlayTexture.NO_OVERLAY).uv2(LightTexture.FULL_BRIGHT).normal(normalMatrix, 0f, 1f, 0f).endVertex();
    }

    @Override
    public ResourceLocation getTextureLocation(DisablingSwipeAoE pEntity) {
        int frame = (pEntity.tickCount / pEntity.ticksPerFrame) % TEXTURES.length;
        return TEXTURES[frame];
    }
}
