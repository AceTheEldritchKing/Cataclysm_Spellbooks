package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.extended.ExtendedDeathLaserBeamEntity;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSEntityRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class AtomicLaserSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "atomic_laser");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(300)
            .build();

    public AtomicLaserSpell()
    {
        this.manaCostPerLevel = 100;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 15;
        this.castTime = 60;
        this.baseManaCost = 600;
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
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SPIT_FINISH_ANIMATION;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ModSounds.HARBINGER_DEATHLASER_PREPARE.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.DEATH_LASER.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double casterX = entity.getX();
        double casterY = CSUtils.getEyeHeight(entity);
        double casterZ = entity.getZ();

        float dir = 90F;
        float casterXRot = (float) -(entity.getXRot() * Math.PI/180F);
        float casterYHeadRot = (float) ((entity.getYHeadRot() + dir) * Math.PI/180D);

        ScreenShake_Entity.ScreenShake(level, entity.position(), 15, 0.05F, 20, 20);
        if (!level.isClientSide)
        {
            // Prevent player from moving
            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.INCAPACITATED_EFFECT.get(),
                    80, 2, true, true, true));
            entity.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(),
                    80, 2, true, true, true));

            // Firing mah laser
            // This laser doesn't destroy blocks, honestly I might keep it that way
            ExtendedDeathLaserBeamEntity atomic_laser = new ExtendedDeathLaserBeamEntity(
                    CSEntityRegistry.EXTENDED_DEATH_LASER_BEAM.get(),
                    level, entity, casterX, casterY, casterZ,
                    casterYHeadRot, casterXRot,
                    60,
                    getDamage(spellLevel, entity),
                    getHPDamage(spellLevel));

            level.addFreshEntity(atomic_laser);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return (float) (1.5 * getSpellPower(spellLevel, caster));
    }

    private float getHPDamage(int spellLevel)
    {
        return (float) (spellLevel * 10) / 100;
    }
}
