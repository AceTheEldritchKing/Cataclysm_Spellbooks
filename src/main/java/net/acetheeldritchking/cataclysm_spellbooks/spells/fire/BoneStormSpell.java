package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.projectile.Blazing_Bone_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

@AutoSpellConfig
public class BoneStormSpell extends AbstractIgnisSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "bone_storm");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(10)
            .build();

    public BoneStormSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 0;
        this.spellPowerPerLevel = 1;
        this.castTime = 20;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        launchBone1(entity, spellLevel, level);
        launchBone2(entity, spellLevel, level);
        launchBone3(entity, spellLevel, level);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void launchBone1(LivingEntity caster, int spellLevel, Level level)
    {
        caster.playSound(SoundEvents.TRIDENT_THROW, 1.0F, 0.75F);

        for (int i = 0; i < spellLevel + 3; ++i)
        {
            float throwAngle = (float) (i * Math.PI/4.0F);

            double casterX = caster.getX() + Mth.cos(throwAngle);
            double casterY = caster.getY() + caster.getBbHeight() * 0.62D;
            double casterZ = caster.getZ() + Mth.sin(throwAngle);

            double angleX = Mth.cos(throwAngle);
            double angleY = 0.0D;
            double angleZ = Mth.sin(throwAngle);

            Blazing_Bone_Entity blazingBone = new Blazing_Bone_Entity(level, caster);
            blazingBone.moveTo(casterX, casterY, casterZ, i * 45.0F, caster.getXRot());
            float speed = 0.5F;
            blazingBone.shoot(angleX, angleY, angleZ, speed, 1.0F);

            level.addFreshEntity(blazingBone);
        }
    }

    private void launchBone2(LivingEntity caster, int spellLevel, Level level)
    {
        caster.playSound(SoundEvents.TRIDENT_THROW, 1.0F, 0.75F);

        for (int i = 0; i < spellLevel + 2; ++i)
        {
            float throwAngle = (float) (i * Math.PI/3.0F);

            double casterX = caster.getX() + Mth.cos(throwAngle);
            double casterY = caster.getY() + caster.getBbHeight() * 0.62D;
            double casterZ = caster.getZ() + Mth.sin(throwAngle);

            double angleX = Mth.cos(throwAngle);
            double angleY = 0.0D;
            double angleZ = Mth.sin(throwAngle);

            Blazing_Bone_Entity blazingBone = new Blazing_Bone_Entity(level, caster);
            blazingBone.moveTo(casterX, casterY, casterZ, i * 60.0F, caster.getXRot());
            float speed = 0.6F;
            blazingBone.shoot(angleX, angleY, angleZ, speed, 1.0F);

            level.addFreshEntity(blazingBone);
        }
    }

    private void launchBone3(LivingEntity caster, int spellLevel, Level level)
    {
        caster.playSound(SoundEvents.TRIDENT_THROW, 1.0F, 0.75F);

        for (int i = 0; i < spellLevel + 5; ++i)
        {
            float throwAngle = (float) (i * Math.PI/5.0F);

            double casterX = caster.getX() + Mth.cos(throwAngle);
            double casterY = caster.getY() + caster.getBbHeight() * 0.62D;
            double casterZ = caster.getZ() + Mth.sin(throwAngle);

            double angleX = Mth.cos(throwAngle);
            double angleY = 0.0D;
            double angleZ = Mth.sin(throwAngle);

            Blazing_Bone_Entity blazingBone = new Blazing_Bone_Entity(level, caster);
            blazingBone.moveTo(casterX, casterY, casterZ, i * 36.0F, caster.getXRot());
            float speed = 0.4F;
            blazingBone.shoot(angleX, angleY, angleZ, speed, 1.0F);

            level.addFreshEntity(blazingBone);
        }
    }
}
