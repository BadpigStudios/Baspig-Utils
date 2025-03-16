package baspig.apis.utils.events.block;

import baspig.apis.utils.events.item.ItemEvents;
import baspig.apis.utils.register.tags.ExtraItemTags;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**This class gives you some easy config block events
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class BlockEvents {
    static Random random = new Random();

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * */
    public static void onBlockDestroyed(@NotNull Block block,@NotNull Item item, TagKey<Item> toolTag){

        blockDropSettings.put(block, new AdvancedDropSettings(item, toolTag, 100, 1, false, 1));
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     */
    public static void onBlockDestroyed(Block block, Item item, TagKey<Item> toolTag, float rate){
        blockDropSettings.put(block, new AdvancedDropSettings(item, toolTag, rate, 1, false, 1));
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will dropv
     */
    public static void onBlockDestroyed(Block block, Item item, TagKey<Item> toolTag, float rate, int amount){

        blockDropSettings.put(block, new AdvancedDropSettings(item, toolTag, rate, amount, false, amount));
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     * @param randomDrops make the block drop a random amount
     */
    public static void onBlockDestroyed(Block block, Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops){

        blockDropSettings.put(block, new AdvancedDropSettings(item, toolTag, rate, amount, randomDrops, amount));
    }

    /**
     * @param block Is a block that will drop an in item on break
     * @param item Is an item that the block will drop on break
     * @param rate Chance to drop the item
     * @param amount How many items the block will drop
     * @param randomDrops make the block drop a random amount
     * @param minDrop Minimum amount of items that will be dropped
     */
    public static void onBlockDestroyed(Block block, Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops, int minDrop){
        blockDropSettings.put(block, new AdvancedDropSettings(item, toolTag, rate, amount, randomDrops, minDrop));
    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     */
    private static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag,  float rate){

    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     */
    private static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity){

    }

    /**
     * @param dropsInCreative The item can be dropped in creative mode?
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     * @param quantity How many items the block will drop
     * @param randomDrops make the block drop a random quantity of max in quantity
     */
    private static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity, boolean randomDrops){

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
    private static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, TagKey<Item> toolTag, float rate, int quantity, boolean randomDrops, int minDrop){

    }

    /// ----------------------------------------------------------------------------------------------------------------
    /**
     * @param world It's the world that will be executing this method
     * @param pos Is the block position
     * @param isRaining Check if raining at block
     * @param skyVisible Check if sky visible for the block
     * @param biomeHasPrecipitation Check if actual biome has precipitation
     * @return It returns true if its raining, the sky is visible and if the biome has precipitation
     */
    public static boolean isRaining(ServerWorld world, BlockPos pos, boolean isRaining, boolean skyVisible, boolean biomeHasPrecipitation) {
        return world.isRaining() == isRaining &&
                world.isSkyVisible(pos) == skyVisible &&
                world.getBiome(pos).value().hasPrecipitation() == biomeHasPrecipitation;
    }

    /**
     * @param world It's the world that will be executing this method
     * @param pos Is the block position
     * @return It returns true if it's raining, the sky is visible and if the biome has precipitation
     */
    public static boolean isRaining(ServerWorld world, BlockPos pos) {
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
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     */
    public static void explode(ServerWorld world, BlockPos pos, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir) {
        if(replaceSourceBlockWithAir){
            world.setBlockState(pos, Blocks.AIR.getDefaultState());}
        world.createExplosion(null, pos.getX() + 0.5f, pos.getY() + 0.4f, pos.getZ() + 0.5f, explosionPower, true, World.ExplosionSourceType.TNT);
    }

    /**
     * @param world Current world that the block will explode
     * @param pos Explosion position, block position
     * @param explosionPower Power of the explosion
     * @param fire If true the explosion will add fire around
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(ServerWorld world, BlockPos pos, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir, World.ExplosionSourceType sourceType) {
        if(replaceSourceBlockWithAir){
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
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     */
    public static void explode(ServerWorld world, double x, double y, double z, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir) {
        if(replaceSourceBlockWithAir){
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
     * @param replaceSourceBlockWithAir If true the explosion will replace the source block with air
     * @param sourceType Type of source that the explosion will use
     */
    public static void explode(ServerWorld world, double x, double y, double z, float explosionPower, boolean fire, boolean replaceSourceBlockWithAir, World.ExplosionSourceType sourceType) {
        if(replaceSourceBlockWithAir){
            world.setBlockState(new BlockPos((int) x,(int) y,(int) z), Blocks.AIR.getDefaultState());}
        world.createExplosion(null, x + 0.5f, y + 0.4f, z + 0.5f, explosionPower, true, sourceType);
    }

    /// ----------------------------------------------------------------------------------------------------------------
    private static final Map<Block, BlockEvents.AdvancedDropSettings> blockDropSettings = new HashMap<>();

    /**
     * !!DO NOT USE, THIS MAY CAUSE YOUR MOD AND OTHER TO MALFUNCTION!!
     */
    public static void DestroyEvent() {
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            Block block = blockState.getBlock();

            if (blockDropSettings.containsKey(block)) {
                AdvancedDropSettings settings = blockDropSettings.get(block);
                if (random.nextFloat() * 100 < settings.rate) {

                    if(playerEntity.getMainHandStack().isIn(settings.toolTag) || settings.toolTag == ExtraItemTags.NO_TOOL_LEVEL){

                        int extra = (settings.quantity > settings.minDrop) ? random.nextInt(settings.quantity - settings.minDrop) : 0;
                        int dropAmount = settings.randomDrops ? settings.minDrop + extra : settings.minDrop;

                        for (int i = 0; i < dropAmount; i++) {
                            ItemEvents.createWorldItem(world, blockPos, settings.item.getDefaultStack());
                        }
                    }
                }
            }
        });
    }

    private static class AdvancedDropSettings {
        Item item;
        TagKey<Item> toolTag;
        float rate;
        int quantity;
        boolean randomDrops;
        int minDrop;

        AdvancedDropSettings(Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops, int minDrop) {
            this.item = item;
            this.toolTag = toolTag;
            this.rate = rate;
            this.quantity = amount;
            this.randomDrops = randomDrops;
            this.minDrop = minDrop;
        }
    }
    /// ----------------------------------------------------------------------------------------------------------------
}
