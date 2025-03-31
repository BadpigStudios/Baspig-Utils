package baspig.apis.utils.advanced.audio;

import baspig.apis.utils.util.DimensionData;
import baspig.apis.utils.util.Dimension;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class SourceObjectLoader {

    private static final Map<BlockPos, SourceObjectData> sourceObjects = new HashMap<>();

    protected static void initiate(){
        ServerTickEvents.START_WORLD_TICK.register(world -> {


            for (Map.Entry<BlockPos, SourceObjectData> entry : sourceObjects.entrySet()) {
                BlockPos pos = entry.getKey();
                SourceObjectData data = entry.getValue();

                if (DimensionData.checkIf(data.getDimension(), world) && world.isPosLoaded(pos)) {
                    if (data.getSoundEvent() != null) {

                        if(data.getCurrentTick() > data.getMaxTicks()){

                            world.playSound(null, data.getPosition(), data.getSoundEvent(), data.getSoundCategory(), 1, 1);
                            data.currentTick = 0;
                        }else {
                            data.currentTick++;
                        }
                    }
                }
            }
        });
    }

    protected static void addSourceObject(BlockPos position, Dimension dimension,
                                          SoundEvent soundEvents, SoundCategory category,
                                          int maxTicks, boolean initialized) {

        sourceObjects.put(position, new SourceObjectData(dimension, position,
                soundEvents, category, maxTicks, initialized));
    }


    protected static class SourceObjectData {

        private final Dimension dimension;
        private final BlockPos position;
        private final SoundEvent soundEvent;
        private final SoundCategory soundCategory;
        private final boolean repeatable;
        private final int maxTicks;
        private int currentTick;

        protected SourceObjectData (Dimension dimension, BlockPos position,
                                 SoundEvent soundEvents, SoundCategory category, int maxTicks,
                                 boolean repeatable){
            this.dimension = dimension;
            this.position = position;
            this.soundEvent = soundEvents;
            this.soundCategory = category;
            this.repeatable = repeatable;
            this.maxTicks = maxTicks;
            this.currentTick = maxTicks;
        }

        protected Dimension getDimension(){
            return dimension;
        }

        protected BlockPos getPosition(){
            return position;
        }

        protected SoundEvent getSoundEvent(){
            return soundEvent;
        }

        protected SoundCategory getSoundCategory() {
            return soundCategory;
        }

        protected boolean isRepeatable(){
            return repeatable;
        }

        protected int getMaxTicks(){
            return maxTicks;
        }

        protected int getCurrentTick(){
            return currentTick;
        }
    }
}