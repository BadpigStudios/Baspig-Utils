package baspig.apis.utils.events.block;

import baspig.apis.utils.events.item.ItemEvents;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

@SuppressWarnings("unused")
public class BlockEvents {
    static Random random = new Random();

    /**
     * @param block A list of blocks that will drop an in item on break
     * @param item A list of items that the block will drop on break
     * */
    public static void onBlockDestroyed(@NotNull Block block,@NotNull Item item){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == block){
                ItemEvents.createWorldItem(world, blockPos, item.getDefaultStack());
            }
        });
    }

    /**
     * @param blocks A list of blocks that will drop an in item on break
     * @param items A list of items that the block will drop on break
     * @param rate Chance to drop the item
     */
    public static void onBlockDestroyed(Block blocks, Item items, float rate){
        assert blocks != null;
        assert items != null;
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == blocks){
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
    public static void onBlockDestroyed(Block blocks, Item items, float rate, int quantity){
        assert blocks != null;
        assert items != null;
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == blocks){
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
    public static void onBlockDestroyed(Block blocks, Item items, float rate, int quantity, boolean randomDrops){
        assert blocks != null;
        assert items != null;
        int randomDrop = quantity / random.nextInt(1, quantity);
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(blockState.getBlock() == blocks){
                if(random.nextFloat(0, 1) * 100 < rate && randomDrops){
                    for(int i = 0; i <= randomDrop; i++){
                        ItemEvents.createWorldItem(world, blockPos, items.getDefaultStack());
                    }
                }else {
                    onBlockDestroyed(blocks, items, rate, quantity);
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
    public static void onBlockDestroyed(Block blocks, Item items, float rate, int quantity, boolean randomDrops, int minDrop){
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
                        onBlockDestroyed(blocks, items, rate);
                    }
                }else {
                    onBlockDestroyed(blocks, items, rate);
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
    public static void onBlockDestroyedAdvanced(boolean dropsInCreative, Block blocks, Item items, float rate){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate);
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
    public static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, float rate, int quantity){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate, quantity);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate, quantity);
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
    public static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, float rate, int quantity, boolean randomDrops){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate, quantity ,randomDrops);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate, quantity, randomDrops);
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
    public static void onBlockDestroyed(boolean dropsInCreative, Block blocks, Item items, float rate, int quantity, boolean randomDrops, int minDrop){
        PlayerBlockBreakEvents.AFTER.register((world, playerEntity, blockPos, blockState, blockEntity) -> {
            if(playerEntity.isInCreativeMode() && dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate, quantity ,randomDrops, minDrop);
            }
            if(!playerEntity.isInCreativeMode() && !dropsInCreative){
                BlockEvents.onBlockDestroyed(blocks, items, rate, quantity, randomDrops, minDrop);
            }
        });
    }
}
