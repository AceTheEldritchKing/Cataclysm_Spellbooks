package net.acetheeldritchking.cataclysm_spellbooks.capabilities.wrath;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerWrathProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerWrath> PLAYER_WRATH =
            CapabilityManager.get(new CapabilityToken<PlayerWrath>() {
                // No need to override anything
            });

    private PlayerWrath wrath = null;
    private final LazyOptional<PlayerWrath> optional = LazyOptional.of(this::createPlayerWrath);

    private PlayerWrath createPlayerWrath()
    {
        if (this.wrath == null)
        {
            this.wrath = new PlayerWrath();
        }

        return this.wrath;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_WRATH)
        {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerWrath().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerWrath().loadNBTdata(nbt);
    }
}
