package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import mod.azure.azurelib.model.GeoModel;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.BloomStoneMageArmorItem;
import net.minecraft.resources.ResourceLocation;

public class BloomStoneMageArmorModel extends GeoModel<BloomStoneMageArmorItem> {
    @Override
    public ResourceLocation getModelResource(BloomStoneMageArmorItem bloomStoneMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/bloom_stone_mage.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BloomStoneMageArmorItem bloomStoneMageArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/bloom_stone_mage.png");
    }

    @Override
    public ResourceLocation getAnimationResource(BloomStoneMageArmorItem bloomStoneMageArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
