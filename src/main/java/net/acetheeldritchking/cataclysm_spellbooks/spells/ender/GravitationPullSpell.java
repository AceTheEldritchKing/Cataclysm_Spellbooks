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
                System.out.println(target);

            } while (target instanceof Player && ((Player) target).getAbilities().invulnerable);

            if (entity.isCrouching())
            {
                // Pull entities away
                Vec3 diff = target.position().add(entity.position().subtract(entity.getX(),entity.getY(),entity.getZ()));
                diff = diff.normalize().scale(getDifference(spellLevel, entity));
                target.setDeltaMovement(target.getDeltaMovement().add(diff));
            } else
            {
                // Drag entities in
                Vec3 diff = target.position().subtract(entity.position().add(0,0,0));
                diff = diff.normalize().scale(getDifference(spellLevel, entity));
                target.setDeltaMovement(target.getDeltaMovement().subtract(diff));
            }

            super.onCast(level, spellLevel, entity, castSource, playerMagicData);
        }
    }

    private float getDifference(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster)/2;
    }
}
