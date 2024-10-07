package net.acetheeldritchking.cataclysm_spellbooks.mixins.entities.projectiles;

import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import net.acetheeldritchking.cataclysm_spellbooks.util.IExtendedCataclysmProjectileInterface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Amethyst_Cluster_Projectile_Entity.class)
public class AmethystClusterMixin implements IExtendedCataclysmProjectileInterface {
    @Override
    public boolean isFromSpell() {
        return true;
    }

    @Override
    public void setFromSpell(boolean bool) {
        if (bool)
        {
            isFromSpell();
        }
    }
}
