package baspig.apis.utils.events.block;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages all {@link BlockEvents} settings
 * @Author Baspig_
 */
public class BlockSettingClasses {

    public static final Map<String, Map<Block, Object>> GeneralSettingsMap = new HashMap<>();

    public static final Map<Block, Object> ExplodeHashMap = new HashMap<>();
    public static class Explode{
        TagKey<Item> toolTag;
        float rate;
        float power;
        boolean fireSpread;

        public Explode(float power, TagKey<Item> toolTag, float rate, boolean fireSpread) {

            this.toolTag = toolTag;
            this.rate = rate;
            this.power = power;
            this.fireSpread = fireSpread;
        }
    }

    public static final Map<Block, Object> SpawnEntityHashMap = new HashMap<>();
    public static class SpawnEntity{
        EntityType<?> entityType;
        TagKey<Item> toolTag;
        float rate;
        int quantity;

        public SpawnEntity(EntityType<?> entityType, TagKey<Item> toolTag, float rate, int amount) {
            this.entityType = entityType;
            this.toolTag = toolTag;
            this.rate = rate;
            this.quantity = amount;

        }
    }

    public static final Map<Block, Object> DropItemMap = new HashMap<>();
    public static class DropSettings {
        Item item;
        TagKey<Item> toolTag;
        float rate;
        int quantity;
        boolean randomDrops;
        int minDrop;
        boolean dropsOnCreative;

        public DropSettings(Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops, int minDrop, boolean dropsOnCreative) {
            this.item = item;
            this.toolTag = toolTag;
            this.rate = rate;
            this.quantity = amount;
            this.randomDrops = randomDrops;
            this.minDrop = minDrop;
            this.dropsOnCreative = dropsOnCreative;
        }
    }
}
