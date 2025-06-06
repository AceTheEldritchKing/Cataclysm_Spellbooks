package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import io.redspace.ironsspellbooks.capabilities.magic.RecastResult;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.ExcelsiusPowerArmorItem;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

@AutoSpellConfig
public class OverchargedSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "overcharged");

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
                            60*20, castSource, null), playerMagicData);
        }

        if (entity.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get() && ExcelsiusPowerArmorItem.IsOvercharged == false)
        {
            System.out.println("Overcharged = true");
            System.out.println("Overcharge type before: " + ExcelsiusPowerArmorItem.IsOvercharged);

            ExcelsiusPowerArmorItem.IsOvercharged = true;

            System.out.println("Overcharge type after: " + ExcelsiusPowerArmorItem.IsOvercharged);
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onRecastFinished(ServerPlayer serverPlayer, RecastInstance recastInstance, RecastResult recastResult, ICastDataSerializable castDataSerializable) {
        super.onRecastFinished(serverPlayer, recastInstance, recastResult, castDataSerializable);

        if (serverPlayer.getItemBySlot(EquipmentSlot.CHEST).getItem() == ItemRegistries.EXCELSIUS_POWER_CHESTPLATE.get())
        {
            System.out.println("Overcharged = false");
            System.out.println("Overcharge type before: " + ExcelsiusPowerArmorItem.IsOvercharged);
            ExcelsiusPowerArmorItem.IsOvercharged = false;

            System.out.println("Overcharge type after: " + ExcelsiusPowerArmorItem.IsOvercharged);
        }
    }
}
