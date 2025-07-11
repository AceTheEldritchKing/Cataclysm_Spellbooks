package net.acetheeldritchking.cataclysm_spellbooks.spells.evocation;

import com.github.L_Ender.cataclysm.init.ModTag;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class PilferSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "pilfer");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.cataclysm_spellbooks.pilfer_priority"));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(SchoolRegistry.EVOCATION_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(100)
            .build();

    public PilferSpell()
    {
        this.manaCostPerLevel = 15;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 0;
        this.castTime = 20;
        this.baseManaCost = 110;
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
    public int getEffectiveCastTime(int spellLevel, @Nullable LivingEntity entity) {
        return getCastTime(spellLevel);
    }

    @Override
    public AnimationHolder getCastStartAnimation() {
        return SpellAnimations.ONE_HANDED_HORIZONTAL_SWING_ANIMATION;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundEvents.PLAYER_ATTACK_SWEEP);
    }

    @Override
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, spellLevel * 2, .15f);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetEntityCastData)
        {
            var targetEntity = targetEntityCastData.getTarget((ServerLevel) level);
            if (targetEntity != null)
            {
                ItemStack offhandItem = targetEntity.getOffhandItem();
                ItemStack mainhandItem = targetEntity.getMainHandItem();

                int i;
                ItemStack offhandCopyItem;
                if (!offhandItem.isEmpty())
                {
                    if (!offhandItem.is(ModTag.STICKY_ITEM))
                    {
                        i = offhandItem.getCount();
                        offhandCopyItem = offhandItem.copy();
                        offhandCopyItem.setCount(1);
                        stealItemDrop(offhandCopyItem, targetEntity);
                        targetEntity.setItemSlot(EquipmentSlot.OFFHAND, offhandItem.split(i - 1));
                    }
                } else if (!mainhandItem.isEmpty())
                {
                    if (!offhandItem.is(ModTag.STICKY_ITEM))
                    {
                        i = mainhandItem.getCount();
                        offhandCopyItem = mainhandItem.copy();
                        offhandCopyItem.setCount(1);
                        stealItemDrop(offhandCopyItem, targetEntity);
                        targetEntity.setItemSlot(EquipmentSlot.MAINHAND, mainhandItem.split(i - 1));
                    }
                }
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private ItemEntity stealItemDrop(ItemStack itemStack, LivingEntity target)
    {
        if (itemStack.isEmpty())
        {
            return null;
        } else if (target.level().isClientSide)
        {
            return null;
        } else
        {
            double d0 = target.getEyeY() - 0.3F;
            ItemEntity itemEntity = new ItemEntity(target.level(), target.getX(), d0, target.getZ(), itemStack);
            itemEntity.setDefaultPickUpDelay();
            itemEntity.setExtendedLifetime();

            float targetSinX = Mth.sin((float) (target.getXRot() * (Math.PI / 180)));
            float targetCosX = Mth.cos((float) (target.getXRot() * (Math.PI / 180)));
            float targetSinY = Mth.sin((float) (target.getYRot() * (Math.PI / 180)));
            float targetCosY = Mth.cos((float) (target.getYRot() * (Math.PI / 180)));

            float f5 = (float) (target.getRandom().nextFloat() * (Math.PI * 2F));
            float f6 = 0.02F * target.getRandom().nextFloat();

            itemEntity.setDeltaMovement(
                    (double)(-targetSinY * targetCosX * 0.3F) + Math.cos(f5) * (double)f6,
                    (-targetSinX * 0.3F + 0.1F + (target.getRandom().nextFloat() - target.getRandom().nextFloat()) * 0.1F),
                    (double)(targetCosY * targetCosX * 0.3F) + Math.sin(f5) * (double)f6);
            target.level().addFreshEntity(itemEntity);
            return itemEntity;
        }
    }
}
