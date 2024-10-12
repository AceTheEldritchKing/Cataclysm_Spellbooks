package net.acetheeldritchking.cataclysm_spellbooks.spells.ender;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.List;

@AutoSpellConfig
public class GravitationPullSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "gravitation_pull");

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.ENDER_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(30)
            .build();

    public GravitationPullSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 5;
        this.castTime = 100;
        this.baseManaCost = 10;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double radius = spellLevel * 3;
        List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        Iterator<LivingEntity> iterator = entitiesNearby.iterator();

        while (true)
        {
            LivingEntity target;
            do {
                target = iterator.next();
                if (!iterator.hasNext())
                {
                    return;
                }

            } while (target instanceof Player && ((Player) target).getAbilities().invulnerable);

            if (entity.isCrouching())
            {
                // Pull entities away
                Vec3 diff = target.position().add(entity.position().add(0,0,0));
                diff = diff.normalize().scale(getSpellPower(spellLevel, entity));
                target.setDeltaMovement(target.getDeltaMovement().add(diff));
            } else
            {
                // Drag entities away
                Vec3 diff = target.position().subtract(entity.position().add(0,0,0));
                diff = diff.normalize().scale(getSpellPower(spellLevel, entity));
                target.setDeltaMovement(target.getDeltaMovement().subtract(diff));
            }

            super.onCast(level, spellLevel, entity, castSource, playerMagicData);
        }
    }
}
