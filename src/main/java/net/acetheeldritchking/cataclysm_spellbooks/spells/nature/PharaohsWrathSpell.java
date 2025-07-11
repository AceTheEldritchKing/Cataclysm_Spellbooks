package net.acetheeldritchking.cataclysm_spellbooks.spells.nature;

import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.KingsWrathPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.acetheeldritchking.cataclysm_spellbooks.util.IExtendedCataclysmProjectileInterface;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class PharaohsWrathSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "pharaohs_wrath");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getSpellPower(spellLevel, caster) * 20, 1)),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageAttackDamage(spellLevel, caster), 0), Component.translatable("attribute.name.generic.attack_damage")),
                Component.translatable("attribute.modifier.plus.1", Utils.stringTruncation(getPercentageSpellPower(spellLevel, caster), 0), Component.translatable("attribute.irons_spellbooks.spell_power")),
                Component.translatable("ui.cataclysm_spellbooks.kings_wrath_strikes")
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(120)
            .build();

    public PharaohsWrathSpell()
    {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 5;
        this.castTime = 0;
        this.baseManaCost = 95;
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
    public boolean canBeCraftedBy(Player player) {
        return false;
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean allowCrafting() {
        return false;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.REMNANT_ROAR.get());
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_WRATH_ROAR;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.KINGS_WRATH_EFFECT.get(),
                (int) (getSpellPower(spellLevel, entity) * 40),
                spellLevel - 1,
                false,
                false,
                true));

        for (int i = 0; i < 3; i++)
        {
            double casterX = entity.getX();
            double casterY = entity.getY();
            double casterZ = entity.getZ();

            float angle = (float) (i * Math.PI / 1.5F);
            double stormX = casterX + (Mth.cos(angle) * 4.0F);
            double stormZ = casterZ + (Mth.sin(angle) * 4.0F);

            Sandstorm_Entity sandstorm = new Sandstorm_Entity(level, stormX, casterY, stormZ, (int) (getSpellPower(spellLevel, entity) * 40), angle, entity);

            //((IExtendedCataclysmProjectileInterface)sandstorm).setFromSpell(true);

            level.addFreshEntity(sandstorm);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getPercentageAttackDamage(int spellLevel, LivingEntity caster)
    {
        return spellLevel * KingsWrathPotionEffect.ATTACK_DAMAGE_PER_WRATH * 100;
    }

    private float getPercentageSpellPower(int spellLevel, LivingEntity caster)
    {
        return spellLevel * KingsWrathPotionEffect.SPELL_POWER_PER_WRATH * 100;
    }
}
