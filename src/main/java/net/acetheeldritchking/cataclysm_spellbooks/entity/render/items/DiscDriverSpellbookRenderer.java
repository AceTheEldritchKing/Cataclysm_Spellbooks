package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;

public class DiscDriverSpellbookRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "geo/disc_driver.geo.json"
    );

    private static final ResourceLocation TEX = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "textures/item/spell_books/disc_driver_model.png"
    );

    public DiscDriverSpellbookRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .build()
        );
    }
}
