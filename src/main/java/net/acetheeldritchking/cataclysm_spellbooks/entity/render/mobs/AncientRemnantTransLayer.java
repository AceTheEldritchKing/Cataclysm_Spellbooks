package net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.client.model.entity.Ancient_Remnant_Rework_Model;
import com.github.L_Ender.cataclysm.client.render.entity.Ancient_Remnant_Rework_Renderer;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.IABossMonsters.Ancient_Remnant.Ancient_Remnant_Entity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class AncientRemnantTransLayer extends RenderLayer<Ancient_Remnant_Entity, Ancient_Remnant_Rework_Model> {
    private static final ResourceLocation LAYER_TEXTURES = new ResourceLocation(Cataclysm.MODID, "textures/entity/ancient_remnant/ancient_remnant.png");

    public AncientRemnantTransLayer(Ancient_Remnant_Rework_Renderer renderIn) {
        super(renderIn);
    }

    public void render(PoseStack matrixStackIn, MultiBufferSource bufferIn, int packedLightIn, Ancient_Remnant_Entity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        RenderType translucent = RenderType.entityTranslucentEmissive(LAYER_TEXTURES, false);
        VertexConsumer VertexConsumer = bufferIn.getBuffer(translucent);
        (this.getParentModel()).renderToBuffer(matrixStackIn, VertexConsumer, packedLightIn, OverlayTexture.NO_OVERLAY, 0.5F, 0.7F, 0.9F, 0.4F);
    }
}
