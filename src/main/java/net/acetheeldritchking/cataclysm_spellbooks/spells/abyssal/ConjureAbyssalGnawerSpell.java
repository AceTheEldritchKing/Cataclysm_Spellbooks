package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAbyssalGnawer;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class ConjureAbyssalGnawerSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "conjure_abyssal_gnawer");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(80)
            .build();

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.gnawer_count", spellLevel));
    }

    public ConjureAbyssalGnawerSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 25;
        this.baseManaCost = 50;
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
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.EVOKER_PREPARE_SUMMON);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.EVOKER_FANGS_ATTACK);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            //double randomNearbyY = vec.y + entity.getRandom().nextGaussian() * 2;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnGnawersNearby(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer);
        }

        int effectAmplifier = spellLevel - 1;
        if (entity.hasEffect(CSPotionEffectRegistry.ABYSSAL_GNAWER_TIMER.get()))
        {
            effectAmplifier += entity.getEffect(CSPotionEffectRegistry.ABYSSAL_GNAWER_TIMER.get()).getAmplifier() + 1;
        }
        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.ABYSSAL_GNAWER_TIMER.get(), summonTimer, effectAmplifier, false, false, true));

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnGnawersNearby(double x, double y, double z, LivingEntity caster, Level level, int summonTime)
    {
        SummonedAbyssalGnawer abyssalGnawer = new SummonedAbyssalGnawer(level, caster);

        abyssalGnawer.moveTo(x, y, z);

        abyssalGnawer.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(abyssalGnawer.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);
        abyssalGnawer.addEffect(new MobEffectInstance(CSPotionEffectRegistry.ABYSSAL_GNAWER_TIMER.get(),
                summonTime, 0, false, false, false));
        level.addFreshEntity(abyssalGnawer);
    }
}
