package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Ignited_Berserker_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
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
public class ConjureIgnitedReinforcement extends AbstractIgnisSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "conjure_ignited_reinforcement");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.ignited_count", spellLevel));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(80)
            .build();

    public ConjureIgnitedReinforcement()
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        int summonTimer = 20 * 60 * 10;

        for (int i = 0; i < spellLevel; i++)
        {
            Vec3 vec = entity.getEyePosition();

            double randomNearbyX = vec.x + entity.getRandom().nextGaussian() * 3;
            double randomNearbyZ = vec.z + entity.getRandom().nextGaussian() * 3;

            spawnIgnitedNearby(randomNearbyX, vec.y, randomNearbyZ, entity, level, summonTimer);
        }

        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.IGNITED_TIMER.get());
        entity.addEffect(effect);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnIgnitedNearby(double x, double y, double z, LivingEntity caster, Level level, int summonTimer)
    {
        MobEffectInstance effect = new MobEffectInstance(CSPotionEffectRegistry.IGNITED_TIMER.get(),
                summonTimer, 0, false, false, false);

        Ignited_Revenant_Entity revenantEntity = new Ignited_Revenant_Entity(ModEntities.IGNITED_REVENANT.get(), level);
        Ignited_Berserker_Entity berserkerEntity = new Ignited_Berserker_Entity(ModEntities.IGNITED_BERSERKER.get(), level);

        revenantEntity.moveTo(x, y, z);
        berserkerEntity.moveTo(x, y, z);

        revenantEntity.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(revenantEntity.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);
        berserkerEntity.finalizeSpawn((ServerLevelAccessor) level,
                level.getCurrentDifficultyAt(berserkerEntity.getOnPos()),
                MobSpawnType.MOB_SUMMONED, null, null);

        revenantEntity.addEffect(effect);
        berserkerEntity.addEffect(effect);

        level.addFreshEntity(revenantEntity);
        level.addFreshEntity(berserkerEntity);
    }

    /*
    you may be able to avoid mixins by using LivingChangeTargetEvent and cancel it if the entity tries to target the summoner or their allies
    (aoe abiltities would have to be handled using the damageevents to cancel it if it tries to damage the owner)

    you can also add ai goals (maybe irosn spellbooks already has one for summoned entities) to follow the summoner whereever you create the entity (or in EntityJoinLevelEvent)

    to force them to target other entities same deal - damageevent (when summoner attacks or gets attacked) and check summoned entities
    (either store references yourself or look for utility methods / get nearby entities) and have them target the attacked entity

     */

}
