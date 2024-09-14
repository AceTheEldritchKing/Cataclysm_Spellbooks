package net.acetheeldritchking.cataclysm_spellbooks.entity.render.spells;

import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeModel;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeProjectile;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class InfernalBladeRenderer extends GeoProjectilesRenderer<InfernalBladeProjectile> {
    public InfernalBladeRenderer(EntityRendererProvider.Context context) {
        super(context, new InfernalBladeModel());
        this.shadowRadius = 0.5f;
    }
}
