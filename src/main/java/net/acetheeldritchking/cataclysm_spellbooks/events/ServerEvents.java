package net.acetheeldritchking.cataclysm_spellbooks.events;

import com.github.L_Ender.cataclysm.init.ModEffect;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModSounds;
import com.github.L_Ender.lionfishapi.server.event.StandOnFluidEvent;
import io.redspace.ironsspellbooks.api.events.ModifySpellLevelEvent;
import io.redspace.ironsspellbooks.api.events.SpellPreCastEvent;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.effect.ChargeEffect;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import io.redspace.ironsspellbooks.registries.MobEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.capabilities.pharaohs_wrath.PlayerKingWrath;
import net.acetheeldritchking.cataclysm_spellbooks.capabilities.pharaohs_wrath.PlayerKingWrathProvider;
import net.acetheeldritchking.cataclysm_spellbooks.capabilities.wrath.PlayerWrath;
import net.acetheeldritchking.cataclysm_spellbooks.capabilities.wrath.PlayerWrathProvider;
import net.acetheeldritchking.cataclysm_spellbooks.effects.potion.*;
import net.acetheeldritchking.cataclysm_spellbooks.entity.mobs.PhantomAncientRemnant;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusPowerArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSAttributeRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.registries.SpellRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSConfig;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.protocol.game.ClientboundSetActionBarTextPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
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
        Entity target = event.getEntity();

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

        // Shutdown
        if (entity instanceof LivingEntity attacker)
        {
            if (attacker.hasEffect(CSPotionEffectRegistry.SHUTDOWN_EFFECT.get()))
            {
                attacker.hurt(SpellRegistries.SHUTDOWN.get().getDamageSource(null), 1);
                event.setCanceled(true);
            }
        }

        // Intrusion Defense System
        if (target instanceof LivingEntity livingTarget)
        {
            if (livingTarget.hasEffect(CSPotionEffectRegistry.IPS_POTION_EFFECT.get()))
            {
                if (event.getSource().isProjectile())
                {
                    float baseDamage = event.getAmount();

                    event.setAmount(baseDamage / 2);
                }
            }
        }

        // King's Wrath
        if (entity instanceof LivingEntity attacker)
        {
            if (attacker.hasEffect(CSPotionEffectRegistry.KINGS_WRATH_EFFECT.get()))
            {
                if (attacker instanceof Player player)
                {
                    player.getCapability(PlayerKingWrathProvider.PLAYER_KINGS_WRATH).ifPresent(kings_wrath -> {
                        //wrath.addWrath(1);

                        float baseAmount = event.getAmount();
                        float damageBonusPerLevel = WrathfulPotionEffect.ATTACK_DAMAGE_PER_WRATH * kings_wrath.getWrath();
                        float bonusDamage = baseAmount * damageBonusPerLevel;
                        float totalDamage = baseAmount + bonusDamage;

                        //System.out.println("Wrath (on hit): " + kings_wrath.getWrath());
                        event.setAmount(totalDamage);
                        //System.out.println("Damage: " + totalDamage);

                        if (target instanceof LivingEntity livingTarget)
                        {
                            livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTCURSE_OF_DESERT.get(), 100, 0, true, true, true));

                            // Do this at max wrath
                            if (kings_wrath.getWrath() == 4)
                            {
                                livingTarget.addEffect(new MobEffectInstance(ModEffect.EFFECTSTUN.get(), 60, 0, true, true, true));
                            }
                        }
                    });

                    player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.REMNANT_CHARGE_ROAR.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
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

        if (CSConfig.bossAttributes.get())
        {
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

        // Wrathful
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

        // King's Wrath
        if (entity instanceof LivingEntity livingEntity)
        {
            if (effect instanceof KingsWrathPotionEffect)
            {
                if (livingEntity.hasEffect(effect) && livingEntity instanceof Player player)
                {
                    player.getCapability(PlayerKingWrathProvider.PLAYER_KINGS_WRATH).ifPresent(kings_wrath -> {

                        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 1, false, false, false));
                        player.addEffect(new MobEffectInstance(ModEffect.EFFECTCURSE_OF_DESERT.get(), 10*20, 1 + kings_wrath.getWrath(), false, true, true));
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10*20, 1 + kings_wrath.getWrath(), false, true, true));

                        kings_wrath.resetWrath();
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

            // King's Wrath
            if (effect instanceof KingsWrathPotionEffect)
            {
                if (livingEntity.hasEffect(effect) && livingEntity instanceof Player player)
                {
                    player.getCapability(PlayerKingWrathProvider.PLAYER_KINGS_WRATH).ifPresent(kings_wrath -> {

                        player.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 60, 1, false, false, false));
                        player.addEffect(new MobEffectInstance(ModEffect.EFFECTCURSE_OF_DESERT.get(), 10*20, 1 + kings_wrath.getWrath(), false, true, true));
                        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 10*20, 1 + kings_wrath.getWrath(), false, true, true));

                        kings_wrath.resetWrath();
                    });

                    //System.out.println("Poof!");
                }
            }
        }
    }

    @SubscribeEvent
    public void onEffectGainEvent(MobEffectEvent.Added event)
    {
        Entity entity = event.getEntity();
        MobEffect effect = event.getEffectInstance().getEffect();

        if (effect instanceof HardwireUpdatePotionEffect)
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                livingEntity.removeEffect(MobEffectRegistry.CHARGED.get());
                livingEntity.removeEffect(MobEffectRegistry.HASTENED.get());
                livingEntity.removeEffect(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get());
            }
        }

        if (effect instanceof SoftwareUpdatePotionEffect)
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                livingEntity.removeEffect(MobEffectRegistry.CHARGED.get());
                livingEntity.removeEffect(MobEffectRegistry.HASTENED.get());
                livingEntity.removeEffect(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get());
            }
        }

        if (effect instanceof ChargeEffect)
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                livingEntity.removeEffect(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get());
                livingEntity.removeEffect(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get());
            }
        }

        if (effect instanceof MagicMobEffect || effect == MobEffectRegistry.HASTENED.get())
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                livingEntity.removeEffect(CSPotionEffectRegistry.HARDWARE_UPDATE_EFFECT.get());
                livingEntity.removeEffect(CSPotionEffectRegistry.SOFTWARE_UPDATE_EFFECT.get());
            }
        }

        if (effect instanceof WrathfulPotionEffect)
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                livingEntity.removeEffect(CSPotionEffectRegistry.KINGS_WRATH_EFFECT.get());
            }
        }

        if (effect instanceof KingsWrathPotionEffect)
        {
            if (entity instanceof LivingEntity livingEntity)
            {
                livingEntity.removeEffect(CSPotionEffectRegistry.WRATHFUL.get());
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

        // Intrusion Defense System
        if (CSConfig.ipsProjectileImmunity.get())
        {
            if (entity instanceof LivingEntity livingTarget)
            {
                if (livingTarget.hasEffect(CSPotionEffectRegistry.IPS_POTION_EFFECT.get()))
                {
                    if (event.getSource().isProjectile())
                    {
                        event.setCanceled(true);
                    }
                }
            }
        }

        // King's Wrath
        if (entity instanceof LivingEntity livingTarget)
        {
            if (livingTarget.hasEffect(CSPotionEffectRegistry.KINGS_WRATH_EFFECT.get()))
            {
                if (livingTarget instanceof Player player)
                {
                    player.getCapability(PlayerKingWrathProvider.PLAYER_KINGS_WRATH).ifPresent(kings_wrath -> {

                        kings_wrath.addWrath(1);
                        //System.out.println("Wrath: " + kings_wrath.getWrath());
                    });
                }
            }
        }
    }

    @SubscribeEvent
    public void onItemUseEvent(LivingEntityUseItemEvent event)
    {
        Entity entity = event.getEntity();

        // Shutdown
        if (entity instanceof LivingEntity attacker)
        {
            if (attacker.hasEffect(CSPotionEffectRegistry.SHUTDOWN_EFFECT.get()))
            {
                attacker.hurt(SpellRegistries.SHUTDOWN.get().getDamageSource(null), 1);
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCastEvent(SpellPreCastEvent event) {
        var entity = event.getEntity();
        boolean hasSilenceEffect = entity.hasEffect(CSPotionEffectRegistry.SHUTDOWN_EFFECT.get());

        if (CSConfig.shutdownSpellCasting.get())
        {
            if (entity instanceof ServerPlayer player && !player.level.isClientSide)
            {
                if (hasSilenceEffect)
                {
                    event.setCanceled(true);
                    // Effect Duration
                    int time = player.getEffect(CSPotionEffectRegistry.SHUTDOWN_EFFECT.get()).getDuration();
                    // convert duration to time format  using the method convertTicksToTime
                    String formattedTime = convertTicksToTime(time);
                    // display a message to the player
                    player.connection.send(new ClientboundSetActionBarTextPacket(Component.translatable("ui.irons_spellbooks.spell_target_success_self", formattedTime).withStyle(ChatFormatting.GREEN)));
                    player.level.playSound(null , player.getX() , player.getY() , player.getZ() ,
                            SoundEvents.FIRE_EXTINGUISH , SoundSource.PLAYERS , 0.5f , 1f);
                }
            }
        }
    }

    public static String convertTicksToTime(int ticks) {
        // Convert ticks to seconds
        int totalSeconds = ticks / 20;

        // Calculate minutes and seconds
        int minutes = totalSeconds / 60;
        int seconds = totalSeconds % 60;

        // Format the result as mm:ss
        return String.format("%02d:%02d" , minutes , seconds);
    }

    @SubscribeEvent
    public static void onLivingHealEvent(LivingHealEvent event)
    {
        MobEffectInstance disabledEffect = event.getEntity().getEffect(CSPotionEffectRegistry.DISABLED_EFFECT.get());

        if (disabledEffect != null)
        {
            event.setCanceled(true);
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

            // King's Wrath
            if (!event.getObject().getCapability(PlayerKingWrathProvider.PLAYER_KINGS_WRATH).isPresent())
            {
                event.addCapability(new ResourceLocation(CataclysmSpellbooks.MOD_ID, "kings_wrath"), new PlayerKingWrathProvider());
            }
        }
    }

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event)
    {
        event.register(PlayerWrath.class);
        event.register(PlayerKingWrath.class);
    }
}