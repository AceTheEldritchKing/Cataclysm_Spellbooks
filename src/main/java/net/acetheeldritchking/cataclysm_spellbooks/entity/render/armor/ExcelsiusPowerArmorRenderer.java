package net.acetheeldritchking.cataclysm_spellbooks.entity.render.armor;

import mod.azure.azurelib.render.armor.AzArmorRenderer;
import mod.azure.azurelib.render.armor.AzArmorRendererConfig;
import mod.azure.azurelib.render.layer.AzAutoGlowingLayer;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.items.armor.animators.ExcelsiusPowerAnimator;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ExcelsiusPowerArmorRenderer extends AzArmorRenderer {
    private static final ResourceLocation GEO = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "geo/excelsius_attack.geo.json"
    );

    private static final ResourceLocation TEX = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "textures/models/armor/excelsius_spell_power.png"
    );

    private static final ResourceLocation TEX_CHARGED = new ResourceLocation(
            CataclysmSpellbooks.MOD_ID,
            "textures/models/armor/excelsius_spell_power_overcharged.png"
    );

    public ExcelsiusPowerArmorRenderer() {
        super(
                AzArmorRendererConfig.builder(GEO, TEX)
                        .setAnimatorProvider(ExcelsiusPowerAnimator::new)
                        .addRenderLayer(new AzAutoGlowingLayer<>())
                        .setPipelineContext(AzArmorLeggingTorsoLayerPipeline::new)
                        .build()
        );
    }
}
