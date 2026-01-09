package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Abyss_Fireball_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Ignis_Fireball_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class AbyssFireballSpell extends AbstractIgnisSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "abyss_fireball");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.fireball_details"),
                Component.translatable("ui.cataclysm_spellbooks.recast_count",
                        Utils.stringTruncation(recastCount(spellLevel), 2)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(20)
            .build();

    public AbyssFireballSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
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
        return CastType.INSTANT;
    }

    @Override
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        return recastCount(spellLevel);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 100, castSource, null), playerMagicData);
        }
        // Basing this on player health, let me cook...
        final float MAX_HEALTH = entity.getMaxHealth();
        float baseHealth = entity.getHealth();
        double percent = (baseHealth/MAX_HEALTH) * 100;

        if (percent <= 30)
        {
            shootAbyssFireball(entity, level);
        }
        else if (percent <= 50)
        {
            shootFireball(entity, level, true);
        }
        else
        {
            shootFireball(entity, level, false);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        if (recastResult == RecastResult.USED_ALL_RECASTS)
        {
            var level = serverPlayer.level();

            shootAbyssFireball(serverPlayer, level);
        }

        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);
    }

    private void shootAbyssFireball(LivingEntity caster, Level level)
    {
        Ignis_Abyss_Fireball_Entity fireball = new Ignis_Abyss_Fireball_Entity(level, caster);
        fireball.setPos(caster.position().add(0, caster.getEyeHeight() - fireball.getBoundingBox().getYsize() * 0.5F, 0));
        fireball.shootFromRotation(caster, caster.getXRot(), caster.getYHeadRot(), 0, 1, 1);

        level.addFreshEntity(fireball);
    }

    private void shootFireball(LivingEntity caster, Level level, boolean soul)
    {
        Ignis_Fireball_Entity fireball = new Ignis_Fireball_Entity(level, caster);
        fireball.setPos(caster.position().add(0, caster.getEyeHeight() - fireball.getBoundingBox().getYsize() * 0.5F, 0));
        fireball.shootFromRotation(caster, caster.getXRot(), caster.getYHeadRot(), 0, 1, 1);
        fireball.setSoul(soul);

        level.addFreshEntity(fireball);
    }

    private int recastCount(int count)
    {
        return count;
    }
}
