package net.acetheeldritchking.cataclysm_spellbooks.mixins.entities;

import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.AmethystPunctureSpell;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(Amethyst_Cluster_Projectile_Entity.class)
public class AmethystClusterMixin {
    @Inject(method = "onHitEntity", at = @At("RETURN"))
    private void cataclysmSpellbooks$modifyOnEntityHit(EntityHitResult result, CallbackInfo ci)
    {
        Entity shooter = ((Amethyst_Cluster_Projectile_Entity)(Object)this).getOwner();
        Amethyst_Cluster_Projectile_Entity amethystClusterProjectile = ((Amethyst_Cluster_Projectile_Entity)(Object)this);
        Entity entity = result.getEntity();
        AmethystPunctureSpell amethystPunctureSpell = new AmethystPunctureSpell();

        if (shooter instanceof LivingEntity livingEntity)
        {
            if (entity != shooter && !shooter.isAlliedTo(entity))
            {
                if (Objects.equals(ClientMagicData.getSyncedSpellData(livingEntity).getCastingSpellId(), amethystPunctureSpell.getSpellId())
                        && ClientMagicData.getSyncedSpellData(livingEntity).isCasting())
                {
                    DamageSources.applyDamage(entity, amethystPunctureSpell.setDamage(1, 10), SpellRegistries.AMETHYST_PUNCTURE.get().getDamageSource(amethystClusterProjectile, shooter));
                    System.out.println("Here?: " + DamageSources.applyDamage(entity, amethystPunctureSpell.setDamage(1, 10), SpellRegistries.AMETHYST_PUNCTURE.get().getDamageSource(amethystClusterProjectile, shooter)));
                }
            }
        }
    }
}
