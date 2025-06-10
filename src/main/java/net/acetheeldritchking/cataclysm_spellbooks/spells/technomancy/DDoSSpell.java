package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

@AutoSpellConfig
public class DDoSSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "ddos");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(200)
            .build();

    public DDoSSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 2;
        this.castTime = 25;
        this.baseManaCost = 150;
    }

    @Override
    public ResourceLocation getSpellResource() {
        return spellId;
    }

    @Override
    public DefaultConfig getDefaultConfig() {
        return defaultConfig;
    }

    @Override
    public CastType getCastType() {
        return CastType.LONG;
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return getCastTime(spellLevel);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_CHARGED_GROUND_SLAM;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = getRange(spellLevel);
        float distance = 2.0f;

        Vec3 ddosLocation = entity.position().add(entity.getForward().multiply(distance, 0.3f, distance));

        ScreenShake_Entity.ScreenShake(level, entity.position(), radius, 0.15F, 0, 20);

        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(CSSchoolRegistry.TECHNOMANCY.get().getTargetingColor(), getRange(spellLevel - 3)), entity.getX(), entity.getY() + 0.8F, entity.getZ(), 1, 0, 0, 0, 0, true);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(CSSchoolRegistry.TECHNOMANCY.get().getTargetingColor(), getRange(spellLevel)), entity.getX(), entity.getY() + 0.5F, entity.getZ(), 1, 0, 0, 0, 0, true);
        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(CSSchoolRegistry.TECHNOMANCY.get().getTargetingColor(), getRange(spellLevel - 3)), entity.getX(), entity.getY() + 0.2F, entity.getZ(), 1, 0, 0, 0, 0, true);

        var entities = level.getEntities(entity,
                AABB.ofSize(ddosLocation, radius * 2, radius, radius * 2));

        for (Entity target: entities)
        {
            if (target instanceof AntiMagicSusceptible antiMagicSusceptible)
            {
                if (antiMagicSusceptible instanceof MagicSummon summon)
                {
                    if (summon.getSummoner() == entity)
                    {
                        antiMagicSusceptible.onAntiMagic(playerMagicData);
                    } else
                    {
                        antiMagicSusceptible.onAntiMagic(playerMagicData);
                    }
                } else
                {
                    antiMagicSusceptible.onAntiMagic(playerMagicData);
                }
            } else if (target instanceof ServerPlayer player)
            {
                Utils.serverSideCancelCast(player, true);
                MagicData.getPlayerMagicData(player).getPlayerRecasts().removeAll(RecastResult.COUNTERSPELL);
            } else if (target instanceof IMagicEntity abstractSpellCastingMob)
            {
                abstractSpellCastingMob.cancelCast();
            }
            if (target instanceof LivingEntity livingEntity)
            {
                for (MobEffect mobEffect : livingEntity.getActiveEffectsMap().keySet().stream().toList())
                {
                    if (mobEffect instanceof MagicMobEffect magicMobEffect)
                    {
                        livingEntity.removeEffect(magicMobEffect);
                    }
                }
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private int getRange(int spellLevel)
    {
        return (int) (spellLevel * 1.2F);
    }
}
