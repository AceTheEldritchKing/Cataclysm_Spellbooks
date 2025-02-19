package net.acetheeldritchking.cataclysm_spellbooks.items.spellbooks;

import io.redspace.ironsspellbooks.IronsSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;

public class CodexOfMaliceSpellBookModel extends GeoModel<CodexOfMaliceSpellBook> {
    @Override
    public ResourceLocation getModelResource(CodexOfMaliceSpellBook codexOfMaliceSpellBook) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "geo/codex_of_malice.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(CodexOfMaliceSpellBook codexOfMaliceSpellBook) {
        return new ResourceLocation(CataclysmSpellbooks.MOD_ID, "textures/item/spell_books/codex_of_malice_spell_book_model.png");
    }

    @Override
    public ResourceLocation getAnimationResource(CodexOfMaliceSpellBook codexOfMaliceSpellBook) {
        // yes, I'm returning this file because idk what else to return for anims
        return new ResourceLocation(IronsSpellbooks.MODID, "animations/wizard_armor_animation.json");
    }
}
