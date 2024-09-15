package net.acetheeldritchking.cataclysm_spellbooks.entity.render.mobs;

import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawer;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer;

import java.util.List;

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
    public List<GeoRenderLayer<SummonedAbyssalGnawer>> getRenderLayers() {
        return super.getRenderLayers();
    }
}
