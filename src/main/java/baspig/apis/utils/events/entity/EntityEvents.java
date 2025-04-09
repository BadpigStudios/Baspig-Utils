package baspig.apis.utils.events.entity;

import baspig.apis.utils.ModID;
import baspig.apis.utils.events.item.ItemEvents;
import baspig.apis.utils.util.BP;
import baspig.apis.utils.util.ConsoleColors;
import baspig.apis.utils.util.GeneralUtils;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.entity.*;
import net.minecraft.item.Item;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * @author Baspig_
 */
@SuppressWarnings("unused")
@ApiStatus.NonExtendable
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

    private final String modId; //The mod id into a final String

    /**
     * Setups the instances for all Map required events.
     *
     * @param modId The mod id of the mod that uses the vents and creates the instance.
     */
    public EntityEvents(String modId, String referenceId){
        /// Creation of a secure events creation to avoid overwrite other mods events.
        this.modId = modId + ":" + referenceId;
    }

    public enum Mode{
        AdIfAbsent,
        Replace
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entityToAdd The entity that will be generated
     */
    public void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entityToAdd){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.AddEntityOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.AddEntityOnDeath(entityToAdd, 100, 1, false, 1));

        EntitySettingsClasses.GeneralAddEntityOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entityToAdd The entity that will be generated
     * @param rate Is the probability of the entity of being added
     */
    public void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entityToAdd, float rate){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.AddEntityOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.AddEntityOnDeath(entityToAdd, rate, 1, false, 1));

        EntitySettingsClasses.GeneralAddEntityOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entityToAdd The entity that will be generated
     * @param rate Is the probability of the entity of being added
     * @param amount Is the amount of entities to add
     */
    public void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entityToAdd, float rate, int amount){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.AddEntityOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.AddEntityOnDeath(entityToAdd, rate, amount, false, 1));

        EntitySettingsClasses.GeneralAddEntityOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entityToAdd The entity that will be generated
     * @param rate Is the probability of the entity of being added
     * @param amount Is the amount of entities to add
     * @param randomDrops makes the block randomly add the entities between 0 and the given amount value.
     */
    public void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entityToAdd, float rate, int amount, boolean randomDrops){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.AddEntityOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.AddEntityOnDeath(entityToAdd, rate, amount, randomDrops, 1));

        EntitySettingsClasses.GeneralAddEntityOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will generate the other entity
     * @param entityToAdd The entity that will be generated
     * @param rate Is the probability of the entity of being added
     * @param amount Is the amount of entities to add
     * @param randomDrops makes the block randomly add the entities between the minDrop and amount value.
     * @param minDrop Minimum amount of items that will be dropped
     */
    public void spawnEntityOnDeath(@NotNull EntityType<?> source,@NotNull EntityType<?> entityToAdd, float rate, int amount, boolean randomDrops, int minDrop){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.AddEntityOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.AddEntityOnDeath(entityToAdd, rate, amount, randomDrops, minDrop));

        EntitySettingsClasses.GeneralAddEntityOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will drop the item
     * @param item The item that will be dropped
     */
    public void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> source, Item item){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.DropItemOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.DropItemOnDeathSettings(item, 100, 1, false, 1));

        EntitySettingsClasses.GeneralDropItemOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     */
    public void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> source, Item item, float rate){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.DropItemOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.DropItemOnDeathSettings(item, rate, 1, false, 1));

        EntitySettingsClasses.GeneralDropItemOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     */
    public void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> source, Item item, float rate, int amount){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.DropItemOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.DropItemOnDeathSettings(item, rate, amount, false, 1));

        EntitySettingsClasses.GeneralDropItemOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     * @param randomDrops make the block drop a random amount
     */
    public void spawnItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> source, Item item, float rate, int amount, boolean randomDrops){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.DropItemOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.DropItemOnDeathSettings(item, rate, amount, randomDrops, 1));

        EntitySettingsClasses.GeneralDropItemOnDeathMap.put(modId, updateMap);
    }

    /**
     * @param source The entity that will drop the item
     * @param item The item that will be dropped
     * @param rate Is the probability of the item of being dropped
     * @param amount Is the amount of items that will be dropped
     * @param randomDrops make the block drop a random amount
     * @param minDrop Minimum amount of items that will be dropped
     */
    public void dropItemOnEntityDeath(@NotNull EntityType<? extends LivingEntity> source, Item item, float rate, int amount, boolean randomDrops,  int minDrop){
        Map<EntityType<?>, Object> updateMap = EntitySettingsClasses.DropItemOnDeathMap;

        updateMap.put(source, new EntitySettingsClasses.DropItemOnDeathSettings(item, rate, amount, randomDrops, minDrop));

        EntitySettingsClasses.GeneralDropItemOnDeathMap.put(modId, updateMap);
    }

    /// ----------------------------------------------------------------------------------------------------------------

    /**
     * !!DO NOT USE, THIS MAY CAUSE YOUR MOD AND OTHERS TO MALFUNCTION!!
     */
    public static void RegisterEntityEvents(ModID id) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stackTrace.length > 2) {
            String clasWhoCallThis = stackTrace[2].getClassName();
            if(!clasWhoCallThis.equals("baspig.apis.utils.Baspig_utils") && !Objects.equals(id.toString(), "baspig_utils")){
                String r = ConsoleColors.RESET;
                String red = ConsoleColors.RED;
                String yellow = ConsoleColors.YELLOW;
                String lightBlue = ConsoleColors.BLUE_BRIGHT;

                BP.LOG.warn("Other mod is trying to access and register/modify Easy events. It's being called by class: {}{}", yellow, clasWhoCallThis);
            }
        }

        ServerLivingEntityEvents.AFTER_DEATH.register((livingEntity, damageSource) -> {
            if (!(livingEntity.getWorld() instanceof ServerWorld serverWorld)) return;
            EntityType<?> deadEntityType = livingEntity.getType();
            BlockPos blockPos = livingEntity.getBlockPos();

            for(String modIdKeys : EntitySettingsClasses.GeneralAddEntityOnDeathMap.keySet()){
                if(modIdKeys == null) return;
                Map<EntityType<?>, Object> innerMap = EntitySettingsClasses.GeneralAddEntityOnDeathMap.get(modIdKeys);

                /// Search for each settings type entry
                for(Map.Entry<EntityType<?>, Object> entry : innerMap.entrySet()){
                    if(entry == null) return;
                    /// Process all the data
                    if(deadEntityType == entry.getKey() && entry.getValue() instanceof EntitySettingsClasses.AddEntityOnDeath settings){
                        //Here is called to make easier future improvements
                        addEntityOnDeathFunction(settings, serverWorld, blockPos);
                    }
                }
            }

            for(String modIdKeys : EntitySettingsClasses.GeneralDropItemOnDeathMap.keySet()){
                if(modIdKeys == null) return;
                Map<EntityType<?>, Object> innerMap = EntitySettingsClasses.GeneralDropItemOnDeathMap.get(modIdKeys);

                /// Search for each settings type entry
                for(Map.Entry<EntityType<?>, Object> entry : innerMap.entrySet()){
                    if(entry == null) return;
                    /// Process all the data
                    if(deadEntityType == entry.getKey() && entry.getValue() instanceof EntitySettingsClasses.DropItemOnDeathSettings settings){
                        dropItemOnDeathFunction(settings, serverWorld, blockPos);
                    }
                }
            }
        });
    }

    private static int dropMather(EntitySettingsClasses.AddEntityOnDeath settings){
        if(settings.minDrop < settings.amount){
            int extra = random.nextInt(settings.amount - settings.minDrop);
            return settings.randomDrops ? settings.minDrop + extra : settings.minDrop;
        }
        return settings.minDrop;
    }

    private static int dropMather(EntitySettingsClasses.DropItemOnDeathSettings settings){
        if(settings.minDrop < settings.amount){
            int extra = random.nextInt(settings.amount - settings.minDrop);
            return settings.randomDrops ? settings.minDrop + extra : settings.minDrop;
        }
        return settings.minDrop;
    }

    ///
    /// Here start the "easier to edit" code.
    ///

    private static void addEntityOnDeathFunction(EntitySettingsClasses.AddEntityOnDeath settings, ServerWorld serverWorld, BlockPos blockPos){
        if (GeneralUtils.probability(settings.rate)) {
            int dropAmount = dropMather(settings);

            for (int i = 0; i < dropAmount; i++) {
                generate(serverWorld, blockPos, settings.entityType);
            }
        }
    }

    private static void dropItemOnDeathFunction(EntitySettingsClasses.DropItemOnDeathSettings settings, ServerWorld serverWorld, BlockPos blockPos){
        if (GeneralUtils.probability(settings.rate)) {
            int dropAmount = dropMather(settings);

            for (int i = 0; i < dropAmount; i++) {
                ItemEvents.createWorldItem(serverWorld, blockPos, settings.item.getDefaultStack());
            }
        }
    }
}
