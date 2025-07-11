package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.no_man_zone;

import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSDamageTypes;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class NoManZoneAoE extends AoeEntity {
    //public static final DamageSource DAMAGE_SOURCE = new DamageSource(String.format("%s.%s", CataclysmSpellbooks.MOD_ID, "no_man_zone_aoe"));

    private DamageSource damageSource;

    public NoManZoneAoE(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NoManZoneAoE(Level level)
    {
        this(CSEntityRegistry.NO_MAN_ZONE_AOE.get(), level);
    }

    @Override
    public void applyEffect(LivingEntity target) {
        if (damageSource == null) {
            // Temp ISS stuff
            damageSource = new DamageSource(DamageSources.getHolderFromResource(target, CSDamageTypes.NO_MANS_ZONE), this, getOwner());
        }
        DamageSources.ignoreNextKnockback(target);
        target.hurt(damageSource, getDamage());
        target.addEffect(new MobEffectInstance(CSPotionEffectRegistry.DISABLED_EFFECT.get(), 100, 0, true, true, true));
        target.addEffect(new MobEffectInstance(MobEffects.WITHER, 100, 1, true, true, true));
    }

    @Override
    public float getParticleCount() {
        return 0.2f * getRadius();
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.of(ParticleTypes.LARGE_SMOKE);
    }
}
