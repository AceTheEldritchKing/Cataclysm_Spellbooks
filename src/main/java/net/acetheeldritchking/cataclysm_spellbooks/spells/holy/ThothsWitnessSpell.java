package net.acetheeldritchking.cataclysm_spellbooks.spells.holy;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.PhantomAncientRemnant;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedKoboldiator;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoSpellConfig
public class ThothsWitnessSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "thoths_witness");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.remnant_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.HOLY_RESOURCE)
            .setMaxLevel(1)
            // 600 secs
            .setCooldownSeconds(5)
            .build();

    public ThothsWitnessSpell()
    {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 100;
        this.baseManaCost = 450;
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
    public boolean canBeCraftedBy(Player player) {
        return false;
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean allowCrafting() {
        return false;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.TOUCH_GROUND_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SPIT_FINISH_ANIMATION;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Only is around for 45 seconds
        int summonTimer = 20 * 45;

        Vec3 vec = entity.getEyePosition();

        double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 1.5;
        double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 1.5;

        summonPhantomRemnant(randomNearbyX, entity.getY(), randomNearbyZ, entity, level, summonTimer);

        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.REMNANT_TIMER.get(),
                summonTimer, 0, false, false, false);
        entity.addEffect(effect);

        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.HOLY.get().getTargetingColor(), 6), entity.getX(), entity.getY() + 0.8F, entity.getZ(), 1, 0, 0, 0, 0, true);
        ScreenShake_Entity.ScreenShake(level, entity.position(), 6.0F, 0.15F, 20, 20);

        // Just for visuals
        EarthquakeAoe aoe = new EarthquakeAoe(level);
        aoe.moveTo(entity.position());
        aoe.setOwner(entity);
        aoe.setCircular();
        aoe.setRadius(10);
        aoe.setDuration(20);
        aoe.setDamage(0);
        aoe.setSlownessAmplifier(0);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void summonPhantomRemnant(double x, double y, double z, LivingEntity caster, Level level, int summonTimer)
    {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.REMNANT_TIMER.get(),
                summonTimer, 0, false, false, false);

        PhantomAncientRemnant ancientRemnant = new PhantomAncientRemnant(level, caster);

        ancientRemnant.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(ancientRemnant.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);

        ancientRemnant.moveTo(x, y, z);

        ancientRemnant.setSleep(false);

        ancientRemnant.addEffect(effect);

        level.addFreshEntity(ancientRemnant);
    }
}
