package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.WrathfulPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
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
public class ForgoneRageSpell extends AbstractMaledictusSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "forgone_rage");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getSpellPower(spellLevel, caster) * 20, 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageAttackDamage(spellLevel, caster), 0), Component.translatable("attribute.name.generic.attack_damage")),
                Component.translatable("ui.cataclysm_spellbooks.forgone_rage_strikes")
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(120)
            .build();

    public ForgoneRageSpell()
    {
        this.manaCostPerLevel = 25;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 5;
        this.castTime = 0;
        this.baseManaCost = 55;
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
        return Optional.of(ModSounds.MALEDICTUS_BATTLE_CRY.get());
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_WRATH_ROAR;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.WRATHFUL.get(),
                (int) (getSpellPower(spellLevel, entity) * 40),
                spellLevel - 1,
                false,
                false,
                true));

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getPercentageAttackDamage(int spellLevel, LivingEntity caster)
    {
        return spellLevel * WrathfulPotionEffect.ATTACK_DAMAGE_PER_WRATH * 100;
    }
}
