package baspig.apis.utils.client.render.draw_entity;

import baspig.apis.utils.util.BPString;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.World;
import org.joml.Vector3f;

public class DrawEntity extends Entity {

    private static final TrackedData<String> VECTORS_STRING = DataTracker.registerData(DrawEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<String> FIXED_VECTORS_STRING = DataTracker.registerData(DrawEntity.class, TrackedDataHandlerRegistry.STRING);

    public DrawEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(VECTORS_STRING, "");
        builder.add(FIXED_VECTORS_STRING, "");
    }

    public void setStringData(Vector3f first_vertex, Vector3f second_vertex, Vector3f third_vertex, Vector3f fourth_vertex, byte alpha, Direction direction, Identifier identifier) {
        String data = BPString.packetObjectDataToString(';',
                BPString.packVector3fToString(':',first_vertex),
                BPString.packVector3fToString(':',second_vertex),
                BPString.packVector3fToString(':',third_vertex),
                BPString.packVector3fToString(':',fourth_vertex),
                alpha
        );

        data = data.concat( ";" + identifier.toString()).concat(";" + direction.toString().toUpperCase());

        this.dataTracker.set(VECTORS_STRING, data);
    }

    public String getStringData(){
        return this.dataTracker.get(VECTORS_STRING);
    }

    public String getFixedStringData(){
        return this.dataTracker.get(FIXED_VECTORS_STRING);
    }

    @Override
    public String getUuidAsString() {
        return super.getUuidAsString();
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("baspig_utils:data", this.dataTracker.get(VECTORS_STRING));
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if(nbt.getString("baspig_utils:data").isPresent()){
            this.dataTracker.set(VECTORS_STRING, nbt.getString("baspig_utils:data").get());
        }
    }
    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    @Override
    public Vec2f getRotationClient() {
        return super.getRotationClient();
    }

    @Override
    public void setPosition(double x, double y, double z) {
        super.setPosition(getBlockX(), getBlockY(), getBlockZ());
    }
}
