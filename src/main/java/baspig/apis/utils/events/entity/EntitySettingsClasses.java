package baspig.apis.utils.events.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

class EntitySettingsClasses {

    protected static final Map<String, Map<EntityType<?>, Object>> GeneralAddEntityOnDeathMap = new HashMap<>();

    protected static final Map<EntityType<?>, Object> AddEntityOnDeathMap = new HashMap<>();
    protected static class AddEntityOnDeath {
        EntityType<?> entityType;
        float rate;
        int amount;
        boolean randomDrops;
        protected final int minDrop;

        protected AddEntityOnDeath(@NotNull EntityType<?> entity, float rate, int amount, boolean randomDrops, int minDrop) {
            this.entityType = entity;
            this.rate = rate;
            this.amount = amount;
            this.randomDrops = randomDrops;
            this.minDrop = minDrop;
        }
    }

    protected static final Map<String, Map<EntityType<?>, Object>> GeneralDropItemOnDeathMap = new HashMap<>();

    protected static final Map<EntityType<?>, Object> DropItemOnDeathMap = new HashMap<>();
    protected static class DropItemOnDeathSettings {
        Item item;
        float rate;
        int amount;
        boolean randomDrops;
        int minDrop;

        protected DropItemOnDeathSettings(@NotNull Item item, float rate, int amount, boolean randomDrops, int minDrop) {
            this.item = item;
            this.rate = rate;
            this.amount = amount;
            this.randomDrops = randomDrops;
            this.minDrop = minDrop;
        }
    }
}
