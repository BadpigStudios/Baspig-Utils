package baspig.apis.utils.events.entity;

import baspig.apis.utils.ModID;
import baspig.apis.utils.events.item.ItemEvents;
import baspig.apis.utils.util.BP;
import baspig.apis.utils.util.ConsoleColors;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class EntityEvents {
    static Random random = new Random();

    /**
     * @param world Actual world where this method is called
     * @param entity The entity that will be generated
     */
    public static void generate(@NotNull ServerWorld world, BlockPos pos, @NotNull EntityType<?> entity) {

        Entity summon = entity.create(world, SpawnReason.EVENT);
        assert summon != null;
        summon.refreshPositionAndAngles(pos, 0, 0);
        world.spawnEntity(summon);

    }


    /**
     * @param source The entity that will generate the other entity
     * @param entity The entity that will be generated
     */
    public static void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entity){
        entitySpawnSettings.put(entity, new EntityEvents.AdvancedSpawnSettings(source, entity,100, 1, false, 1));
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entity The entity that will be generated
     * @param rate Is the probability of the item of being dropped
     */
    public static void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entity, float rate){
        entitySpawnSettings.put(entity, new EntityEvents.AdvancedSpawnSettings(source, entity,rate, 1, false, 1));
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entity The entity that will be generated
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     */
    public static void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entity, float rate, int amount){
        entitySpawnSettings.put(entity, new EntityEvents.AdvancedSpawnSettings(source, entity,rate, amount, false, 1));
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entity The entity that will be generated
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     * @param randomDrops make the block drop a random amount
     */
    public static void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entity, float rate, int amount, boolean randomDrops){
        entitySpawnSettings.put(entity, new EntityEvents.AdvancedSpawnSettings(source, entity,rate, amount, randomDrops, 1));
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entity The entity that will be generated
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     * @param randomDrops make the block drop a random amount
     * @param minDrop Minimum amount of items that will be dropped
     */
    public static void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entity, float rate, int amount, boolean randomDrops, int minDrop){
        entitySpawnSettings.put(entity, new EntityEvents.AdvancedSpawnSettings(source, entity,rate, amount, randomDrops, minDrop));
    }

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
    /// ----------------------------------------------------------------------------------------------------------------



    /// ----------------------------------------------------------------------------------------------------------------
    private static final Map<EntityType<?>, AdvancedSpawnSettings> entitySpawnSettings = new HashMap<>();

    /**
     * !!DO NOT USE, THIS MAY CAUSE YOUR MOD AND OTHERS TO MALFUNCTION!!
     */
    private static void KillAndSpawnEvent() {
        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if (!(livingEntity.getWorld() instanceof ServerWorld serverWorld)) return;

            EntityType<?> deadEntityType = livingEntity.getType();

            for (AdvancedSpawnSettings settings : entitySpawnSettings.values()) {
                if (deadEntityType.equals(settings.sourceType)) {
                    if (random.nextFloat() * 100 < settings.rate) {
                        int dropAmount = settings.minDrop;

                        if (settings.randomDrops && settings.amount > settings.minDrop) {
                            dropAmount += random.nextInt(settings.amount - settings.minDrop + 1);
                        }

                        for (int i = 0; i < dropAmount; i++) {
                            generate(serverWorld, livingEntity.getBlockPos(), settings.entityType);
                        }
                    }
                }
            }
        });
    }

    private static class AdvancedSpawnSettings {
        final EntityType<?> sourceType;
        final EntityType<?> entityType;
        final float rate;
        final int amount;
        final boolean randomDrops;
        final int minDrop;

        AdvancedSpawnSettings(@NotNull EntityType<?> source, @NotNull EntityType<?> entity, float rate, int amount, boolean randomDrops, int minDrop) {
            this.sourceType = source;
            this.entityType = entity;
            this.rate = rate;
            this.amount = amount;
            this.randomDrops = randomDrops;
            this.minDrop = minDrop;
        }
    }

    /**
     * !!DO NOT USE, THIS MAY CAUSE YOUR MOD AND OTHERS TO MALFUNCTION!!
     */
    public static void EntityEventsRegistry(ModID id){
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            String clasWhoCallThis = stackTrace[2].getClassName();
            if(!clasWhoCallThis.equals("baspig.apis.utils.Baspig_utils") && !Objects.equals(id.toString(), "baspig_utils")){
                String r = ConsoleColors.RESET;
                String red = ConsoleColors.RED;
                String yellow = ConsoleColors.YELLOW;
                String lightBlue = ConsoleColors.BLUE_BRIGHT;

                BP.fancyLog(   "BaspigUtils.EntityEvents."+yellow+"class"+r, lightBlue+"EntityEventsRegistry"+r,
                        "        If you see this message "+yellow+"twice"+r+", it is a " +red+ "problem" +r+
                                "\n        Other/s mod are calling it. \n It's being called by method: " +yellow+ clasWhoCallThis);
            }
        }
        KillEvent();
        KillAndSpawnEvent();
    }
}
