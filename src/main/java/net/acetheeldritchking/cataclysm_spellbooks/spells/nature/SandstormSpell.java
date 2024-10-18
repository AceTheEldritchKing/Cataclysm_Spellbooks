package net.acetheeldritchking.cataclysm_spellbooks.spells.nature;

import com.github.L_Ender.cataclysm.entity.effect.Sandstorm_Entity;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.util.IExtendedCataclysmProjectileInterface;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.List;

@AutoSpellConfig
public class SandstormSpell extends AbstractSpell {
    private final ResourceLocation spellId = new ResourceLocation(CataclysmSpellbooks.MOD_ID, "sandstorm");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.sandstorm.lifespan",
                        getLifespan(spellLevel, caster)),
                Component.translatable("ui.cataclysm_spellbooks.sandstorm_amount",
                        spellLevel)
        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.RARE)
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMaxLevel(3)
            .setCooldownSeconds(30)
            .build();

    public SandstormSpell()
    {
        this.manaCostPerLevel = 10;
        this.baseSpellPower = 2;
        this.spellPowerPerLevel = 2;
        this.castTime = 15;
        this.baseManaCost = 60;
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
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Hot wind blowing, jagged lines across the sand!
        summonSandstormAroundCaster(spellLevel, level, entity);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void summonSandstormAroundCaster(int spellLevel, Level level, LivingEntity caster)
    {
        for (int i = 0; i < spellLevel; i++)
        {
            double casterX = caster.getX();
            double casterY = caster.getY();
            double casterZ = caster.getZ();

            float angle = (float) (i * Math.PI / 1.5F);
            double stormX = casterX + (Mth.cos(angle) * 4.0F);
            double stormZ = casterZ + (Mth.sin(angle) * 4.0F);

            Sandstorm_Entity sandstorm = new Sandstorm_Entity(level, stormX, casterY, stormZ, (int) getLifespan(spellLevel, caster), angle, caster.getUUID());

            ((IExtendedCataclysmProjectileInterface)sandstorm).setFromSpell(true);

            level.addFreshEntity(sandstorm);
        }
    }

    private float getLifespan(int spellLevel, LivingEntity caster)
    {
        //System.out.println("Lifespan: " + getSpellPower(spellLevel, caster) * 15);
        return getSpellPower(spellLevel, caster) * 15;
    }
}
