package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.ImpulseCastData;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class GearShiftSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "gear_shift");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.forward_acceleration", Utils.stringTruncation(forwardAcceleration(spellLevel, caster), 1)),
                Component.translatable("ui.cataclysm_spellbooks.upward_acceleration", Utils.stringTruncation(upwardAcceleration(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(20)
            .build();

    public GearShiftSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 10;
        this.baseManaCost = 35;
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public void onClientCast(Level level, int spellLevel, LivingEntity entity, ICastData castData) {
        if (castData instanceof ImpulseCastData data)
        {
            entity.hasImpulse = data.hasImpulse;

            if (!entity.isCrouching())
            {
                entity.setDeltaMovement(entity.getDeltaMovement().add(data.x, data.y, data.z));
            } else
            {
                double y = Math.max(entity.getDeltaMovement().y, data.y);
                entity.setDeltaMovement(data.x, y, data.z);
            }
        }

        super.onClientCast(level, spellLevel, entity, castData);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        entity.hasImpulse = true;

        Vec3 forwards = entity.getLookAngle();

        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId()))
        {
            playerMagicData.getPlayerRecasts().addRecast
                    (new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity),
                            100, castSource, null), playerMagicData);
        }

        if (playerMagicData.getAdditionalCastData() instanceof GearShiftSpell.GearShiftDirectionOverrideCastData)
        {
            if (Utils.random.nextBoolean())
            {
                forwards = forwards.yRot(90);
            } else
            {
                forwards = forwards.yRot(-90);
            }
        }

        if (!entity.isCrouching())
        {
            Vec3 vec3 = forwards.multiply(3, 1, 3).normalize().add(0, 0.25, 0).scale(forwardAcceleration(spellLevel, entity));

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
        } else
        {
            Vec3 up = entity.getLookAngle().multiply(1, 0, 1).normalize().add(0, 5, 0).scale(upwardAcceleration(spellLevel, entity));

            playerMagicData.setAdditionalCastData(new ImpulseCastData((float) up.x, (float) up.y, (float) up.z, true));

            entity.setDeltaMovement(entity.getDeltaMovement().add(up));
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    public float forwardAcceleration(int spellLevel, LivingEntity entity)
    {
        return ((getSpellPower(spellLevel, entity) + (spellLevel + 1)) / 20);
    }

    public float upwardAcceleration(int spellLevel, LivingEntity entity)
    {
        return (getSpellPower(spellLevel, entity) / 120);
    }

    private static class GearShiftDirectionOverrideCastData implements ICastData
    {
        @Override
        public void reset()
        {
            //
        }
    }
}
