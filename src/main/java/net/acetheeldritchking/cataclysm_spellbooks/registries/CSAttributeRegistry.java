package net.acetheeldritchking.cataclysm_spellbooks.registries;

import io.redspace.ironsspellbooks.api.attribute.MagicRangedAttribute;
import net.acetheeldritchking.cataclysm_spellbooks.CataclysmSpellbooks;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = CataclysmSpellbooks.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CSAttributeRegistry {
    private static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, CataclysmSpellbooks.MOD_ID);

    // Abyssal Magic
    public static final RegistryObject<Attribute> ABYSSAL_MAGIC_RESIST = registerResistanceAttribute("abyssal");
    public static final RegistryObject<Attribute> ABYSSAL_MAGIC_POWER = registerPowerAttribute("abyssal");

    // Technomancy
    public static final RegistryObject<Attribute> TECHNOMANCY_MAGIC_RESIST = registerResistanceAttribute("technomancy");
    public static final RegistryObject<Attribute> TECHNOMANCY_MAGIC_POWER = registerPowerAttribute("technomancy");


    @SubscribeEvent
    public static void modifyEntityAttributes(EntityAttributeModificationEvent event)
    {
        /*event.getTypes().forEach(entity -> {
            event.add(entity, ABYSSAL_MAGIC_RESIST.get());
            event.add(entity, ABYSSAL_MAGIC_POWER.get());
            event.add(entity, TECHNOMANCY_MAGIC_RESIST.get());
            event.add(entity, TECHNOMANCY_MAGIC_POWER.get());
        });*/

        event.getTypes().forEach(entity -> ATTRIBUTES.getEntries().forEach(attribute -> event.add(entity, attribute.get())));
    }

    // ;_;
    private static RegistryObject<Attribute> registerResistanceAttribute(String id)
    {
        return ATTRIBUTES.register(id + "_magic_resist", () ->
                (new MagicRangedAttribute("attribute.cataclysm_spellbooks." + id + "_magic_resist",
                        1.0D, 0, 10).setSyncable(true)));
    }

    private static RegistryObject<Attribute> registerPowerAttribute(String id)
    {
        return ATTRIBUTES.register(id + "_spell_power", () ->
                (new MagicRangedAttribute("attribute.cataclysm_spellbooks." + id + "_spell_power",
                        1.0D, 0, 10).setSyncable(true)));
    }

    public static void register(IEventBus eventBus)
    {
        ATTRIBUTES.register(eventBus);
    }
}
