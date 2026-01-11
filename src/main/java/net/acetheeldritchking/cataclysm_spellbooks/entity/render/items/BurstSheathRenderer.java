package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import mod.azure.azurelib.render.item.AzItemRenderer;
import mod.azure.azurelib.render.item.AzItemRendererConfig;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;

public class BurstSheathRenderer extends AzItemRenderer {
    private static final ResourceLocation GEO = ResourceLocation.fromNamespaceAndPath(
            CataclysmSpellbooks.MOD_ID,
            "geo/burst_sheath.geo.json"
            //"geo/codex_of_malice.geo.json"
    );

    private static final ResourceLocation TEX = ResourceLocation.fromNamespaceAndPath(
            CataclysmSpellbooks.MOD_ID,
            "textures/item/burst_sheath.png"
            //"textures/item/spell_books/codex_of_malice_spell_book_model.png"
    );

    public BurstSheathRenderer() {
        super(
                AzItemRendererConfig.builder(GEO, TEX)
                        .build()
        );
    }
}
