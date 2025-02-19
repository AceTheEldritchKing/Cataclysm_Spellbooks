package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.CodexOfMaliceSpellBook;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.CodexOfMaliceSpellBookModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class CodexOfMaliceSpellBookRenderer extends GeoItemRenderer<CodexOfMaliceSpellBook> {
    public CodexOfMaliceSpellBookRenderer() {
        super(new CodexOfMaliceSpellBookModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(CodexOfMaliceSpellBookRenderer.this));
    }
}
