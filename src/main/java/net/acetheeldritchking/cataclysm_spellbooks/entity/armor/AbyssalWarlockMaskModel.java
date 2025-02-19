package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.AbyssalWarlockMaskItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AbyssalWarlockMaskModel extends GeoModel<AbyssalWarlockMaskItem> {
    @Override
    public ResourceLocation getModelResource(AbyssalWarlockMaskItem abyssalWarlockArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/abyssal_warlock_mask.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AbyssalWarlockMaskItem abyssalWarlockArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/abyssal_warlock_mask.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AbyssalWarlockMaskItem abyssalWarlockArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
