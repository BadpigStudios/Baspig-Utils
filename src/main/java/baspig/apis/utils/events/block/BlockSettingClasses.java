package baspig.apis.utils.events.block;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * This class manages all {@link BlockEvents} settings
 * @Author Baspig_
 */
class BlockSettingClasses {

    protected static final Map<String, Map<Block, Object>> GeneralExplodeOnBreakHashMap = new HashMap<>();

    protected static final Map<Block, Object> ExplodeOnBreakHashMap = new HashMap<>();
    protected static class ExplodeOnBreakSettings {
        TagKey<Item> toolTag;
        float rate;
        float power;
        boolean fireSpread;

        protected ExplodeOnBreakSettings(float power, TagKey<Item> toolTag, float rate, boolean fireSpread) {
            this.toolTag = toolTag;
            this.rate = rate;
            this.power = power;
            this.fireSpread = fireSpread;
        }
    }

    protected static final Map<String, Map<Block, Object>> GeneralSpawnEntityOnBreakHashMap = new HashMap<>();

    protected static final Map<Block, Object> SpawnEntityOnBreakHashMap = new HashMap<>();
    protected static class AddEntityOnBreakSettings {
        EntityType<?> entityType;
        TagKey<Item> toolTag;
        float rate;
        int quantity;

        protected AddEntityOnBreakSettings(EntityType<?> entityType, TagKey<Item> toolTag, float rate, int amount) {
            this.entityType = entityType;
            this.toolTag = toolTag;
            this.rate = rate;
            this.quantity = amount;

        }
    }

    protected static final Map<String, Map<Block, Object>> GeneralDropItemOnBreakMap = new HashMap<>();

    protected static final Map<Block, Object> DropItemOnBreakMap = new HashMap<>();
    protected static class DropItemObBreakSettings {
        Item item;
        TagKey<Item> toolTag;
        float rate;
        int quantity;
        boolean randomDrops;
        int minDrop;
        boolean dropsOnCreative;

        protected DropItemObBreakSettings(Item item, TagKey<Item> toolTag, float rate, int amount, boolean randomDrops, int minDrop, boolean dropsOnCreative) {
            this.item = item;
            this.toolTag = toolTag;
            this.rate = rate;
            this.quantity = amount;
            this.randomDrops = randomDrops;
            this.minDrop = minDrop;
            this.dropsOnCreative = dropsOnCreative;
        }
    }

    protected static final Map<String, Map<Block, Object>> GeneralPlaySoundOnBreakMap = new HashMap<>();

    protected static final Map<Block, Object> PlaySoundOnBreakMap = new HashMap<>();
    protected static class PlaySoundOnBreakSettings {
        SoundEvent soundEvent;
        SoundCategory soundCategory;
        TagKey<Item> toolTag;
        float rate;
        float volume;

        protected PlaySoundOnBreakSettings(SoundEvent soundEvent, SoundCategory soundCategory, TagKey<Item> toolTag, float rate, float volume) {
            this.soundEvent = soundEvent;
            this.soundCategory = soundCategory;
            this.toolTag = toolTag;
            this.rate = rate;
            this.volume = volume;
        }
    }
}
