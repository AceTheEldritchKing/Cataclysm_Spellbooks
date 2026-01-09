package net.acetheeldritchking.cataclysm_spellbooks.spells.ender;

import com.github.L_Ender.cataclysm.entity.effect.Void_Vortex_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
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
public class GravityStormSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "gravity_storm");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.gravity_storm.lifespan",
                        getVortexLifeSpan(spellLevel, caster)/20),
                Component.translatable("ui.cataclysm_spellbooks.range",
                        getRangeForSpell(spellLevel))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(40)
            .build();

    public GravityStormSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
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
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ModSounds.BLACK_HOLE_LOOP.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.BLACK_HOLE_CLOSING.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double casterEye = entity.getEyeY();
        double casterX = entity.getX();
        double casterZ = entity.getZ();

        HitResult result = Utils.raycastForEntity(level, entity, getRangeForSpell(spellLevel), true);
        Vec3 gravityStormLocation = result.getLocation();

        Level casterLevel = entity.level();

        Void_Vortex_Entity voidVortex = new Void_Vortex_Entity(casterLevel, casterX, casterEye, casterZ, 0, entity, 10);

        voidVortex.setLifespan(getVortexLifeSpan(spellLevel, entity));
        voidVortex.moveTo(gravityStormLocation);

        level.addFreshEntity(voidVortex);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private int getVortexLifeSpan(int spellLevel, LivingEntity caster)
    {
        return (int) (getSpellPower(spellLevel, caster)) * 20;
    }

    private int getRangeForSpell(int spellLevel)
    {
        return 12 * spellLevel;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.ANIMATION_LONG_CAST_FINISH;
    }

    @Override
    public boolean stopSoundOnCancel() {
        return true;
    }
}
