package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSParticleRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class LockOnSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "lock_on");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.timeFromTicks(getEffectDuration(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(30)
            .build();

    public LockOnSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 5;
        this.baseManaCost = 25;
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
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.EMP_ACTIVATED.get());
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, .15f);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetingData)
        {
            var targetEntity = targetingData.getTarget((ServerLevel) level);

            if (targetEntity != null)
            {
                // We want to remove Shutdown if the target already has it
                if (!targetEntity.hasEffect(CSPotionEffectRegistry.SHUTDOWN_EFFECT.get()))
                {
                    spawnParticles(targetEntity);

                    targetEntity.addEffect(new MobEffectInstance(MobEffectRegistry.GUIDING_BOLT.get(), getEffectDuration(spellLevel, entity)));
                    targetEntity.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), getEffectDuration(spellLevel, entity)));
                    targetEntity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.INCAPACITATED_EFFECT.get(), getEffectDuration(spellLevel, entity)));
                } else
                {
                    targetEntity.removeEffect(CSPotionEffectRegistry.SHUTDOWN_EFFECT.get());

                    spawnParticles(targetEntity);

                    targetEntity.addEffect(new MobEffectInstance(MobEffectRegistry.GUIDING_BOLT.get(), getEffectDuration(spellLevel, entity)));
                    targetEntity.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), getEffectDuration(spellLevel, entity)));
                    targetEntity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.INCAPACITATED_EFFECT.get(), getEffectDuration(spellLevel, entity)));
                }
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public int getEffectDuration(int spellLevel, LivingEntity caster)
    {
        int amount = (int) Mth.clamp((getSpellPower(spellLevel, caster) * 20), 20, 5*20);
        //System.out.println("Clamp: " + amount);
        return amount;
    }

    private void spawnParticles(LivingEntity entity)
    {
        ServerLevel level = (ServerLevel) entity.level();
        level.sendParticles(CSParticleRegistry.TARGET_PARTICLE.get(), entity.getX(), entity.getY() + 2.5, entity.getZ(), 1, 0, 0, 0, 0.0);
    }
}
