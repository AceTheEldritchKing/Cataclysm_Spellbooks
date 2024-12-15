package net.acetheeldritchking.cataclysm_spellbooks.events;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.lionfishapi.server.event.StandOnFluidEvent;
import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.capabilities.wrath.PlayerWrath;
import net.acetheeldritchking.cataclysm_spellbooks.capabilities.wrath.PlayerWrathProvider;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.AbyssalPredatorPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.CursedFrenzyEffect;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.WrathfulPotionEffect;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.*;
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

        // Forgone Rage
        if (entity instanceof LivingEntity attacker)
        {
            if (attacker.hasEffect(CSPotionEffectRegistry.WRATHFUL.get()))
            {
                if (attacker instanceof Player player)
                {
                    player.getCapability(PlayerWrathProvider.PLAYER_WRATH).ifPresent(wrath -> {
                        wrath.addWrath(1);

                        float baseAmount = event.getAmount();
                        float damageBonusPerLevel = WrathfulPotionEffect.ATTACK_DAMAGE_PER_WRATH * wrath.getWrath();
                        float bonusDamage = baseAmount * damageBonusPerLevel;
                        float totalDamage = baseAmount + bonusDamage;

                        event.setAmount(totalDamage);
                        //System.out.println("Damage: " + totalDamage);
                    });

                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.MALEDICTUS_SHORT_ROAR.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
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

    @SubscribeEvent
    public void onEffectRemove(MobEffectEvent.Remove event)
    {
        Entity entity = event.getEntity();
        MobEffect effect = event.getEffect();
        if (entity instanceof LivingEntity livingEntity)
        {
            if (effect instanceof WrathfulPotionEffect)
            {
                if (livingEntity.hasEffect(effect) && livingEntity instanceof Player player)
                {
                    player.getCapability(PlayerWrathProvider.PLAYER_WRATH).ifPresent(wrath -> {

                        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 1, false, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10*20, 1 + wrath.getWrath(), false, true, true));
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10*20, 1 + wrath.getWrath(), false, true, true));

                        wrath.resetWrath();
                    });
                    //System.out.println("Poof!");
                }
            }
        }
    }

    @SubscribeEvent
    public void onEffectExpire(MobEffectEvent.Expired event)
    {
        Entity entity = event.getEntity();
        MobEffect effect = event.getEffectInstance().getEffect();
        if (entity instanceof LivingEntity livingEntity)
        {
            // Wrathful
            if (effect instanceof WrathfulPotionEffect)
            {
                if (livingEntity.hasEffect(effect) && livingEntity instanceof Player player)
                {
                    player.getCapability(PlayerWrathProvider.PLAYER_WRATH).ifPresent(wrath -> {

                        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 1, false, false, false));
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 10*20, 1 + wrath.getWrath(), false, true, true));
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10*20, 1 + wrath.getWrath(), false, true, true));

                        wrath.resetWrath();
                    });

                    //System.out.println("Poof!");
                }
            }

            // Cursed Frenzy
            if (effect instanceof CursedFrenzyEffect)
            {
                if (!entity.level.isClientSide())
                {
                    //System.out.println("Potion Effect!");
                    CSUtils.spawnHalberdWindmill(5, 5, 1.0F, 0.5F, 0.5F, 1, (LivingEntity) entity, entity.level, 5, 1);
                }
            }
        }
    }

    @SubscribeEvent
    public void onFallEvent(LivingFallEvent event)
    {
        Entity entity = event.getEntity();

        // Cursium Boots
        if (entity instanceof LivingEntity livingEntity)
        {
            if (!livingEntity.getItemBySlot(EquipmentSlot.FEET).isEmpty() &&
                    livingEntity.getItemBySlot(EquipmentSlot.FEET).getItem() == ItemRegistries.CURSIUM_MAGE_BOOTS.get())
            {
                event.setDistance(event.getDistance() * 0.3F);
            }
        }
    }

    @SubscribeEvent
    public void onLivingDeathEvent(LivingDeathEvent event)
    {
        DamageSource damageSource = event.getSource();
        Entity entity = event.getEntity();

        // Cursium Chestplate
        if (entity instanceof LivingEntity livingEntity)
        {
            if (!livingEntity.level.isClientSide())
            {
                if (!damageSource.isBypassInvul())
                {
                    if (CSUtils.tryCurisumChestplateRebirth(livingEntity))
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onLivingAttackEvent(LivingAttackEvent event)
    {
        Entity entity = event.getEntity();

        // Cursium Legs
        if (entity instanceof LivingEntity livingEntity)
        {
            if (!livingEntity.getItemBySlot(EquipmentSlot.LEGS).isEmpty() &&
                    livingEntity.getItemBySlot(EquipmentSlot.LEGS).getItem() == ItemRegistries.CURSIUM_MAGE_LEGGINGS.get())
            {
                if (event.getSource().isBypassInvul())
                {
                    if (livingEntity.getRandom().nextFloat() < 0.15F)
                    {
                        event.setCanceled(true);
                    }
                } else if (!event.getSource().isBypassInvul())
                {
                    if (livingEntity.getRandom().nextFloat() < 0.08F)
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }

    // Capabilities
    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof Player)
        {
            // Wrath
            if (!event.getObject().getCapability(PlayerWrathProvider.PLAYER_WRATH).isPresent())
            {
                event.addCapability(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "wrath"), new PlayerWrathProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        event.register(PlayerWrath.class);
    }
}