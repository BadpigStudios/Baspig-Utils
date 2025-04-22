package baspig.apis.utils.mixin.entity;

import baspig.apis.utils.util.PlayerDrawData;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements PlayerDrawData {

    @Unique
    private NbtCompound drawData = new NbtCompound();

    @SuppressWarnings("all")
    @Override
    public void setDrawData(NbtCompound data) {
        this.drawData = data;
    }

    @SuppressWarnings("all")
    @Override
    public NbtCompound getDrawData() {
        return this.drawData;
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeDrawData(NbtCompound nbt, CallbackInfo ci) {
        nbt.put("baspig_utils:draw_data", this.drawData);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readDrawData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("baspig_utils:draw_data")) {
            this.drawData = nbt.getCompoundOrEmpty("baspig_utils:draw_data");
        }
    }
}
