package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.projectile.Flare_Bomb_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Timer;
import java.util.TimerTask;

@AutoSpellConfig
public class CometShowerSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "comet_shower");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(30)
            .build();

    public CometShowerSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 3;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 40;
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
        Timer flareTimer = new Timer();
        int flareTimerDelay = 350;

        for (int i = 0; i < spellLevel; i++)
        {
            int delay = i * flareTimerDelay;

            flareTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Flare_Bomb_Entity flareBomb = new Flare_Bomb_Entity(ModEntities.FLARE_BOMB.get(), level, entity);

                        Vec3 viewVec = entity.getViewVector(1.0F);

                        flareBomb.setPosRaw(entity.getX() + 0.5, CSUtils.getEyeHeight(entity), entity.getZ() + 0.5);
                        flareBomb.shoot(viewVec.x, viewVec.y, viewVec.z, 1.0F, 1.0F);

                        level.addFreshEntity(flareBomb);
                    }
                }, delay);

            flareTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        flareTimer.cancel();
                    }
                }, (long) (spellLevel) * flareTimerDelay);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
}
