package net.acetheeldritchking.cataclysm_spellbooks.spells.ender;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class GravitationPullSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "gravitation_pull");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.gravitation"),
                Component.translatable("ui.cataclysm_spellbooks.range", spellLevel),
                Component.translatable("ui.cataclysm_spellbooks.difference", getDifference(spellLevel, caster))
        );
    }

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
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.PORTAL_AMBIENT);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.CHARGE_SPIT_ANIMATION;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        double radius;
        Item gauntlet = ModItems.GAUNTLET_OF_GUARD.get();
        if (entity.getOffhandItem().is(gauntlet) || entity.getMainHandItem().is(gauntlet))
        {
            //System.out.println("Hand?");
            radius =  spellLevel * 15;
        }
        else
        {
            radius = spellLevel * 10;
        }

        List<LivingEntity> entitiesNearby = level.getEntitiesOfClass(LivingEntity.class, entity.getBoundingBox().inflate(radius));
        entitiesNearby.forEach(target -> {
            if (target == entity || target instanceof Player player && player.getAbilities().invulnerable)
            {
                return;
            }

            // Ty Cadentem :D
            Vec3 difference = entity.position().subtract(target.position());
            difference = difference.normalize().scale(getDifference(spellLevel, entity));

            if (entity.isCrouching())
            {
                // Pull entities away
                target.setDeltaMovement(difference.multiply(-getDifference(spellLevel, entity), -getDifference(spellLevel, entity), -getDifference(spellLevel, entity)));
            }
            else
            {
                // Drag entities in
                float distance = target.distanceTo(entity);

                if (distance <= 1)
                {
                    // entities can't really exist in the same position as the caster (they just bounce around)
                    return;
                }

                // Handle overshot
                double overshot = difference.length() / distance;

                if (overshot > 1)
                {
                    // max. pull should be the distance to the entity, not further (reduces bouncing around)
                    difference = new Vec3(difference.x() / overshot, difference.y() / overshot, difference.z() / overshot);
                }

                // if the added movement is too much they fly past the target - 0.2F
                difference = difference.multiply(getDifference(spellLevel, entity), getDifference(spellLevel, entity), getDifference(spellLevel, entity));
                target.setDeltaMovement(difference);
            }
        });

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDifference(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster)/2;
    }
}
