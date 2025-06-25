package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedCoralssus;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoSpellConfig
public class ConjureCoralssusSpell extends AbstractAbyssalSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "conjure_coralssus");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.coralssus_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(150)
            .build();

    public ConjureCoralssusSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 30;
        this.baseManaCost = 100;
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
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnCoralssus(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer);
        }

        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.CORALSSUS_TIMER.get(),
                summonTimer, 0, false, false, false);
        entity.addEffect(effect);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnCoralssus(double x, double y, double z, LivingEntity caster, Level level, int summonTimer)
    {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.CORALSSUS_TIMER.get(),
                summonTimer, 0, false, false, false);

        SummonedCoralssus coralssus = new SummonedCoralssus(level, caster);

        coralssus.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(coralssus.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);

        coralssus.moveTo(x, y, z);

        coralssus.addEffect(effect);

        level.addFreshEntity(coralssus);
    }
}
