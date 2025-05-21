package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import mod.azure.azurelib.rewrite.render.item.AzItemRenderer;
import mod.azure.azurelib.rewrite.render.item.AzItemRendererConfig;
import mod.azure.azurelib.rewrite.render.layer.AzAutoGlowingLayer;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;

public class CodexOfMaliceSpellBookRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "geo/codex_of_malice.geo.json"
    );

    private static final ResourceLocation TEX = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "textures/item/spell_books/codex_of_malice_spell_book_model.png"
    );

    public CodexOfMaliceSpellBookRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .build()
        );
    }
}
