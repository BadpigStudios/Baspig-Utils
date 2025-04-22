package baspig.apis.utils.util;

import net.minecraft.nbt.NbtCompound;

public interface PlayerDrawData {
    void setDrawData(NbtCompound data);
    NbtCompound getDrawData();
}
