package net.acetheeldritchking.cataclysm_spellbooks.spells.nature;

import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import io.redspace.ironsspellbooks.api.config.DefaultConfig;
import io.redspace.ironsspellbooks.api.magic.MagicData;
import io.redspace.ironsspellbooks.api.registry.SchoolRegistry;
import io.redspace.ironsspellbooks.api.spells.*;
import io.redspace.ironsspellbooks.api.util.Utils;
import io.redspace.ironsspellbooks.capabilities.magic.RecastInstance;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.acetheeldritchking.cataclysm_spellbooks.registries.ItemRegistries;
import net.acetheeldritchking.cataclysm_spellbooks.util.CSUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AutoSpellConfig
public class AmethystPunctureSpell extends AbstractSpell {
    private final ResourceLocation spellId = ResourceLocation.fromNamespaceAndPath(CataclysmSpellbooks.MOD_ID, "amethyst_puncture");

    @Override
    public List<MutableComponent> getUniqueInfo(int spellLevel, LivingEntity caster) {
        return List.of(
                Component.translatable("ui.cataclysm_spellbooks.amethyst_speed",
                        Utils.stringTruncation(getAmethystClusterSpeed(spellLevel, getSpellPower(spellLevel, caster)), 1)),
                Component.translatable("ui.irons_spellbooks.damage", Utils.stringTruncation(getDamageForProjectileSpeed(spellLevel, 1, getSpellPower(spellLevel, caster))/5, 1))

        );
    }

    private final DefaultConfig defaultConfig = new DefaultConfig()
            .setMinRarity(SpellRarity.UNCOMMON)
            .setSchoolResource(SchoolRegistry.NATURE_RESOURCE)
            .setMaxLevel(5)
            .setCooldownSeconds(5)
            .build();

    public AmethystPunctureSpell()
    {
        this.manaCostPerLevel = 5;
        this.baseSpellPower = 1;
        this.spellPowerPerLevel = 1;
        this.castTime = 0;
        this.baseManaCost = 15;
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
    public int getRecastCount(int spellLevel, @Nullable LivingEntity entity) {
        //assert entity != null;
        if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.BLOOM_STONE_PAULDRONS.get()) && entity.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistries.BLOOM_STONE_STAFF.get()))
        {
            //System.out.println("Bonus for chest & staff: " + 1 + spellLevel);
            return  1 + spellLevel;
        }
        else if (entity.getItemBySlot(EquipmentSlot.CHEST).is(ModItems.BLOOM_STONE_PAULDRONS.get()) || entity.getItemBySlot(EquipmentSlot.MAINHAND).is(ItemRegistries.BLOOM_STONE_STAFF.get()))
        {
            //System.out.println("Bonus for chest or staff: " + spellLevel);
            return spellLevel;
        }
        else
        {
            //System.out.println("No bonus: " + spellLevel);
            return 2;
        }
    }

    @Override
    public void onCast(Level level, int spellLevel, LivingEntity entity, CastSource castSource, MagicData playerMagicData) {
        // Recasts
        if (!playerMagicData.getPlayerRecasts().hasRecastForSpell(getSpellId())) {
            playerMagicData.getPlayerRecasts().addRecast(new RecastInstance(getSpellId(), spellLevel, getRecastCount(spellLevel, entity), 100, castSource, null), playerMagicData);
        }

        // Shoot da crystal
        shootAmethystCluster(entity, spellLevel, level);

        super.onCast(level, spellLevel, entity, castSource, playerMagicData);
    }

    private void shootAmethystCluster(LivingEntity caster, int spellLevel, Level level)
    {
        double casterX = caster.getX();
        double casterY = CSUtils.getEyeHeight(caster);
        double casterZ = caster.getZ();

        float speed = 0.2F;

        Amethyst_Cluster_Projectile_Entity amethyst = new Amethyst_Cluster_Projectile_Entity(ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), level, caster,
                getDamageForProjectileSpeed(spellLevel, speed, getSpellPower(spellLevel, caster)));

        amethyst.moveTo(casterX, casterY, casterZ, 0, caster.getXRot());
        float speedSpellPower = getAmethystClusterSpeed(speed, getSpellPower(spellLevel, caster));
        amethyst.setNoGravity(true);
        amethyst.shootFromRotation(caster, caster.getXRot(), caster.getYHeadRot(), 0, speedSpellPower, 1.0F);

        level.addFreshEntity(amethyst);
    }

    private float getAmethystClusterSpeed(float speed, float spellPower)
    {
        return speed * spellPower;
    }

    private float getDamageForProjectileSpeed(int spellLevel, float speed, float spellPower)
    {
        float totalSpeed = getAmethystClusterSpeed(speed, spellPower);
        float damage = spellLevel * totalSpeed;

        //System.out.println("Damage: " + damage);
        return damage;
    }
}
