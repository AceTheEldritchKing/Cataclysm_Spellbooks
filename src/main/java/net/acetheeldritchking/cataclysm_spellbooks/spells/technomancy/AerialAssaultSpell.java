package net.acetheeldritchking.cataclysm_spellbooks.spells.technomancy;

import com.github.L_Ender.cataclysm.entity.projectile.Wither_Missile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.entity.spells.target_area.TargetedAreaEntity;
import io.redspace.ironsspellbooks.spells.TargetAreaCastData;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.CSSchoolRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class AerialAssaultSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "aerial_assault");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamage(spellLevel, caster), 2)),
                Component.translatable("ui.irons_spellbooks.radius", Utils.stringTruncation(getRadius(caster), 1))
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.COMMON)
            .setSchoolResource(CSSchoolRegistry.TECHNOMANCY_RESOURCE)
            .setMaxLevel(10)
            .setCooldownSeconds(25)
            .build();

    public AerialAssaultSpell()
    {
        this.manaCostPerLevel = 1;
        this.baseSpellPower = 10;
        this.spellPowerPerLevel = 2;
        this.castTime = 160;
        this.baseManaCost = 5;
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
        return CastType.CONTINUOUS;
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        if (!(playerMagicData.getAdditionalCastData() instanceof TargetAreaCastData))
        {
            Vec3 targetArea = Utils.moveToRelativeGroundLevel(level, Utils.raycastForEntity(level, entity, 40, true).getLocation(), 12);
            playerMagicData.setAdditionalCastData(new TargetAreaCastData(targetArea, TargetedAreaEntity.createTargetAreaEntity(level, targetArea, getRadius(entity), 0xfa1033)));
        }

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    @Override
    public void onServerCastTick(Level level, int spellLevel, LivingEntity entity, @Nullable MagicData playerMagicData) {
        if (playerMagicData != null && (playerMagicData.getCastDurationRemaining() + 1) % 4 == 0)
        {
            if (playerMagicData.getAdditionalCastData() instanceof TargetAreaCastData targetAreaCastData)
            {
                for (int i = 0; i < 2; i++)
                {
                    Vec3 center = targetAreaCastData.getCenter();
                    float radius = getRadius(entity);
                    Vec3 spawn = center.add(new Vec3(0, entity.getY(), entity.getRandom().nextFloat() * radius).yRot(entity.getRandom().nextInt(360)));

                    shootMissiles(level, entity, spellLevel, spawn);
                }
            }
        }

        super.onServerCastTick(level, spellLevel, entity, playerMagicData);
    }

    public void shootMissiles(Level level, LivingEntity caster, int spellLevel, Vec3 spawn)
    {
        Vec3 offset = Utils.getRandomVec3(1).normalize().scale(0.075F);

        float d = caster.getXRot();

        float d2 = -Mth.sin(d * ((float) Math.PI / 180F));

        Wither_Missile_Entity missile = new Wither_Missile_Entity(ModEntities.WITHER_MISSILE.get(), caster, spawn.add(offset).x, spawn.add(offset).y,  spawn.add(offset).z, 0, d2, 0, getDamage(spellLevel, caster), level);

        level.addFreshEntity(missile);
    }

    private float getDamage(int spellLevel, LivingEntity entity)
    {
        return getSpellPower(spellLevel, entity) * 0.9f;
    }

    private float getRadius(LivingEntity caster)
    {
        return 6;
    }
}
