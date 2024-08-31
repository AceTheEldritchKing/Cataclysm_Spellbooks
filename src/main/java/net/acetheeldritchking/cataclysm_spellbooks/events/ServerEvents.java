package net.acetheeldritchking.cataclysm_spellbooks.events;

import com.github.L_Ender.cataclysm.init.ModEntities;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.AbyssalPredatorPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ServerEvents {

    @SubscribeEvent
    public static void onLivingDamageEvent(LivingDamageEvent event)
    {
        Entity entity = event.getSource().getEntity();

        if (entity instanceof LivingEntity attacker)
        {
            // ABYSSAL PREDATOR
            if (attacker.hasEffect(CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get()))
            {
                int effectLevel = attacker.getEffect(CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get()).getAmplifier() + 1;
                float baseAmount = event.getAmount();
                float damageBonusPerLevel = AbyssalPredatorPotionEffect.ATTACK_DAMAGE_BONUS_PER_LEVEL * effectLevel;
                float bonusDamage = baseAmount * damageBonusPerLevel;
                float totalDamage = baseAmount + bonusDamage;

                // Now do the bonus when underwater
                if (attacker.isInWaterOrRain())
                {
                    //System.out.println("I'm doing damage guys: " + totalDamage);
                    event.setAmount(totalDamage);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingTickEvent(LivingEvent.LivingTickEvent event)
    {
        Entity entity = event.getEntity();
        Level level = entity.level;
        if (!level.isClientSide)
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                // ABYSSAL PREDATOR
                if (livingEntity.hasEffect(CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get()))
                {
                    int effectLevel = livingEntity.getEffect(CSPotionEffectRegistry.ABYSSAL_PREDATOR_EFFECT.get()).getAmplifier() + 1;
                    float baseMovementSpeed = livingEntity.getSpeed();
                    float speedBonusPerLevel = AbyssalPredatorPotionEffect.MOVEMENT_SPEED_BONUS_PER_LEVEL * effectLevel;
                    float bonusSpeed = baseMovementSpeed * speedBonusPerLevel;
                    float totalSpeed = baseMovementSpeed + bonusSpeed;

                    if (livingEntity.isInWaterOrRain())
                    {
                        livingEntity.setSpeed(totalSpeed);
                    }
                    else
                    {
                        livingEntity.setSpeed(baseMovementSpeed);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void handleResistanceAttributeSpawn(LivingSpawnEvent.SpecialSpawn event)
    {
        var mob = event.getEntity();

        if (mob.getType() == ModEntities.IGNIS.get())
        {
            // Ignis takes extra abyssal damage, and less fire damage
            setIfNonNull(mob, CSAttributeRegistry.ABYSSAL_MAGIC_RESIST.get(), 0.5);
            setIfNonNull(mob, AttributeRegistry.FIRE_MAGIC_RESIST.get(), 1.5);
        }
        if (mob.getType() == ModEntities.THE_LEVIATHAN.get())
        {
            // Leviathan takes extra lightning damage, and less abyssal damage
            setIfNonNull(mob, AttributeRegistry.LIGHTNING_MAGIC_RESIST.get(), 0.5);
            setIfNonNull(mob, CSAttributeRegistry.ABYSSAL_MAGIC_RESIST.get(), 1.5);
        }
        if (mob.getType() == ModEntities.ENDER_GUARDIAN.get())
        {
            // Ender Guardian takes extra ice damage, and less ender damage
            setIfNonNull(mob, AttributeRegistry.ICE_MAGIC_RESIST.get(), 0.5);
            setIfNonNull(mob, AttributeRegistry.ENDER_MAGIC_RESIST.get(), 1.5);
        }
        if (mob.getType() == ModEntities.THE_HARBINGER.get())
        {
            // Harbinger takes extra lightning damage, and less blood damage
            setIfNonNull(mob, AttributeRegistry.LIGHTNING_MAGIC_RESIST.get(), 0.5);
            setIfNonNull(mob, AttributeRegistry.BLOOD_MAGIC_RESIST.get(), 1.5);
        }
        if (mob.getType() == ModEntities.ANCIENT_REMNANT.get())
        {
            // Ancient Remnant takes extra holy damage, and less fire damage(?)
            setIfNonNull(mob, AttributeRegistry.HOLY_MAGIC_RESIST.get(), 0.5);
            setIfNonNull(mob, AttributeRegistry.FIRE_MAGIC_RESIST.get(), 1.5);
        }
        if (mob.getType() == ModEntities.NETHERITE_MONSTROSITY.get())
        {
            // Netherite Monstrosity takes extra ice damage, and less fire damage
            setIfNonNull(mob, AttributeRegistry.ICE_MAGIC_RESIST.get(), 0.5);
            setIfNonNull(mob, AttributeRegistry.FIRE_MAGIC_RESIST.get(), 1.5);
        }
    }

    // Using same code from ISS for dealing with mob attributes, please forgive me
    private static void setIfNonNull(LivingEntity entity, Attribute attribute, double value)
    {
        var instance = entity.getAttributes().getInstance(attribute);
        if (instance != null)
        {
            instance.setBaseValue(value);
        }
    }
}