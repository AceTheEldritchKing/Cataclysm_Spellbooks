package net.acetheeldritchking.cataclysm_spellbooks.mixins.entities;

import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import net.acetheeldritchking.cataclysm_spellbooks.util.IExtendedCataclysmProjectileInterface;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

    @Inject(method = "onHitEntity", at = @At("HEAD"))
    private void cataclysmSpellbooks$modifyOnEntityHit(EntityHitResult result, CallbackInfo ci)
    {
        Entity shooter = ((Amethyst_Cluster_Projectile_Entity)(Object)this).getOwner();
        Amethyst_Cluster_Projectile_Entity amethystClusterProjectile = ((Amethyst_Cluster_Projectile_Entity)(Object)this);
        Entity entity = result.getEntity();

        if (shooter instanceof LivingEntity)
        {
            if (entity != shooter && !shooter.isAlliedTo(entity))
            {
                if (isFromSpell())
                {
                    //System.out.println("Mixin!");
                }
            }
        }
    }
}
