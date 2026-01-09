package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.SummonManager;
import io.redspace.ironsspellbooks.capabilities.magic.SummonedEntitiesCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedAptrgangr;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedDraugur;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedEliteDraugur;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.SummonedRoyalDraugur;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;

@AutoSpellConfig
public class ConjureThrallsSpell extends AbstractMaledictusSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "conjure_thralls");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.thrall_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(120)
            .build();

    public ConjureThrallsSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 80;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        var castData= new SummonedEntitiesCastData();
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnThrallsNearby(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer, castData);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnThrallsNearby(double x, double y, double z, LivingEntity caster, Level level, int summonTimer, SummonedEntitiesCastData castData)
    {
        boolean isRoyal = Utils.random.nextDouble() < 0.4;
        boolean isElite = Utils.random.nextDouble() < 0.5;
        boolean isAptrgangr = Utils.random.nextDouble() < 0.2;

        boolean isBase = Utils.random.nextDouble() < 0.6;
        boolean isFullArmy = Utils.random.nextDouble() < 0.75;

        SummonedDraugur draugur = new SummonedDraugur(level, caster);
        SummonedRoyalDraugur royalDraugur = new SummonedRoyalDraugur(level, caster);
        SummonedEliteDraugur eliteDraugur = new SummonedEliteDraugur(level, caster);
        SummonedAptrgangr aptrgangr = new SummonedAptrgangr(level, caster);

        Monster isBaseDraugur = isRoyal ? draugur : royalDraugur;
        Monster isEliteDraugur = isElite ? eliteDraugur : royalDraugur;
        Monster isAptrgangrDraugur = isAptrgangr ? aptrgangr : isEliteDraugur;

        Monster baseArmy = isBase ? isBaseDraugur : isEliteDraugur;
        Monster draugurArmry = isFullArmy ? baseArmy : isAptrgangrDraugur;

        draugurArmry.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(draugurArmry.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);

        draugurArmry.moveTo(x, y, z);

        level.addFreshEntity(draugurArmry);

        SummonManager.initSummon(caster, draugurArmry, summonTimer, castData);
    }
}
