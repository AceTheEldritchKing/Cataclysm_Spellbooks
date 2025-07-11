package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import io.redspace.ironsspellbooks.particle.BlastwaveParticleOptions;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class MalevolentBattlefieldSpell extends AbstractMaledictusSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "malevolent_battlefield");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.halberd_rings", spellLevel),
                Component.translatable("ui.cataclysm_spellbooks.halberd_amount", Utils.stringTruncation(getHalberdsPerBranch(spellLevel, caster), 0)),
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 1)),
                Component.translatable("ui.cataclysm_spellbooks.maledictus_armory_bonus", Utils.stringTruncation(getBonusDamage(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(60)
            .build();

    public MalevolentBattlefieldSpell()
    {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 3;
        this.castTime = 80;
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
    public AnimationHolder getCastStartAnimation() {
        return CSSpellAnimations.ANIMATION_MALEVOLENT_HAND_SIGN;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SPIT_FINISH_ANIMATION;
    }

    @Override
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(SoundEvents.WARDEN_HEARTBEAT);
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.MALEDICTUS_BATTLE_CRY.get());
    }

    @Override
    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    @Override
    public void onServerPreCast(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        //System.out.println("Pre cast");

        double radius = 15;

        List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        for (LivingEntity targets : entitiesNearby)
        {
            targets.addEffect(new MobEffectInstance(MobEffects.DARKNESS, getCastTime(spellLevel), 1, false, false, false));
        }
        entity.addEffect(new MobEffectInstance(MobEffects.DARKNESS, getCastTime(spellLevel), 1, false, false, false));

        super.onServerPreCast(level, spellLevel, entity, playerMagicData);
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        if (entity.tickCount % 10 == 0)
        {
            // ring 1
            int count = 16;
            float particleRadius = 1.25f;
            for (int i = 0; i < count; i++) {
                double x, z;
                double theta = Math.toRadians((double) 360 / count) * i;
                x = Math.cos(theta) * particleRadius;
                z = Math.sin(theta) * particleRadius;
                MagicManager.spawnParticles(entity.level(), ModParticle.PHANTOM_WING_FLAME.get(), entity.position().x + x, entity.position().y, entity.position().z + z, 1, 0, 0, 0, 0.1, false);
            }

            // ring 2
            int count2 = 16;
            float particleRadius2 = 3.25f;
            for (int i = 0; i < count; i++) {
                double x, z;
                double theta = Math.toRadians((double) 360 / count2) * i;
                x = Math.cos(theta) * particleRadius2;
                z = Math.sin(theta) * particleRadius2;
                MagicManager.spawnParticles(entity.level(), ModParticle.PHANTOM_WING_FLAME.get(), entity.position().x + x, entity.position().y, entity.position().z + z, 1, 0, 0, 0, 0.1, false);
            }

            // ring 3
            int count3 = 16;
            float particleRadius3 = 5.25f;
            for (int i = 0; i < count; i++) {
                double x, z;
                double theta = Math.toRadians((double) 360 / count3) * i;
                x = Math.cos(theta) * particleRadius3;
                z = Math.sin(theta) * particleRadius3;
                MagicManager.spawnParticles(entity.level(), ModParticle.PHANTOM_WING_FLAME.get(), entity.position().x + x, entity.position().y, entity.position().z + z, 1, 0, 0, 0, 0.1, false);
            }
        }

        super.onServerCastTick(level, spellLevel, entity, playerMagicData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Summon halberd field
        Item soulRenderer = ModItems.SOUL_RENDER.get();
        Item annihilator = ModItems.THE_ANNIHILATOR.get();

        if (entity.getMainHandItem().is(soulRenderer) || entity.getMainHandItem().is(annihilator))
        {
            CSUtils.spawnHalberdWindmill(spellLevel * 4, getHalberdsPerBranch(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, getBonusDamage(spellLevel, entity), spellLevel);
            //spawnHalberdField(spellLevel * 4, (int) getSpellPower(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, getBonusDamage(spellLevel, entity), spellLevel);
        }
        else
        {
            CSUtils.spawnHalberdWindmill(spellLevel * 4, getHalberdsPerBranch(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, getDamage(spellLevel, entity), spellLevel);
            //spawnHalberdField(spellLevel * 4, (int) getSpellPower(spellLevel, entity), 1.5, 0.75, 0.2, 1, entity, level, getDamage(spellLevel, entity), spellLevel);
        }
        //System.out.println("After cast");
        EarthquakeAoe aoe = new EarthquakeAoe(level);
        aoe.moveTo(entity.position());
        aoe.setOwner(entity);
        aoe.setCircular();
        aoe.setRadius(6);
        aoe.setDuration(20);
        aoe.setDamage(0);
        aoe.setSlownessAmplifier(0);

        MagicManager.spawnParticles(level, new BlastwaveParticleOptions(SchoolRegistry.ICE.get().getTargetingColor(), 4), entity.getX(), entity.getY() + 0.8F, entity.getZ(), 1, 0, 0, 0, 0, true);
        ScreenShake_Entity.ScreenShake(level, entity.position(), 6.0F, 0.15F, 20, 20);
        level.addFreshEntity(aoe);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker).setIFrames(0).setFreezeTicks(200);
    }

    private int getHalberdsPerBranch(int spellLevel, LivingEntity caster)
    {
        return (int) Mth.clamp(getSpellPower(spellLevel, caster), 1, 5);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) + 7;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster)
    {
        float baseDamage = getDamage(spellLevel, caster);
        int bonusAmount = (int) (3.5 + spellLevel);

        return baseDamage + bonusAmount;
    }
}
