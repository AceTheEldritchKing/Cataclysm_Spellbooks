package net.acetheeldritchking.cataclysm_spellbooks.capabilities.pharaohs_wrath;


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

public class PlayerKingWrathProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerKingWrath> PLAYER_KINGS_WRATH =
            CapabilityManager.get(new CapabilityToken<PlayerKingWrath>() {
                // No need to override anything
            });

    private PlayerKingWrath wrath = null;
    private final LazyOptional<PlayerKingWrath> optional = LazyOptional.of(this::createPlayerKingsWrath);

    private PlayerKingWrath createPlayerKingsWrath()
    {
        if (this.wrath == null)
        {
            this.wrath = new PlayerKingWrath();
        }

        return this.wrath;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_KINGS_WRATH)
        {
            return optional.cast();
        }

        return LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();
        createPlayerKingsWrath().saveNBTdata(nbt);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createPlayerKingsWrath().loadNBTdata(nbt);
    }
}
