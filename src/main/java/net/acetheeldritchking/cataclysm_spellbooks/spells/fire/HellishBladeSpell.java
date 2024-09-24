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
import io.redspace.ironsspellbooks.capabilities.magic.TargetEntityCastData;
import io.redspace.ironsspellbooks.damage.ISpellDamageSource;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.entity.spells.hellish_blade.HellishBladeProjectile;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class HellishBladeSpell extends AbstractIgnisSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "hellish_blade");

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
            .setMaxLevel(5)
            .setCooldownSeconds(60)
            .build();

    public HellishBladeSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 15;
        this.spellPowerPerLevel = 2;
        this.castTime = 15;
        this.baseManaCost = 50;
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
    public boolean checkPreCastConditions(Level level, int spellLevel, LivingEntity entity, MagicData playerMagicData) {
        return Utils.preCastTargetHelper(level, entity, playerMagicData, this, 32, .15f);
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (playerMagicData.getAdditionalCastData() instanceof TargetEntityCastData targetEntityCastData)
        {
            var targetEntity = targetEntityCastData.getTarget((ServerLevel) level);
            if (targetEntity != null)
            {
                double targetEye = targetEntity.getEyeHeight();

                float damage = getDamage(spellLevel, entity);
                float bonusDamage = getBonusDamage(spellLevel, entity);

                // Damage if Incinerator is in main hand
                Item incinerator = ModItems.THE_INCINERATOR.get();

                Vec3 center = targetEntity.position().add(0, targetEye / 2, 0);
                Vec3 spawn = center.add(0, 10, 0);
                Vec3 motion = center.subtract(spawn).normalize();

                HellishBladeProjectile hellishBlade = new HellishBladeProjectile(level, entity);

                hellishBlade.moveTo(spawn);
                hellishBlade.shoot(motion);
                if (entity.getMainHandItem().is(incinerator))
                {
                    hellishBlade.setDamage(bonusDamage);
                }
                else
                {
                    hellishBlade.setDamage(damage);
                }

                level.addFreshEntity(hellishBlade);
            }
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public DamageSource getDamageSource(@Nullable Entity projectile, @Nullable Entity attacker) {
        return ((ISpellDamageSource) super.getDamageSource(projectile, attacker)).get();
    }

    private float getDamage(int spellLevel, LivingEntity caster)
    {
        return getSpellPower(spellLevel, caster) * 0.6f;
    }

    private float getBonusDamage(int spellLevel, LivingEntity caster)
    {
        float baseDamage = getDamage(spellLevel, caster);
        int bonusAmount = (int) (2.5 + spellLevel);

        return baseDamage + bonusAmount;
    }
}
