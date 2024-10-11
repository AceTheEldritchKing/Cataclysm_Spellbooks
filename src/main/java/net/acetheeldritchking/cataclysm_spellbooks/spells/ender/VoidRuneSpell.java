package net.acetheeldritchking.cataclysm_spellbooks.spells.ender;

import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class VoidRuneSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "void_rune");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.void_rune",
                        Utils.stringTruncation(getSpellPower(spellLevel, caster), 1)),
                Component.translatable("ui.irons_spellbooks.damage",
                        Utils.stringTruncation(getDamage(spellLevel), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(20)
            .build();

    public VoidRuneSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 3;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
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
    public Optional<SoundEvent> getCastStartSound() {
        return super.getCastStartSound();
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.VOID_RUNE_RISING.get());
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 50, .15f);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetingData)
        {
            var targetEntity = targetingData.getTarget((ServerLevel) level);
            if (targetEntity != null)
            {
                double targetX = targetEntity.getX();
                double targetY = targetEntity.getY();
                double targetZ = targetEntity.getZ();

                BlockPos blockPos = new BlockPos(targetX, targetY, targetZ);

                for (int i = 0; i < getDuration(spellLevel, entity); i++)
                {
                    //System.out.println("In main spell loop?");
                    double d0 = targetY;
                    double d1 = targetY + 1.0D;
                    float f = (float) Mth.atan2(targetZ, targetX);

                    float f1 = (float) (f + (Math.PI * 0.4f));
                    int delay = i / 3;

                    Void_Rune_Entity voidRune = new Void_Rune_Entity(level, targetX + Mth.cos(f1) * 1.5D, blockPos.getY() + d0, targetZ + Mth.sin(f1) * 1.5D, (float) d0, delay, getDamage(spellLevel), entity);
                    level.addFreshEntity(voidRune);
                    targetEntity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.SUMMON_VOID_RUNE.get(),
                            getEffectDuration(spellLevel, entity), spellLevel - 1, false, false, false));
                }
            }
        }
        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private int getDuration(int spellLevel, LivingEntity caster)
    {
        return getLevelFor(spellLevel, caster);
    }

    private int getEffectDuration(int spellPower, LivingEntity caster)
    {
        int duration = ((int) (getSpellPower(spellPower, caster) * 100))/2;
        int maxTicksForDuration = 10 * 20;
        int maxDuration = Math.min(duration * 20, maxTicksForDuration);

        return maxDuration;
    }

    private float getDamage(int spellLevel)
    {
        return spellLevel;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ANIMATION_INSTANT_CAST;
    }
}
