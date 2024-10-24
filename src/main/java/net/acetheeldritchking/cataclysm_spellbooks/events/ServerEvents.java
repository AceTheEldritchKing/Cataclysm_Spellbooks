package net.acetheeldritchking.cataclysm_spellbooks.events;

import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignited_Revenant_Entity;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.Ignited_Berserker_Entity;
import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModTag;
import com.github.L_Ender.lionfishapi.server.event.StandOnFluidEvent;
import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.damage.DamageSources;
import io.redspace.ironsspellbooks.entity.mobs.goals.*;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.AbyssalPredatorPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.spells.nature.AmethystPunctureSpell;
import net.acetheeldritchking.cataclysm_spellbooks.util.IExtendedCataclysmProjectileInterface;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Objects;

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

        // Ignis Wizard armor
        LivingEntity livingEntity = event.getEntity();
        ItemStack legEquipment = livingEntity.getItemBySlot(EquipmentSlot.LEGS);
        if (!legEquipment.isEmpty() && event.getEntity() != null &&
                event.getSource().getEntity() != null &&
                legEquipment.getItem() == ItemRegistries.IGNITIUM_WIZARD_LEGGINGS.get())
        {
            Entity attacker = event.getSource().getEntity();
            if (attacker instanceof LivingEntity livingAttacker && attacker != event.getEntity() && event.getEntity().getRandom().nextFloat() < 0.5F)
            {
                MobEffectInstance mobEffectInstance = livingAttacker.getEffect(ModEffect.EFFECTBLAZING_BRAND.get());

                int i = 1;
                if (mobEffectInstance != null)
                {
                    i = i + mobEffectInstance.getAmplifier();
                    livingAttacker.removeEffectNoUpdate(ModEffect.EFFECTBLAZING_BRAND.get());
                }
                else
                {
                    i = i + 1;
                }

                i = Mth.clamp(i, 0, 2);
                MobEffectInstance effectInstance = new MobEffectInstance(ModEffect.EFFECTBLAZING_BRAND.get(), 100, i, false, false, true);
                (livingAttacker).addEffect(effectInstance);
                if (!attacker.isOnFire())
                {
                    attacker.setSecondsOnFire(5);
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
        if (mob.getType() == ModEntities.MALEDICTUS.get())
        {
            // Maledictus takes extra eldritch damage, and less ice damage
            setIfNonNull(mob, AttributeRegistry.ELDRITCH_MAGIC_RESIST.get(), 0.5);
            setIfNonNull(mob, AttributeRegistry.ICE_MAGIC_RESIST.get(), 1.5);
        }
    }

    @SubscribeEvent
    public void standOnFluidEvent(StandOnFluidEvent event)
    {
        LivingEntity entity = event.getEntity();
        ItemStack bootEquipment = entity.getItemBySlot(EquipmentSlot.FEET);

        if (!bootEquipment.isEmpty() && bootEquipment.getItem() == ItemRegistries.IGNITIUM_WIZARD_BOOTS.get() &&
                !entity.isShiftKeyDown() &&
                (event.getFluidState().is(Fluids.LAVA) || event.getFluidState().is(Fluids.FLOWING_LAVA)))
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onLivingSetTargetEvent(LivingChangeTargetEvent event)
    {
        if (event.getNewTarget() != null)
        {
            LivingEntity livingEntity = event.getEntity();

            // Ignis Wizard Leggings
            if (livingEntity instanceof Mob mob)
            {
                if (mob.getType().is(ModTag.LAVA_MONSTER) && livingEntity.getLastHurtByMob() != event.getNewTarget() && event.getNewTarget().getItemBySlot(EquipmentSlot.HEAD).is(ItemRegistries.IGNITIUM_WIZARD_HELMET.get()))
                {
                    event.setCanceled(true);
                }
            }

            // Ignited Reinforcement Spell
            if (livingEntity instanceof Ignited_Revenant_Entity ignitedRevenant ||
                    livingEntity instanceof Ignited_Berserker_Entity ignitedBerserker)
            {
                if (event.getNewTarget().hasEffect(CSPotionEffectRegistry.IGNITED_TIMER.get()))
                {
                    event.setCanceled(true);
                }
            }
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

    // Modify Spell Event
    @SubscribeEvent
    public void onSpellModifyEvent(ModifySpellLevelEvent event)
    {
        if (event.getEntity().getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistries.BLOOM_STONE_STAFF.get()))
        {
            if (event.getSpell().equals(SpellRegistries.AMETHYST_PUNCTURE.get()))
            {
                event.addLevels(1);
                //System.out.println("Added spell level");
                //System.out.println("spell level: " + event.getLevel());
            }
        }
    }
}