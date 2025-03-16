package baspig.apis.utils.events.entity;

import baspig.apis.utils.events.item.ItemEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class EntityEvents {
    static Random random = new Random();

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> entity, Item item){
        entityDropSettings.put(entity, new EntityEvents.AdvancedDropSettings(item, 100, 1, false, 1));
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> entity, Item item, float rate){
        entityDropSettings.put(entity, new EntityEvents.AdvancedDropSettings(item, rate, 1, false, 1));
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> entity, Item item, float rate, int amount){
        entityDropSettings.put(entity, new EntityEvents.AdvancedDropSettings(item, rate, amount, false, 1));
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     * @param randomDrops make the block drop a random amount
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> entity, Item item, float rate, int amount, boolean randomDrops){
        entityDropSettings.put(entity, new EntityEvents.AdvancedDropSettings(item, rate, amount, randomDrops, 1));
    }

    /**
     * @param entity The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     * @param randomDrops make the block drop a random amount
     * @param minDrop Minimum amount of items that will be dropped
     */
    public static void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> entity, Item item, float rate, int amount, boolean randomDrops,  int minDrop){
        entityDropSettings.put(entity, new EntityEvents.AdvancedDropSettings(item, rate, amount, randomDrops, 1));
    }


    /// ----------------------------------------------------------------------------------------------------------------
    private static final Map<EntityType<?>, EntityEvents.AdvancedDropSettings> entityDropSettings = new HashMap<>();

    /**
     * !!DO NOT USE, THIS MAY CAUSE YOUR MOD AND OTHERS TO MALFUNCTION!!
     */
    private static void KillEvent() {
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {

            EntityType<?> entity = livingEntity.getType();

            if (entityDropSettings.containsKey(entity)) {
                EntityEvents.AdvancedDropSettings settings = entityDropSettings.get(entity);
                if(random.nextFloat(0,100) < settings.rate){
                    if(settings.minDrop < settings.amount){

                        int extra = random.nextInt(settings.amount - settings.minDrop);
                        int dropAmount = settings.randomDrops ? settings.minDrop + extra : settings.minDrop;

                        for (int i = 0; i < dropAmount; i++) {
                            ItemEvents.createWorldItem(livingEntity.getWorld(), livingEntity.getBlockPos(), settings.item.getDefaultStack());
                        }
                    }
                }
            }
        });
    }

    private static class AdvancedDropSettings {
        Item item;
        float rate;
        int amount;
        boolean randomDrops;
        int minDrop;

        AdvancedDropSettings(@NotNull Item item, float rate, int amount, boolean randomDrops, int minDrop) {
            this.item = item;
            this.rate = rate;
            this.amount = amount;
            this.randomDrops = randomDrops;
            this.minDrop = minDrop;
        }
    }

    public static void BlockEventsRegistry(){
        KillEvent();
    }
}
