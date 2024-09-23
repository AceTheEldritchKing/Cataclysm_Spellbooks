package net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells;

import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeModel;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class HellishBladeRenderer extends GeoProjectilesRenderer<HellishBladeProjectile> {
    public HellishBladeRenderer(EntityRendererProvider.Context context) {
        super(context, new HellishBladeModel());
        this.shadowRadius = 0.5f;
    }
}
