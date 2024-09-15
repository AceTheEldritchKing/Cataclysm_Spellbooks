package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.projectile.Blazing_Bone_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class BonePierceSpell extends AbstractIgnisSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "piercing_bone");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.blazing_bone_speed",
                        Utils.stringTruncation(getBoneSpeed(0.05f, getSpellPower(spellLevel, caster)), 2)),
                Component.translatable("ui.irons_spellbooks.damage",
                        Utils.stringTruncation(getDamage(spellLevel, caster), 2)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(20)
            .build();

    public BonePierceSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 100, castSource, null), playerMagicData);
        }

        // Bone shoot!
        shootBone(entity, spellLevel, level);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    // Finished Recast
    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (recastResult == RecastResult.USED_ALL_RECASTS)
        {
            var level = serverPlayer.level();

            // Bone spread!
            spreadBoneShoot(serverPlayer, level);
        }

        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
    }

    private void shootBone(LivingEntity caster, int spellLevel, Level level)
    {
        caster.playSound(SoundEvents.TRIDENT_THROW, 1.0F, 0.75F);

        double casterX = caster.getX();
        double casterY = CSUtils.getEyeHeight(caster);
        double casterZ = caster.getZ();

        Blazing_Bone_Entity blazingBone = new Blazing_Bone_Entity(level, getDamage(spellLevel, caster), caster);
        blazingBone.moveTo(casterX, casterY, casterZ, 0, caster.getXRot());
        float speed = 0.05F;
        float speedSpellPower = getBoneSpeed(speed, getSpellPower(spellLevel, caster));
        blazingBone.setNoGravity(true);
        blazingBone.shootFromRotation(caster, caster.getXRot(), caster.getYHeadRot(), 0, speedSpellPower, 1.0F);

        level.addFreshEntity(blazingBone);
    }

    private void spreadBoneShoot(LivingEntity caster, Level level)
    {
        caster.playSound(SoundEvents.TRIDENT_THROW, 1.0F, 0.75F);

        for (int i = 0; i < 8; ++i)
        {
            float throwAngle = (float) (i * Math.PI/4.0F);

            double casterX = caster.getX() + Mth.cos(throwAngle);
            double casterY = caster.getY() + caster.getBbHeight() * 0.62D;
            double casterZ = caster.getZ() + Mth.sin(throwAngle);

            double angleX = Mth.cos(throwAngle);
            double angleY = 0.2D;
            double angleZ = Mth.sin(throwAngle);

            Blazing_Bone_Entity blazingBone = new Blazing_Bone_Entity(level, 3, caster);
            blazingBone.moveTo(casterX, casterY, casterZ, i * 45.0F, caster.getXRot());
            float speed = 0.5F;
            blazingBone.setNoGravity(true);
            blazingBone.shoot(angleX, angleY, angleZ, speed, 1.0F);

            level.addFreshEntity(blazingBone);
        }
    }

    private float getBoneSpeed(float speed, float spellPower)
    {
        return speed * spellPower;
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster);
    }
}
