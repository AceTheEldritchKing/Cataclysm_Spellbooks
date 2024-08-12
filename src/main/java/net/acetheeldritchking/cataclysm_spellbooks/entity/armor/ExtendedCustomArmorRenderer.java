package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.entity.armor.GenericCustomArmorRenderer;
import mod.azure.azurelib.core.animatable.GeoAnimatable;
import mod.azure.azurelib.model.GeoModel;
import mod.azure.azurelib.renderer.GeoArmorRenderer;

public class ExtendedCustomArmorRenderer<T extends GenericCustomArmorRenderer & GeoAnimatable> extends GeoArmorRenderer<T> {
    public ExtendedCustomArmorRenderer(GeoModel<T> model) {
        super(model);
    }
}
