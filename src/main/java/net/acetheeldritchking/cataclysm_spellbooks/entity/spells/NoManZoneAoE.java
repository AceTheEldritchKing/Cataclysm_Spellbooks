package net.acetheeldritchking.cataclysm_spellbooks.entity.spells;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
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
    public static final DamageSource DAMAGE_SOURCE = new DamageSource(String.format("%s.%s", CataclysmSpellbooks.MOD_ID, "no_man_zone_aoe"));

    public NoManZoneAoE(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public NoManZoneAoE(Level level)
    {
        this(CSEntityRegistry.NO_MAN_ZONE_AOE.get(), level);
    }

    @Override
    public void applyEffect(LivingEntity target) {
        var damageSource = DamageSources.indirectDamageSource(DAMAGE_SOURCE, this, getOwner());
        DamageSources.ignoreNextKnockback(target);
        target.hurt(damageSource, getDamage());
        target.addEffect(new MobEffectInstance(CSPotionEffectRegistry.DISABLED_EFFECT.get(), 5 * 20, 0, true, true, true));
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
