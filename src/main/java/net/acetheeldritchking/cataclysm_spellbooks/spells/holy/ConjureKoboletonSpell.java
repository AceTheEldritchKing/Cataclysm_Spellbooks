package net.acetheeldritchking.cataclysm_spellbooks.spells.holy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedKoboleton;
import net.acetheeldritchking.cataclysm_spellbooks.spells.AbstractSummonSpell;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoSpellConfig
public class ConjureKoboletonSpell extends AbstractSummonSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "summon_koboleton");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.koboleton_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.HOLY_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(80)
            .build();

    public ConjureKoboletonSpell() {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 30;
        this.baseManaCost = 75;
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
    protected int onSummoningCast(Level level, int spellLevel, LivingEntity caster, CastSource castSource, MagicData playerMagicData, SummonedEntitiesCastData castData) {
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++) {
            Vec3 vec = caster.getEyePosition();

            double randomNearbyX = vec.x + caster.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + caster.getRandom().nextGaussian() * 3;

            spawnHelper(randomNearbyX, vec.y, randomNearbyZ, caster, level, summonTimer, castData, () -> new SummonedKoboleton(level, caster));
        }
        return summonTimer;
    }
}
