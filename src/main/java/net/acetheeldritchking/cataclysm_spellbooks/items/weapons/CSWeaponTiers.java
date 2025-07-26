package net.acetheeldritchking.cataclysm_spellbooks.items.weapons;

import com.github.L_Ender.cataclysm.init.ModItems;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public enum CSWeaponTiers implements Tier {
    // Monstrous Flamberge (Flamberge + Monstrous horn)
    MONSTROUS_FLAMBERGE(3, 1561, -2.7f, 10, 15, () -> Ingredient.of(Items.NETHERITE_SCRAP)),
    // Spellstealer (Khopesh + Spellbreaker)
    SPELLSTEALER(3, 1000, -2.6f, 5, 15, () -> Ingredient.of(ModItems.ANCIENT_METAL_INGOT.get())),
    // Lacerator (Incinerator + Tidal Claw)
    // Apparition Maw (Cursed Bow + Tidal Claw)
    // Murasama
    MURASAMA(3, 1681, -1.3f, 14.5F, 15, () -> Ingredient.of(ModItems.WITHERITE_INGOT.get())),
    ;

    private final int level;
    private final int uses;
    private final float speed;
    private final float damage;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    CSWeaponTiers(int level, int uses, float speed, float damage, int enchantmentValue, Supplier<Ingredient> repairIngredient)
    {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.damage = damage;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return this.uses;
    }

    @Override
    public float getSpeed() {
        return this.speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return this.damage;
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    @Override
    public int getEnchantmentValue() {
        return this.enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}
