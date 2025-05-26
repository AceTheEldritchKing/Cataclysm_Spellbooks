package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.entity.IMagicEntity;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.entity.mobs.AntiMagicSusceptible;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = spellLevel + 0.5F;
        float distance = 2.0f;

        Vec3 ddosLocation = entity.position().add(entity.getForward().multiply(distance, 0.3f, distance));

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
}
