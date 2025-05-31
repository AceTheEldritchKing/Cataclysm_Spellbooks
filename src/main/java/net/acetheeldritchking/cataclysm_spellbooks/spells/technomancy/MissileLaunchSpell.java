package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import com.github.L_Ender.cataclysm.entity.projectile.Wither_Homing_Missile_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.MultiTargetEntityCastData;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class MissileLaunchSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "missile_launch");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.projectile_count", (getRecastCount(spellLevel, caster)))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(20)
            .build();

    public MissileLaunchSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
        this.baseManaCost = 20;
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, .15f);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetEntityCastData)
        {
            var recasts = playerMagicData.getPlayerRecasts();

            if (!recasts.hasRecastForSpell(getSpellId()))
            {
                recasts.addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 80, castSource, new MultiTargetEntityCastData(targetEntityCastData.getTarget((ServerLevel) level))), playerMagicData);
            } else
            {
                var instance = recasts.getRecastInstance(this.getSpellId());

                if (instance != null && instance.getCastData() instanceof MultiTargetEntityCastData targetingData)
                {
                    targetingData.addTarget(targetEntityCastData.getTargetUUID());
                }
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);

        if (castDataSerializable instanceof MultiTargetEntityCastData targetingData)
        {
            targetingData.getTargets().forEach(uuid -> {
                var target = (LivingEntity) ((ServerLevel) serverPlayer.level).getEntity(uuid);

                if (target != null)
                {
                    float yDiff = 0.5F;
                    yDiff += 1.5F;

                    launchMissiles(yDiff, 0.5F, serverPlayer, target);
                }
            });
        }
    }

    private void launchMissiles(float yDiff, float math, LivingEntity caster, LivingEntity target)
    {
        float f = (float) Math.cos(caster.yBodyRot * (Math.PI / 180));
        float f1 = (float) Math.sin(caster.yBodyRot * (Math.PI / 180));
        double theta = caster.yBodyRot * (Math.PI / 180);

        theta += Math.PI / 2;

        double vecX = Math.cos(theta);
        double vecZ = Math.sin(theta);

        double x = caster.getX() + 0.5 * vecX + (f * math);
        double y = caster.getY() + yDiff;
        double z = caster.getZ() + 0.5 * vecZ + (f1 * math);

        Wither_Homing_Missile_Entity homingMissile = new Wither_Homing_Missile_Entity(caster.level, caster, target);
        homingMissile.setPosRaw(x, y, z);
        caster.level.addFreshEntity(homingMissile);
    }

    private float getDamage(int spellLevel, LivingEntity caster) {
        return getSpellPower(spellLevel, caster);
    }

    @Override
    public ICastDataSerializable getEmptyCastData() {
        return new MultiTargetEntityCastData();
    }
}
