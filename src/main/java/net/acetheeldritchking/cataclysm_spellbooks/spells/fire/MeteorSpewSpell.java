package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Lava_Bomb_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@AutoSpellConfig
public class MeteorSpewSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "meteor_spew");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(100)
            .build();

    public MeteorSpewSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 5;
        this.spellPowerPerLevel = 5;
        this.castTime = 30;
        this.baseManaCost = 40;
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return spellLevel;
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return SpellAnimations.SPIT_FINISH_ANIMATION;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(ModSounds.MONSTROSITYGROWL.get());
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

        Vec3 viewVec = entity.getViewVector(1.0F);

        Lava_Bomb_Entity lavaBomb = new Lava_Bomb_Entity(ModEntities.LAVA_BOMB.get(), level, entity);

        lavaBomb.setPosRaw(entity.getX() + 0.5, CSUtils.getEyeHeight(entity), entity.getZ() + 0.5);
        lavaBomb.shoot(viewVec.x, viewVec.y, viewVec.z, 0.85F, 1.0F);

        ScreenShake_Entity.ScreenShake(level, entity.position(), 3.0F, 0.05F, 20, 20);
        level.addFreshEntity(lavaBomb);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }
}
