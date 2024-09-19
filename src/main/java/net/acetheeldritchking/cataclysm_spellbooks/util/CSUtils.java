package net.acetheeldritchking.cataclysm_spellbooks.util;

import net.minecraft.world.entity.LivingEntity;

public class CSUtils {
    // Get caster eye height, pretty much what it says
    public static double getEyeHeight(LivingEntity entity)
    {
        return entity.getY() + entity.getEyeHeight() - 0.2;
    }
}
