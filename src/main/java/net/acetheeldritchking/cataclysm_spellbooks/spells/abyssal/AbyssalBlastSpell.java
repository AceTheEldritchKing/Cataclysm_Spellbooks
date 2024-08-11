package net.acetheeldritchking.cataclysm_spellbooks.spells.abyssal;
;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.The_Leviathan.Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.CameraShakeData;
import io.redspace.ironsspellbooks.api.util.CameraShakeManager;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.Optional;

@AutoSpellConfig
public class AbyssalBlastSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "abyssal_blast");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(200)
            .build();

    public AbyssalBlastSpell()
    {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 1;
        this.castTime = 60;
        this.baseManaCost = 700;
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
    public Optional<SoundEvent> getCastStartSound() {
        return Optional.of(ModSounds.ABYSS_BLAST_ONLY_CHARGE.get());
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.ABYSS_BLAST.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double casterX = entity.getX();
        double casterY = entity.getY();
        double casterZ = entity.getZ();

        float dir = 90F;
        float casterXRot = (float) -(entity.getXRot() * Math.PI/180F);
        float casterYHeadRot = (float) ((entity.getYHeadRot() + dir) * Math.PI/180D);

        CameraShakeManager.addCameraShake(new CameraShakeData(15, entity.position(), 25));
        if (!level.isClientSide)
        {
            // Prevent player from moving
            entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                    80, 2, true, true, true));

            // Firing mah laser
            Abyss_Blast_Entity abyss_blast = new Abyss_Blast_Entity((EntityType)ModEntities.ABYSS_BLAST.get(),
                    level, entity, casterX, casterY, casterZ,
                    casterYHeadRot, casterXRot, 80, dir);

            level.addFreshEntity(abyss_blast);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
}
