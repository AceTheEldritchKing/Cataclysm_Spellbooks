package net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeModel;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeProjectile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.util.Mth;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;
import software.bernie.geckolib3.util.EModelRenderCycle;

import java.util.Collections;

public class InfernalBladeRenderer extends GeoProjectilesRenderer<InfernalBladeProjectile> {
    public InfernalBladeRenderer(EntityRendererProvider.Context context) {
        super(context, new InfernalBladeModel());
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(InfernalBladeProjectile projectile, float yaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.lerp(partialTick, projectile.yRotO, projectile.getYRot()) + 180));
        poseStack.mulPose(Vector3f.XP.rotationDegrees(Mth.lerp(partialTick, projectile.xRotO, projectile.getXRot())));
        GeoModel model = this.modelProvider.getModel(modelProvider.getModelResource(animatable));
        this.dispatchedMat = poseStack.last().pose().copy();
        setCurrentModelRenderCycle(EModelRenderCycle.INITIAL);
        AnimationEvent<InfernalBladeProjectile> predicate = new AnimationEvent<>(projectile, 0, 0, partialTick, false, Collections.singletonList(new EntityModelData()));
        modelProvider.setLivingAnimations(projectile, getInstanceId(projectile), predicate); // TODO change to setCustomAnimations in 1.20+
        RenderSystem.setShaderTexture(0, getTextureLocation(projectile));
        Color renderColor = getRenderColor(projectile, partialTick, poseStack, bufferSource, null, packedLight);
        RenderType renderType = getRenderType(projectile, partialTick, poseStack, bufferSource, null, packedLight, getTextureLocation(projectile));
        if (!projectile.isInvisibleTo(Minecraft.getInstance().player))
        {
            render(model, projectile, partialTick, renderType, poseStack, bufferSource, null, packedLight,
                    getPackedOverlay(projectile, 0), renderColor.getRed() / 255f, renderColor.getGreen() / 255f,
                    renderColor.getBlue() / 255f, renderColor.getAlpha() / 255f);
        }

        poseStack.popPose();
    }

}
