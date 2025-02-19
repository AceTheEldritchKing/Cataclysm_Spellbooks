package net.acetheeldritchking.cataclysm_spellbooks.entity.render.items;

import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.SpiritSundererStaff;
import net.acetheeldritchking.cataclysm_spellbooks.items.staffs.SpiritSundererStaffModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SpiritSundererStaffRenderer extends GeoItemRenderer<SpiritSundererStaff> {
    public SpiritSundererStaffRenderer() {
        super(new SpiritSundererStaffModel());
        addRenderLayer(new AutoGlowingGeoLayer<>(SpiritSundererStaffRenderer.this));
    }
}
