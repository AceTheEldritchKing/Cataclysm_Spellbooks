package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.GauntletOfPowerItem;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.GauntletOfPowerModel;

public class GauntletOfPowerRenderer extends GeoItemRenderer<GauntletOfPowerItem> {
    public GauntletOfPowerRenderer() {
        super(new GauntletOfPowerModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
