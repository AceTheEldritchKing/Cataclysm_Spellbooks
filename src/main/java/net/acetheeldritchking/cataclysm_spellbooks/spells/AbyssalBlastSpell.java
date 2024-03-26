package net.acetheeldritchking.cataclysm_spellbooks.spells;

import com.github.L_Ender.cataclysm.entity.BossMonsters.The_Leviathan.Abyss_Blast_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Optional;

@AutoSpellConfig
public class AbyssalBlastSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "abyssal_blast");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.ABYSSAL_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(20)
            .build();

    public AbyssalBlastSpell()
    {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 1;
        this.castTime = 10;
        this.baseManaCost = 350;
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
        return CastType.CONTINUOUS;
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

        Vec3 casterEyePosition = entity.getEyePosition();

        float dir = 90F;
        float casterXRot = (float) (entity.getXRot() * Math.PI/180F);
        float casterEyeYPosition = (float) (-(casterEyePosition.y + dir) * Math.PI/180.0D);

        HitResult hitResult = Utils.raycastForEntity(level, entity, 32, true, 0.15F);
        if (hitResult.getType() == HitResult.Type.ENTITY)
        {
            Entity target = ((EntityHitResult)hitResult).getEntity();
            if (target instanceof LivingEntity)
            {
                Abyss_Blast_Entity abyss_blast = new Abyss_Blast_Entity(ModEntities.ABYSS_BLAST.get(),
                        entity.level, entity,casterX, casterY, casterZ,
                        casterEyeYPosition, casterXRot, 80, dir);

                level.addFreshEntity(abyss_blast);
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
}
