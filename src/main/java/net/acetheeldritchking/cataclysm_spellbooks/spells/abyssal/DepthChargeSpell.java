package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Mine_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;

@AutoSpellConfig
public class DepthChargeSpell extends AbstractAbyssalSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "depth_charge");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(40)
            .build();

    public DepthChargeSpell()
    {
        this.manaCostPerLevel = 50;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 10;
        this.castTime = 10;
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
        return CastType.INSTANT;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.LEVIATHAN_STUN_ROAR.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double casterX = entity.getX();
        double casterY = entity.getY();
        double casterZ = entity.getZ();

        double radiusX = casterX + spellLevel;
        double radiusZ = casterZ + spellLevel;

        float f = (float) Mth.atan2(radiusZ - casterZ, radiusX - casterX);
        for (int l = 0; l < 35; ++l)
        {
            int j = (int) (2.0F * l);
            for (int i = 0; i < amountForMines(spellLevel, entity); i++)
            {
                double nearbyRandomX = casterX + entity.getRandom().nextGaussian() * randomNearby1(spellLevel);
                double nearbyRandomY = casterY + entity.getRandom().nextGaussian() * randomNearby2(spellLevel);
                double nearbyRandomZ = casterZ + entity.getRandom().nextGaussian() * randomNearby1(spellLevel);

                spawnMines(nearbyRandomX, nearbyRandomY, nearbyRandomZ, f, j, entity);
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void spawnMines(double x, double y, double z, float rotation, int delay, LivingEntity caster)
    {
        Level level = caster.level();

        Abyss_Mine_Entity abyssMine = new Abyss_Mine_Entity(level, x, y, z, rotation, delay, caster);

        if (caster != null)
        {
            if (abyssMine.level().noCollision(abyssMine))
            {
                caster.level().addFreshEntity(abyssMine);
            }
        }
    }

    private double randomNearby1(int spellLevel)
    {
        return 4.0D * spellLevel;
    }

    private double randomNearby2(int spellLevel)
    {
        return 3.0D * spellLevel;
    }

    // We need a hard cap on mines...
    private int amountForMines(int spellLevel, LivingEntity caster)
    {
        return (int) Mth.clamp(getSpellPower(spellLevel, caster), 0, 5);
    }
}
