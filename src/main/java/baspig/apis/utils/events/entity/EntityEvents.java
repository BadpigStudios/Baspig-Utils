package baspig.apis.utils.events.entity;

import baspig.apis.utils.events.item.ItemEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EntityEvents {
    static Random random = new Random();

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType entity, Item item){
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if(livingEntity.getType() == entity){
                ItemEvents.createWorldItem(livingEntity.getWorld(), livingEntity.getBlockPos(), item.getDefaultStack());
            }
        });
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType entity, Item item, float rate){
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if(livingEntity.getType() == entity){
                if(random.nextFloat(0,100) < rate){
                    ItemEvents.createWorldItem(livingEntity.getWorld(), livingEntity.getBlockPos(), item.getDefaultStack());
                }
            }
        });
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param quantity Is the amount of items that will be dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType entity, Item item, float rate, int quantity){
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if(livingEntity.getType() == entity){
                if(random.nextFloat(0,100) < rate){
                    for(int i = 0; i < quantity; i++){
                        ItemEvents.createWorldItem(livingEntity.getWorld(), livingEntity.getBlockPos(), item.getDefaultStack());
                    }
                }
            }
        });
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param quantity Is the amount of items that will be dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType entity, Item item, float rate, int quantity, boolean randomDrops){
        int randomDrop = quantity / random.nextInt(1, quantity);
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if(livingEntity.getType() == entity){
                if(random.nextFloat(0,100) < rate){
                    for(int i = 0; i < randomDrop; i++){
                        ItemEvents.createWorldItem(livingEntity.getWorld(), livingEntity.getBlockPos(), item.getDefaultStack());
                    }
                } else {
                    EntityEvents.spawnItemOnEntityDeath(entity, item, rate, quantity);
                }
            }
        });
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param quantity Is the amount of items that will be dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType entity, Item item, float rate, int quantity,boolean randomDrops,  int minDrop){
        int extra_drops = quantity - minDrop;
        int extra = random.nextInt(0, extra_drops);
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if(livingEntity.getType() == entity){
                if(random.nextFloat(0,100) < rate){
                    if(minDrop <= quantity){
                        for(int i = 0; i <= minDrop; i++){
                            ItemEvents.createWorldItem(livingEntity.getWorld(), livingEntity.getBlockPos(), item.getDefaultStack());
                        }

                        for(int i = 0; i < extra; i++){
                            ItemEvents.createWorldItem(livingEntity.getWorld(), livingEntity.getBlockPos(), item.getDefaultStack());
                        }
                    } else {
                        EntityEvents.spawnItemOnEntityDeath(entity, item, rate, quantity);
                    }
                }
            }
        });
    }
}
