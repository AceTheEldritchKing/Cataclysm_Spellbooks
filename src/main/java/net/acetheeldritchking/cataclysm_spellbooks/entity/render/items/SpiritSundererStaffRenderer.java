package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import mod.azure.azurelib.renderer.GeoItemRenderer;
import mod.azure.azurelib.renderer.layer.AutoGlowingGeoLayer;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.SpiritSundererStaff;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.SpiritSundererStaffModel;

public class SpiritSundererStaffRenderer extends GeoItemRenderer<SpiritSundererStaff> {
    public SpiritSundererStaffRenderer() {
        super(new SpiritSundererStaffModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(SpiritSundererStaffRenderer.this));
    }
}
