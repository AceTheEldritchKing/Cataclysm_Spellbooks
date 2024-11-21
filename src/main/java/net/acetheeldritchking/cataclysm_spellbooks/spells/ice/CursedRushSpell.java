package net.acetheeldritchking.cataclysm_spellbooks.spells.ice;

import com.github.L_Ender.cataclysm.entity.projectile.Phantom_Halberd_Entity;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.ImpulseCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

@AutoSpellConfig
public class CursedRushSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "cursed_rush");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.ICE_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(80)
            .build();

    public CursedRushSpell()
    {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 5;
        this.castTime = 10;
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
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new ImpulseCastData();
    }

    @Override
    public void onClientCast(Level level, int spellLevel, LivingEntity entity, ICastData castData) {
        if (castData instanceof ImpulseCastData data)
        {
            entity.hasImpulse = data.hasImpulse;
            entity.setDeltaMovement(entity.getDeltaMovement().add(data.x, data.y, data.z));
        }

        super.onClientCast(level, spellLevel, entity, castData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.hasImpulse = true;
        float multiplier = ((getSpellPower(spellLevel, entity) + (spellLevel + 10)) / 10);

        Vec3 forwards = entity.getLookAngle();
        //forwards = forwards.yRot(90);
        if (playerMagicData.getAdditionalCastData() instanceof CursedRushDirectionOverrideCastData)
        {
            if (Utils.random.nextBoolean())
            {
                forwards = forwards.yRot(90);
            } else
            {
                forwards = forwards.yRot(-90);
            }
        }

        Vec3 vec3 = forwards.multiply(3, 1, 3).normalize().add(0, 0.25, 0).scale(multiplier);

        if (entity.isOnGround())
        {
            entity.setPos(entity.position().add(0, 1.5, 0));
            vec3.add(0, 0.25, 0);
        }

        playerMagicData.setAdditionalCastData(new ImpulseCastData((float) vec3.x, (float) vec3.y, (float) vec3.z, true));
        entity.setDeltaMovement(new Vec3(
                Mth.lerp(0.75F, entity.getDeltaMovement().x, vec3.x),
                Mth.lerp(0.75F, entity.getDeltaMovement().y, vec3.y),
                Mth.lerp(0.75F, entity.getDeltaMovement().z, vec3.z)
        ));

        entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.CURSED_FRENZY.get(), 20, (int) getDamage(spellLevel, entity), false, false, false));
        CSUtils.spawnHalberdWindmill(5, 5, 1.0F, 1.0F, 0.2F, 1, entity, level, 5, spellLevel);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 5;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster)
    {
        float baseDamage = getDamage(spellLevel, caster);
        int bonusAmount = (int) (3.5 + spellLevel);

        return baseDamage + bonusAmount;
    }

    private static class CursedRushDirectionOverrideCastData implements ICastData
    {
        @Override
        public void reset()
        {
            // Doesn't seem like I have to put anything in here tbh?
        }
    }
}
