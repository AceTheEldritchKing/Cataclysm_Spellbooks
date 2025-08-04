package net.acetheeldritchking.cataclysm_spellbooks.capabilities.murasama_combo;


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

public class PlayerMurasamaComboProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static Capability<PlayerMurasamaCombo> PLAYER_MURASAMA_COMBO =
            CapabilityManager.get(new CapabilityToken<PlayerMurasamaCombo>() {
                // No need to override anything
            });

    private PlayerMurasamaCombo combo = null;
    private final LazyOptional<PlayerMurasamaCombo> optional = LazyOptional.of(this::createPlayerKingsWrath);

    private PlayerMurasamaCombo createPlayerKingsWrath()
    {
        if (this.combo == null)
        {
            this.combo = new PlayerMurasamaCombo();
        }

        return this.combo;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == PLAYER_MURASAMA_COMBO)
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
