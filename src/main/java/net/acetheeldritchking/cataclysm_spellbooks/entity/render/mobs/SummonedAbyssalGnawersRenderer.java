package net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawerModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class SummonedAbyssalGnawersRenderer extends GeoEntityRenderer<SummonedAbyssalGnawer> {
    public SummonedAbyssalGnawersRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SummonedAbyssalGnawerModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SummonedAbyssalGnawer animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/abyssal_gnawers/abyssal_gnawers.png");
    }

    @Override
    public RenderType getRenderType(SummonedAbyssalGnawer animatable, float partialTick, PoseStack poseStack,
                                    @Nullable MultiBufferSource bufferSource,
                                    @Nullable VertexConsumer buffer, int packedLight,
                                    ResourceLocation texture)
    {
        //poseStack.scale(0.8f, 0.8f, 0.8f);
        return super.getRenderType(animatable, partialTick, poseStack, bufferSource, buffer, packedLight, texture);
    }
}
