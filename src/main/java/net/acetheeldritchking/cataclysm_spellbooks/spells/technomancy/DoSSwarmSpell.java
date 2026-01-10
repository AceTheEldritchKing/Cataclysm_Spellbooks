package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedCounterspellWatcher;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoSpellConfig
public class DoSSwarmSpell extends AbstractHarbingerSummonSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "dos_swarm");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.watcher_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(100)
            .build();

    public DoSSwarmSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 35;
        this.baseManaCost = 90;
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
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_CONSTRUCT_SUMMON;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
    }

    @Override
    protected int onSummoningCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource, MagicData playerMagicData, SummonedEntitiesCastData castData) {
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++) {
            Vec3 vec = caster.getEyePosition();

            double randomNearbyX = vec.x + caster.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + caster.getRandom().nextGaussian() * 3;

            spawnHelper(randomNearbyX, vec.y, randomNearbyZ, caster, level, summonTimer, castData, () -> new SummonedCounterspellWatcher(level, caster));
        }
        return summonTimer;
    }
}
