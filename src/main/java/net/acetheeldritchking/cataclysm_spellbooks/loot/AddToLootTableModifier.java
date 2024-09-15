package net.acetheeldritchking.cataclysm_spellbooks.loot;

import com.google.common.base.Suppliers;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class AddToLootTableModifier extends LootModifier {
    public static final Supplier<Codec<AddToLootTableModifier>> CODEC = Suppliers.memoize(()
            -> RecordCodecBuilder.create(inst -> codecStart(inst).
            and(ResourceLocation.CODEC.fieldOf("loot_table").
                    forGetter(m -> m.lootTable)).apply(inst, AddToLootTableModifier::new)));

    public ResourceLocation lootTable;

    /**
     * Constructs a LootModifier.
     *
     * @param conditionsIn the ILootConditions that need to be matched before the loot is modified.
     */
    protected AddToLootTableModifier(LootItemCondition[] conditionsIn, ResourceLocation lootTable)
    {
        super(conditionsIn);
        this.lootTable = lootTable;
    }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context)
    {
        //TODO is this line correct?
        LootTable table = LootTable.lootTable().setRandomSequence(lootTable).build();

        table.getRandomItems(context, generatedLoot::add);

        return generatedLoot;
    }

    @Override
    public Codec<? extends IGlobalLootModifier> codec()
    {
        return CODEC.get();
    }
}
