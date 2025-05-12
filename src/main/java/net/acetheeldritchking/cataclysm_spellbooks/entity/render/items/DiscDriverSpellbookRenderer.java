package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.DiscDriverSpellbook;
import net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks.DiscDriverSpellbookModel;

public class DiscDriverSpellbookRenderer extends GeoItemRenderer<DiscDriverSpellbook> {
    public DiscDriverSpellbookRenderer() {
        super(new DiscDriverSpellbookModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
