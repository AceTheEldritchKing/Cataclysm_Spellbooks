package net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SurveillanceDroneEntity;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SurveillanceDroneModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class SurveillanceDroneRenderer extends GeoEntityRenderer<SurveillanceDroneEntity> {
    public SurveillanceDroneRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new SurveillanceDroneModel());
        this.shadowRadius = 0.3f;
    }

    @Override
    public ResourceLocation getTextureLocation(SurveillanceDroneEntity animatable) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/entity/surveillance_drone/surveillance_drone.png");
    }

    @Override
    public RenderType getRenderType(SurveillanceDroneEntity animatable, ResourceLocation texture, @Nullable MultiBufferSource bufferSource, float partialTick) {
        return super.getRenderType(animatable, texture, bufferSource, partialTick);
    }
}
