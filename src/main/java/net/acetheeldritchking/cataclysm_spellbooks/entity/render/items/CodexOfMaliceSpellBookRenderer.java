package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.CodexOfMaliceSpellBook;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.CodexOfMaliceSpellBookModel;

public class CodexOfMaliceSpellBookRenderer extends GeoItemRenderer<CodexOfMaliceSpellBook> {
    public CodexOfMaliceSpellBookRenderer() {
        super(new CodexOfMaliceSpellBookModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(CodexOfMaliceSpellBookRenderer.this));
    }
}
