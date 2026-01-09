package net.acetheeldritchking.cataclysm_spellbooks.spells.nature;

import com.github.L_Ender.cataclysm.entity.projectile.Sandstorm_Projectile;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

@AutoSpellConfig
public class DesertWindsSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "desert_winds");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(8)
            .build();

    public DesertWindsSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 2;
        this.spellPowerPerLevel = 2;
        this.castTime = 0;
        this.baseManaCost = 20;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Le sandstorm
        summonSandstormProjectile(spellLevel, entity);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void summonSandstormProjectile(int spellLevel, LivingEntity caster)
    {
        Level level = caster.level();

        double casterX = caster.getX();
        double casterEyeHeight = CSUtils.getEyeHeight(caster);
        double casterZ = caster.getZ();

        float casterYRot = caster.getYRot();
        float casterXRot = caster.getXRot();

        float d1 = -Mth.sin(casterYRot * ((float) Math.PI / 180.0F)) * Mth.cos(casterXRot * ((float) Math.PI / 180.0F));
        float d2 = -Mth.sin(casterXRot * ((float) Math.PI / 180.0F));
        float d3 = Mth.cos(casterYRot * ((float) Math.PI / 180.0F)) * Mth.cos(casterXRot * ((float) Math.PI / 180.0F));

        double theta = casterYRot * (Math.PI / 180.0F);
        theta += Math.PI / 2;

        double vecX = Math.cos(theta);
        double vexZ = Math.sin(theta);

        double x = casterX + vecX;
        double z = casterZ + vexZ;

        Sandstorm_Projectile sandstormProjectile = new Sandstorm_Projectile(caster, d1, d2, d3, level,getDamage(spellLevel, caster));
        sandstormProjectile.setState(1);
        sandstormProjectile.setPos(x, casterEyeHeight, z);
        level.addFreshEntity(sandstormProjectile);
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 2;
    }
}
