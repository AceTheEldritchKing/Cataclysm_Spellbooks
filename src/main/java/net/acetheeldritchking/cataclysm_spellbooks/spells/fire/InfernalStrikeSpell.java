package net.acetheeldritchking.cataclysm_spellbooks.spells.fire;

import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.AutoSpellConfig;
import io.redspace.ironsspellbooks.api.spells.CastSource;
import io.redspace.ironsspellbooks.api.spells.CastType;
import io.redspace.ironsspellbooks.api.spells.SpellRarity;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.damage.SpellDamageSource;
import io.redspace.ironsspellbooks.registries.SoundRegistry;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.infernal_blade.InfernalBladeProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.Optional;

@AutoSpellConfig
public class InfernalStrikeSpell extends AbstractIgnisSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "infernal_strike");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage",
                Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.cataclsym_spellboks.incinerator_damage",
                        Utils.stringTruncation(getBonusDamage(spellLevel, caster), 2)));
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.EPIC)
            .setSchoolResource(SchoolRegistry.FIRE_RESOURCE)
            .setMaxLevel(8)
            .setCooldownSeconds(20)
            .build();

    public InfernalStrikeSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 2;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
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
        return CastType.INSTANT;
    }

    @Override
    public Optional<SoundEvent> getCastFinishSound() {
        return Optional.of(SoundRegistry.FLAMING_STRIKE_SWING.get());
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        InfernalBladeProjectile infernalBlade = new InfernalBladeProjectile(level, entity);
        infernalBlade.setPos(entity.position().add(0, entity.getEyeHeight() - infernalBlade.getBoundingBox().getYsize() * .5f, 0));
        infernalBlade.shootFromRotation(entity, entity.getXRot(), entity.getYHeadRot(), 0, 1, 1);
        // Damage if Incinerator is in main hand
        Item incinerator = ModItems.THE_INCINERATOR.get();

        final float MAX_HEALTH = entity.getMaxHealth();
        float baseHealth = entity.getHealth();
        double percent = (baseHealth/MAX_HEALTH) * 100;

        // Eval the damage if soul or not
        float damage = infernalBlade.getIsSoul() ? getDamage(spellLevel, entity) * 1.5F : getDamage(spellLevel, entity);
        float bonusDamage = infernalBlade.getIsSoul() ? getBonusDamage(spellLevel, entity) * 1.5F : getBonusDamage(spellLevel, entity);

        if (entity.getMainHandItem().is(incinerator))
        {
            infernalBlade.setDamage(bonusDamage);
        }
        else
        {
            infernalBlade.setDamage(damage);
        }

        if (percent <= 50)
        {
            infernalBlade.setIsSoul(true);
        } else {
            infernalBlade.setIsSoul(false);
        }

        level.addFreshEntity(infernalBlade);
        //System.out.println("Damage: " + infernalBlade.getDamage());

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public SpellDamageSource getDamageSource(Entity projectile, Entity attacker) {
        return super.getDamageSource(projectile, attacker);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 1.5f;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster)
    {
        float baseDamage = getDamage(spellLevel, caster);
        int bonusAmount = (int) (1.5 + spellLevel);

        return baseDamage + bonusAmount;
    }
}
