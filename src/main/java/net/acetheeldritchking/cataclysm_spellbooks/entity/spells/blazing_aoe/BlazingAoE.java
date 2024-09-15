package net.acetheeldritchking.cataclysm_spellbooks.entity.spells.blazing_aoe;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.spells.AoeEntity;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class BlazingAoE extends AoeEntity {
    // TODO: Incorrect! Restore these comments
//    public static final DamageSource DAMAGE_SOURCE = new DamageSource(String.format("%s.%s", CataclysmSpellbooks.MOD_ID, "blazing_aoe"));

    public BlazingAoE(EntityType<? extends Projectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public BlazingAoE(Level level)
    {
        this(CSEntityRegistry.BLAZING_AOE_ENTITY.get(), level);
    }

    @Override
    public void applyEffect(LivingEntity target) {
        // TODO: Incorrect! Restore these comments
//        var damageSource = DamageSources.indirectDamageSource(DAMAGE_SOURCE, this, getOwner());
//        DamageSources.ignoreNextKnockback(target);
//        target.hurt(damageSource, getDamage());
//        target.addEffect(new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 100, 0));
    }

    @Override
    public float getParticleCount() {
        return 0.2f * getRadius();
    }

    @Override
    public Optional<ParticleOptions> getParticle() {
        return Optional.of(ModParticle.TRAP_FLAME.get());
    }
}
