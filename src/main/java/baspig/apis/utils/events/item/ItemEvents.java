package baspig.apis.utils.events.item;

import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@SuppressWarnings("unused")
public class ItemEvents {
    public static void createWorldItem(World world, BlockPos pos, ItemStack itemStack) {
        if (!itemStack.isEmpty()) {
            world.spawnEntity(new ItemEntity(
                    world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                    itemStack
            ));
        }
    }

    public static void removeItem(PlayerEntity player, Hand hand) {
        if (!player.getWorld().isClient) {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isEmpty()) {
                stack.decrement(1);
                player.setStackInHand(hand, stack);
            }
        }
    }

    public static void removeItem(PlayerEntity player, Hand hand, byte quantity) {
        if (!player.getWorld().isClient) {
            ItemStack stack = player.getStackInHand(hand);
            if (!stack.isEmpty()) {
                stack.decrement(quantity);
                player.setStackInHand(hand, stack);
            }
        }
    }
}
