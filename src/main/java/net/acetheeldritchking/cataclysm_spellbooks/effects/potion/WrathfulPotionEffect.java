package net.acetheeldritchking.cataclysm_spellbooks.effects.potion;

import com.github.L_Ender.cataclysm.init.ModParticle;
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

public class WrathfulPotionEffect extends MagicMobEffect {
    public WrathfulPotionEffect() {
        super(MobEffectCategory.BENEFICIAL, 4583645);
        this.addAttributeModifier(Attributes.ATTACK_DAMAGE, "f3ebfd1c-ae19-4802-aa8e-e5cd04ad197b", WrathfulPotionEffect.ATTACK_DAMAGE_PER_WRATH, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    public static final float ATTACK_DAMAGE_PER_WRATH = 0.20f;

    @Override
    public void removeAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void addAttributeModifiers(LivingEntity pLivingEntity, AttributeMap pAttributeMap, int pAmplifier) {
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide)
        {
            ServerLevel world = (ServerLevel) pLivingEntity.level;

            world.sendParticles(ModParticle.PHANTOM_WING_FLAME.get(), pLivingEntity.getX(), pLivingEntity.getY(0.5), pLivingEntity.getZ(),
                    25,
                    world.random.nextGaussian() * 0.007D,
                    world.random.nextGaussian() * 0.007D,
                    world.random.nextGaussian() * 0.007D,
                    0.05D);
        }
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return pDuration % 20 == 0;
    }

    // I'm solving my jank by adding this
    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
        return ret;
    }
}
