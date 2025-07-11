package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.events.SpellHealEvent;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.util.ParticleHelper;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

@AutoSpellConfig
public class RebootSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "reboot");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.healing", Utils.stringTruncation(getHealing(spellLevel, caster), 2)),
                Component.translatable("ui.cataclysm_spellbooks.summon_healing", Utils.stringTruncation((double) getHealing(spellLevel, caster) / 2, 2))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(50)
            .build();

    public RebootSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 1;
        this.castTime = 10;
        this.baseManaCost = 20;
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
        return CastType.INSTANT;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        float radius = spellLevel;
        float distance = 1.65F;
        float healAmount = getHealing(spellLevel, entity);
        float allyHealAmount = (float) getHealing(spellLevel, entity) / 2;

        Vec3 hitLocation = entity.position().add(0, entity.getBbHeight() * 0.3F, 0).add(entity.getForward().multiply(distance, 0.35F, distance));
        var entities = level.getEntities(entity, AABB.ofSize(hitLocation, radius * 2, radius, radius * 2));

        for (Entity target : entities)
        {
            if (entity.isPickable() && entity.distanceToSqr(target) < radius * radius && Utils.hasLineOfSight(level, entity.getEyePosition(), target.getBoundingBox().getCenter(), false))
            {
                if (target instanceof LivingEntity livingEntity && livingEntity.isAlliedTo(entity))
                {
                    MinecraftForge.EVENT_BUS.post(new SpellHealEvent(entity, entity, allyHealAmount, getSchoolType()));

                    livingEntity.heal(allyHealAmount);

                    makeParticleCircle(livingEntity);
                }
            }
        }

        MinecraftForge.EVENT_BUS.post(new SpellHealEvent(entity, entity, healAmount, getSchoolType()));
        entity.heal(healAmount);

        makeParticleCircle(entity);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private int getHealing(int spellLevel, LivingEntity entity)
    {
        return (int) (getSpellPower(spellLevel, entity) * 0.5f);
    }

    private void makeParticleCircle(LivingEntity entity)
    {
        int count = 16;
        float particleRadius = 1.25f;
        for (int i = 0; i < count; i++) {
            double x, z;
            double theta = Math.toRadians((double) 360 / count) * i;
            x = Math.cos(theta) * particleRadius;
            z = Math.sin(theta) * particleRadius;
            MagicManager.spawnParticles(entity.level(), ParticleHelper.ELECTRICITY, entity.position().x + x, entity.position().y, entity.position().z + z, 1, 0, 0, 0, 0.1, false);
        }
    }
}
