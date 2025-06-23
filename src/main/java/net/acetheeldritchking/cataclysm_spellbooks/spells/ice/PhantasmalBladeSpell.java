package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Axe_Blade_Entity;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.entity.spells.EarthquakeAoe;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class PhantasmalBladeSpell extends AbstractMaledictusSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "phantasmal_blade");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.phantom_blades", spellLevel),
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 1)),
                Component.translatable("ui.cataclysm_spellbooks.maledictus_armory_bonus", Utils.stringTruncation(getBonusDamage(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(12)
            .setCooldownSeconds(25)
            .build();

    public PhantasmalBladeSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 16;
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
        return CastType.LONG;
    }

    @Override
    public boolean canBeInterrupted(@Nullable Player player) {
        return false;
    }

    @Override
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return getCastTime(spellLevel);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.OVERHEAD_MELEE_SWING_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.MALEDICTUS_BATTLE_CRY.get());
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return 3;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId()))
        {
            playerMagicData.getPlayerRecasts().addRecast
                    (new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity),
                            100, castSource, null), playerMagicData);
        }

        // Mal armory bonus
        Item soulRenderer = ModItems.SOUL_RENDER.get();
        Item annihilator = ModItems.THE_ANNIHILATOR.get();

        if (entity.getMainHandItem().is(soulRenderer) || entity.getMainHandItem().is(annihilator))
        {
            summonAxeBlades(level, entity, spellLevel, getBonusDamage(spellLevel, entity));
        }
        else
        {
            summonAxeBlades(level, entity, spellLevel, getDamage(spellLevel, entity));
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void summonAxeBlades(Level level, LivingEntity caster, int spellLevel, float damage)
    {
        double theta = caster.yBodyRot * (Math.PI / 180);

        theta += Math.PI * 2;

        double vecX = Math.cos(theta);
        double vecZ = Math.sin(theta);
        float angleStep = 30.0F;

        for (int i = 0; i < spellLevel; i++)
        {
            float angle = caster.yBodyRot + (i - ((float) spellLevel / 2)) * angleStep;

            float rad = (float) Math.toRadians(angle);
            double dX = -Math.sin(rad);
            double dZ = Math.cos(rad);

            Axe_Blade_Entity axeBladeEntity = new Axe_Blade_Entity(caster, dX, 0, dZ, level, damage, angle);
            //axeBladeEntity.setDamage(damage);

            double spawnX = caster.getX();
            double spawnY = caster.getY(0.15D);
            double spawnZ = caster.getZ();

            axeBladeEntity.setPos(spawnX, spawnY, spawnZ);

            //System.out.println("Damage: " + axeBladeEntity.getDamage());

            level.addFreshEntity(axeBladeEntity);
        }

        // Just for visuals
        EarthquakeAoe aoe = new EarthquakeAoe(level);
        aoe.moveTo(caster.position());
        aoe.setOwner(caster);
        aoe.setCircular();
        aoe.setRadius(6);
        aoe.setDuration(20);
        aoe.setDamage(0);
        aoe.setSlownessAmplifier(0);

        level.addFreshEntity(aoe);

        ScreenShake_Entity.ScreenShake(level, caster.position(), 6.0F, 0.05F, 20, 20);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 0.9F;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster)
    {
        float baseDamage = getDamage(spellLevel, caster);
        int bonusAmount = (int) (3.5 + spellLevel);

        return baseDamage + bonusAmount;
    }
}
