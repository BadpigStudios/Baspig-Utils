package baspig.apis.utils.events.block;

import baspig.apis.utils.events.item.ItemEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("unused")
public class BlockEvents {
    static Random random = new Random();

    /**
     * @param block A list of blocks that will drop an in item on break
     * @param item A list of items that the block will drop on break
     * */
    public static void onBlockDestroyed(@NotNull Block block,@NotNull Item item, TagKey<Item> toolTag){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == block && playerEntity.getMainHandStack().isIn(toolTag)){
                ItemEvents.createWorldItem(world, blockPos, item.getDefaultStack());
            }
        });
    }

    /**
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     */
    public static void onBlockDestroyed(Block blocks, Item items, TagKey<Item> toolTag, float rate){
        assert blocks != null;
        assert items != null;
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == blocks && playerEntity.getMainHandStack().isIn(toolTag)){
                if(random.nextFloat(0, 1) * 100  < rate){
                    ItemEvents.createWorldItem(world, blockPos, items.getDefaultStack());
                }
            }
        });
    }


    /**
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     */
    public static void onBlockDestroyed(Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity){
        assert blocks != null;
        assert items != null;
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == blocks && playerEntity.getMainHandStack().isIn(toolTag)){
                if(random.nextFloat(0, 1) * 100 < rate){
                    for(int i = 0; i <= quantity; i++){
                        ItemEvents.createWorldItem(world, blockPos, items.getDefaultStack());
                    }
                }
            }
        });
    }

    /**
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     * @param randomDrops make the block drop a random quantity of max in quantity
     */
    public static void onBlockDestroyed(Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity, boolean randomDrops){
        assert blocks != null;
        assert items != null;
        int randomDrop = quantity / random.nextInt(1, quantity);
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == blocks && playerEntity.getMainHandStack().isIn(toolTag)){
                if(random.nextFloat(0, 1) * 100 < rate && randomDrops){
                    for(int i = 0; i <= randomDrop; i++){
                        ItemEvents.createWorldItem(world, blockPos, items.getDefaultStack());
                    }
                }else {
                    onBlockDestroyed(blocks, items, toolTag, rate, quantity);
                }
            }
        });
    }

    /**
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     * @param randomDrops make the block drop a random quantity of max in quantity
     * @param minDrop Minimum amount of items that will be dropped
     */
    public static void onBlockDestroyed(Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity, boolean randomDrops, int minDrop){
        assert blocks != null;
        assert items != null;
        int extra_drops = quantity - minDrop;
        int extra = random.nextInt(0, extra_drops);
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == blocks){
                if(random.nextFloat(0, 1) * 100 < rate && randomDrops){
                    if(minDrop <= quantity){
                        for(int i = 0; i <= minDrop; i++){
                            ItemEvents.createWorldItem(world, blockPos, items.getDefaultStack());
                        }
                        for(int i = 0; i <= extra; i++){
                            ItemEvents.createWorldItem(world, blockPos, items.getDefaultStack());
                        }
                    } else {
                        onBlockDestroyed(blocks, items, toolTag, rate);
                    }
                }else {
                    onBlockDestroyed(blocks, items, toolTag, rate);
                }
            }
        });
    }



    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     */
    public static void onBlockDestroyedAdvanced(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag,  float rate){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate);
            }
        });
    }



    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     */
    public static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate, quantity);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate, quantity);
            }
        });
    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     * @param randomDrops make the block drop a random quantity of max in quantity
     */
    public static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity, boolean randomDrops){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate, quantity ,randomDrops);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate, quantity, randomDrops);
            }
        });
    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     * @param randomDrops make the block drop a random quantity of max in quantity
     * @param minDrop Minimum amount of items that will be dropped
     */
    public static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity, boolean randomDrops, int minDrop){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate, quantity ,randomDrops, minDrop);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, toolTag, rate, quantity, randomDrops, minDrop);
            }
        });
    }

    /**
     * @param world It's the world that will be executing this method
     * @param pos Is the block position
     * @param isRaining Check if raining at block
     * @param skyVisible Check if sky visible for the block
     * @param biomeHasPrecipitation Check if actual biome has precipitation
     * @return It returns true if it's raining, the sky is visible and if the biome has precipitation
     */
    public static boolean isRainingOnBlock(ServerWorld world, BlockPos pos, boolean isRaining, boolean skyVisible, boolean biomeHasPrecipitation) {
        return world.isRaining() == isRaining &&
                world.isSkyVisible(pos) == skyVisible &&
                world.getBiome(pos).value().hasPrecipitation() == biomeHasPrecipitation;
    }

    /**
     * @param world It's the world that will be executing this method
     * @param pos Is the block position
     * @return It returns true if it's raining, the sky is visible and if the biome has precipitation
     */
    public static boolean isRainingOnBlock(ServerWorld world, BlockPos pos) {
        return world.isRaining()
                && world.isSkyVisible(pos)
                && world.getBiome(pos).value().hasPrecipitation();
    }

    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     */
    public static void explode(ServerWorld world, BlockPos pos, float explosionPower, boolean fire) {
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, World.ExplosionSourceType.TNT);
    }

    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(ServerWorld world, BlockPos pos, float explosionPower, boolean fire, World.ExplosionSourceType sourceType) {
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, sourceType);
    }
    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceWithAir If true the explosion will replace the source block with air
     */
    public static void explode(ServerWorld world, BlockPos pos, float explosionPower, boolean fire, boolean replaceWithAir) {
        if(replaceWithAir){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());}
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, World.ExplosionSourceType.TNT);
    }

    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceWithAir If true the explosion will replace the source block with air
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(ServerWorld world, BlockPos pos, float explosionPower, boolean fire, boolean replaceWithAir, World.ExplosionSourceType sourceType) {
        if(replaceWithAir){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());}
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, sourceType);
    }

    /**
     * @param world Current world that the block will explode
     * @param x Explosion position, x position
     * @param y Explosion position, y position
     * @param z Explosion position, z position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceWithAir If true the explosion will replace the source block with air
     */
    public static void explode(ServerWorld world, double x, double y, double z, float explosionPower, boolean fire, boolean replaceWithAir) {
        if(replaceWithAir){
            world.setBlockState(new BlockPos((int) x,(int) y,(int) z), Blocks.AIR.getDefaultState());}
        world.createExplosion(null, x + 0.5f, y + 0.4f, z + 0.5f, explosionPower, true, World.ExplosionSourceType.TNT);
    }

    /**
     * @param world Current world that the block will explode
     * @param x Explosion position, x position
     * @param y Explosion position, y position
     * @param z Explosion position, z position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceWithAir If true the explosion will replace the source block with air
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(ServerWorld world, double x, double y, double z, float explosionPower, boolean fire, boolean replaceWithAir, World.ExplosionSourceType sourceType) {
        if(replaceWithAir){
            world.setBlockState(new BlockPos((int) x,(int) y,(int) z), Blocks.AIR.getDefaultState());}
        world.createExplosion(null, x + 0.5f, y + 0.4f, z + 0.5f, explosionPower, true, sourceType);
    }
}
