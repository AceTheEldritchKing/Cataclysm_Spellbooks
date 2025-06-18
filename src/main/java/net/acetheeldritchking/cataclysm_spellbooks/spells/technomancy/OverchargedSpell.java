package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSPotionEffectRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class OverchargedSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "overcharged");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.effect_length", Utils.stringTruncation((double) getEffectDuration() / 20, 2)),
                Component.translatable("ui.cataclysm_spellbooks.effect_percentage", Utils.stringTruncation(getPercentageEffectAmplifier(), 2))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(10)
            .build();

    public OverchargedSpell()
    {
        this.manaCostPerLevel = 20;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 2;
        this.castTime = 20;
        this.baseManaCost = 150;
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
        return 2;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId()))
        {
            playerMagicData.getPlayerRecasts().addRecast
                    (new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity),
                            getEffectDuration(), castSource, null), playerMagicData);
        }

        if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get() && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_POWER_HELMET.get())
        {
            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.MANA_OVERCHARGED_EFFECT.get(),
                    getEffectDuration(),
                    0,
                    true,
                    true,
                    true));
        }
        else if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get() && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_RESIST_HELMET.get())
        {
            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.SPELL_RES_OVERCHARGED_EFFECT.get(),
                    getEffectDuration(),
                    0,
                    true,
                    true,
                    true));
        }
        else if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get() && entity.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_SPEED_HELMET.get())
        {
            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.COOLDOWN_OVERCHARGED_EFFECT.get(),
                    getEffectDuration(),
                    0,
                    true,
                    true,
                    true));
        }
        else {
            entity.addEffect(new MobEffectInstance(CSPotionEffectRegistry.BASE_OVERCHARGED_EFFECT.get(),
                    getEffectDuration(),
                    0,
                    true,
                    true,
                    true));
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);

        if (serverPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get() || serverPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_POWER_HELMET.get())
        {
            serverPlayer.removeEffect(CSPotionEffectRegistry.MANA_OVERCHARGED_EFFECT.get());
        }
        else if (serverPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_RESIST_CHESTPLATE.get() || serverPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_RESIST_HELMET.get())
        {
            serverPlayer.removeEffect(CSPotionEffectRegistry.SPELL_RES_OVERCHARGED_EFFECT.get());
        }
        else if (serverPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_SPEED_CHESTPLATE.get() || serverPlayer.getItemBySlot(EquipmentSlot.HEAD).getItem() == ItemRegistries.EXCELSIUS_SPEED_HELMET.get())
        {
            serverPlayer.removeEffect(CSPotionEffectRegistry.COOLDOWN_OVERCHARGED_EFFECT.get());
        } else
        {
            // Just remove everything as a fallback
            serverPlayer.removeEffect(CSPotionEffectRegistry.MANA_OVERCHARGED_EFFECT.get());
            serverPlayer.removeEffect(CSPotionEffectRegistry.SPELL_RES_OVERCHARGED_EFFECT.get());
            serverPlayer.removeEffect(CSPotionEffectRegistry.COOLDOWN_OVERCHARGED_EFFECT.get());
            serverPlayer.removeEffect(CSPotionEffectRegistry.BASE_OVERCHARGED_EFFECT.get());
        }
    }

    private float getPercentageEffectAmplifier()
    {
        return 0.5F * 100;
    }

    public int getEffectDuration()
    {
        return 60 * 20;
    }
}
