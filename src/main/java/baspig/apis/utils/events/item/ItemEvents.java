package baspig.apis.utils.events.item;

import baspig.apis.utils.util.BP;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

/**
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class ItemEvents {


    /**
     * @param world Actual world where this method is called
     * @param pos Position of the item creation
     * @param itemStack The item that will be created
     */
    public static void createWorldItem(@NotNull World world, BlockPos pos,@NotNull ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            world.spawnEntity(new ItemEntity(
                    world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    itemStack
            ));
        }else {
            BP.LOG.info("A exception occurred at: createWorldItem \n" +
                    "ItemStack is empty."
            );
        }
    }

    /**
     * It will remove only 1 item.
     *
     * @param player The player who will remove the item
     * @param hand It will remove item in hand
     */
    public static void removeItemInHand(@NotNull PlayerEntity player, Hand hand) {
        if (!player.getWorld().isClient) {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isEmpty()) {
                stack.decrement(1);
                player.setStackInHand(hand, stack);
            }
        }
    }

    /**
     * @param player The player who will remove the item
     * @param hand The actual player hand, the selected item
     * @param quantity Amount of items that will remove from the hand
     */
    public static void removeItemInHand(@NotNull PlayerEntity player, Hand hand, byte quantity) {
        if (!player.getWorld().isClient) {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isEmpty()) {
                stack.decrement(quantity);
                player.setStackInHand(hand, stack);
            }
        }
    }
}
