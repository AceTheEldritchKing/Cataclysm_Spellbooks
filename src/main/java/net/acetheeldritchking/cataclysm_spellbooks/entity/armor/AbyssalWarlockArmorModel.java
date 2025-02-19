package net.acetheeldritchking.cataclysm_spellbooks.entity.armor;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.AbyssalWarlockArmorItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class AbyssalWarlockArmorModel extends GeoModel<AbyssalWarlockArmorItem> {
    @Override
    public ResourceLocation getModelResource(AbyssalWarlockArmorItem abyssalWarlockArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/abyssal_warlock_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(AbyssalWarlockArmorItem abyssalWarlockArmorItem) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/models/armor/abyssal_warlock_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(AbyssalWarlockArmorItem abyssalWarlockArmorItem) {
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
