package net.acetheeldritchking.cataclysm_spellbooks.loot;

import com.mojang.serialization.Codec;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class CSLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CataclysmSpellbooks.MOD_ID);

    public static final RegistryObject<Codec<? extends IGlobalLootModifier>> ADD_TO_LOOT =
            LOOT_MODIFIER_SERIALIZERS.register("add_to_table", AddToLootTableModifier.CODEC);

    public static void register(IEventBus eventBus)
    {
        LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }
}
