package baspig.apis.utils.register.easifier;

import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

/**
 * This is a faster way to Register and create blocks.
 *
 * @Author Baspig_
 */
public class BPuRegister {

    /// Items------------------------------------ ->

    public static RegistryKey<Item> createItemKey(String modId, String name) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(modId, name));
    }

    // Builder class for fluent item registration
    public static class ItemBuilder {
        private final String name;
        private final String modId;
        private Item.Settings itemSettings;

        private ItemBuilder(String modId, String name) {
            this.name = name;
            this.itemSettings = new Item.Settings();
            this.modId = modId;
        }

        public ItemBuilder settings(Item.Settings settings) {
            this.itemSettings = settings;
            return this;
        }

        public ItemBuilder maxCount(int maxCount) {
            this.itemSettings = this.itemSettings.maxCount(maxCount);
            return this;
        }

        public ItemBuilder fireproof() {
            this.itemSettings = this.itemSettings.fireproof();
            return this;
        }

        public ItemBuilder maxDamage(int maxDamage) {
            this.itemSettings = this.itemSettings.maxDamage(maxDamage);
            return this;
        }

        public ItemBuilder recipeRemainder(Item recipeRemainder) {
            this.itemSettings = this.itemSettings.recipeRemainder(recipeRemainder);
            return this;
        }

        public <T extends Item> T register(ItemFactory<T> factory) {
            RegistryKey<Item> key = createItemKey(modId,name);
            // Apply registry key to item settings
            itemSettings = itemSettings.registryKey(key);
            T item = factory.create(itemSettings);
            return Registry.register(Registries.ITEM, key, item);
        }

        public Item register() {
            return register(Item::new);
        }
    }

    @FunctionalInterface
    public interface ItemFactory<T extends Item> {
        T create(Item.Settings settings);
    }

    public static ItemBuilder item(String modId, String name) {
        return new ItemBuilder(modId,name);
    }

    public static <T extends Item> T register(String modId, String name, T item) {
        return Registry.register(Registries.ITEM, Identifier.of(modId, name), item);
    }
}
