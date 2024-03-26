package net.acetheeldritchking.cataclysm_spellbooks.spells;

import com.github.L_Ender.cataclysm.entity.BossMonsters.The_Leviathan.Dimensional_Rift_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class DimensionalRiftSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "dimensional_rift");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.dimensional_rift_spell.lifespan",
                        getRiftLifespan(spellLevel, caster))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(6)
            .setCooldownSeconds(120)
            .build();

    public DimensionalRiftSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 10;
        this.baseManaCost = 200;
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
        return super.getCastStartSound();
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.BLACK_HOLE_OPENING.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double casterEye = entity.getEyeY();
        double casterX = entity.getX();
        double casterZ = entity.getZ();

        HitResult result = Utils.raycastForEntity(level, entity, 32, true);
        Vec3 dimensionRiftLocation = result.getLocation();

        Level casterLevel = entity.level;

        Dimensional_Rift_Entity dimensionalRift =
                new Dimensional_Rift_Entity(casterLevel, casterX, casterEye, casterZ, entity);

        dimensionalRift.setStage(1);
        dimensionalRift.setLifespan(getRiftLifespan(spellLevel, entity));

        dimensionalRift.moveTo(dimensionRiftLocation);
        if (!casterLevel.isClientSide)
        {
            level.addFreshEntity(dimensionalRift);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public int getRiftLifespan(int spellLevel, LivingEntity caster)
    {
        return (int) (getSpellPower(spellLevel, caster) * 100);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SLASH_ANIMATION;
    }

    @Override
    public boolean stopSoundOnCancel() {
        return true;
    }
}
