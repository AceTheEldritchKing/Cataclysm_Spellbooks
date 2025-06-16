package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.AnimationHolder;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.parting_shot.PartingShotProjectile;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.spells.CSSpellAnimations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class PartingShotSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "parting_shot");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.LEGENDARY)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(1)
            .setCooldownSeconds(120)
            .build();

    public PartingShotSpell()
    {
        this.manaCostPerLevel = 0;
        this.baseSpellPower = 25;
        this.spellPowerPerLevel = 0;
        this.castTime = 35;
        this.baseManaCost = 150;
    }

    @Override
    public boolean canBeCraftedBy(Player player) {
        return false;
    }

    @Override
    public boolean allowLooting() {
        return false;
    }

    @Override
    public boolean allowCrafting() {
        return false;
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
        return CSSpellAnimations.ANIMATION_CHARGE_GUN_FULL;
    }

    @Override
    public AnimationHolder getCastFinishAnimation() {
        return AnimationHolder.pass();
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        PartingShotProjectile partingShot1 = new PartingShotProjectile(level, entity);
        PartingShotProjectile partingShot2 = new PartingShotProjectile(level, entity);

        partingShot1.setPos(entity.position().add(0, (entity.getEyeHeight() + 0.5) - partingShot1.getBoundingBox().getYsize() * .5f, 0));
        partingShot1.shootFromRotation(entity, entity.getXRot(), entity.getYHeadRot(), 0, 1, 1);
        partingShot1.setExplosionRadius(6);

        partingShot2.setPos(entity.position().add(0, (entity.getEyeHeight() - 0.5) - partingShot2.getBoundingBox().getYsize() * .5f, 0));
        partingShot2.shootFromRotation(entity, entity.getXRot(), entity.getYHeadRot(), 0, 1, 1);
        partingShot2.setExplosionRadius(6);

        float damage = getDamage(spellLevel, entity);

        partingShot1.setDamage(damage);
        //partingShot2.setDamage(damage);

        level.addFreshEntity(partingShot1);
        level.addFreshEntity(partingShot2);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 2.0f;
    }
}
