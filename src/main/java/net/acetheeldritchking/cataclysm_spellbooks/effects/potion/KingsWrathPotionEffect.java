package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import com.github.L_Ender.cataclysm.client.particle.StormParticle;
import com.github.L_Ender.cataclysm.init.ModParticle;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import io.redspace.ironsspellbooks.capabilities.magic.MagicManager;
import io.redspace.ironsspellbooks.effect.MagicMobEffect;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KingsWrathPotionEffect extends MagicMobEffect {
    public KingsWrathPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xf2a723);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "1c218a5a-33c6-463d-9f05-66a62b6d532f", KingsWrathPotionEffect.ATTACK_DAMAGE_PER_WRATH, AttributeModifier.Operation.MULTIPLY_TOTAL);
        this.addAttributeModifier(AttributeRegistry.SPELL_POWER.get(), "399b0201-0036-4ee7-98cd-6559a9c9adae", KingsWrathPotionEffect.SPELL_POWER_PER_WRATH, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float ATTACK_DAMAGE_PER_WRATH = 0.20f;
    public static final float SPELL_POWER_PER_WRATH = 0.05f;

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int pAmplifier) {
        if (!entity.level().isClientSide)
        {
            ServerLevel world = (ServerLevel) entity.level();

            float random = 0.04F;

            float r = 0.89F + entity.getRandom().nextFloat() * random;
            float g = 0.85F + entity.getRandom().nextFloat() * random;
            float b = 0.69F + entity.getRandom().nextFloat() * random * 1.5F;

            MagicManager.spawnParticles(world, new StormParticle.OrbData(r, g, b, 2.75F + entity.getRandom().nextFloat() * 0.6F, 3.75F + entity.getRandom().nextFloat() * 0.6F, entity.getId()), entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);
            MagicManager.spawnParticles(world, new StormParticle.OrbData(r, g, b, 2.5F + entity.getRandom().nextFloat() * 0.45F, 3.0F + entity.getRandom().nextFloat() * 0.45F, entity.getId()), entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);
            MagicManager.spawnParticles(world, new StormParticle.OrbData(r, g, b, 2.25F + entity.getRandom().nextFloat() * 0.45F, 2.25F + entity.getRandom().nextFloat() * 0.45F, entity.getId()), entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);
            MagicManager.spawnParticles(world, new StormParticle.OrbData(r, g, b, 1.25F + entity.getRandom().nextFloat() * 0.45F, 1.25F + entity.getRandom().nextFloat() * 0.45F, entity.getId()), entity.getX(), entity.getY(), entity.getZ(), 1, 0, 0, 0, 1, true);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 5 == 0;
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }
}
