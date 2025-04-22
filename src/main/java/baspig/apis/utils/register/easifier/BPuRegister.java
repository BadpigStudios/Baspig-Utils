package baspig.apis.utils.register.easifier;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * This is a faster way to register and create blocks.
 *
 * @Author Baspig_
 */
class BPuRegister {

    public static RegistryKey<Block> createBlockKey(String modId,String name) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(modId, name));
    }

    /**
     * Builder class for multiple block registration
     */
    public static class BlockBuilder {
        private final String name;
        private final String modId;
        private AbstractBlock.Settings blockSettings;
        private Item.Settings itemSettings = null;

        private BlockBuilder(String modId, String name) {
            this.modId = modId;
            this.name = name;
            this.blockSettings = AbstractBlock.Settings.create();
        }

        public BlockBuilder settings(AbstractBlock.Settings settings) {
            this.blockSettings = settings;
            return this;
        }

        public BlockBuilder requiresTool() {
            this.blockSettings = this.blockSettings.requiresTool();
            return this;
        }

        public BlockBuilder sounds(BlockSoundGroup soundGroup) {
            this.blockSettings = this.blockSettings.sounds(soundGroup);
            return this;
        }

        public BlockBuilder withItem(Item.Settings itemSettings) {
            this.itemSettings = itemSettings;
            return this;
        }

        public BlockBuilder withItem() {
            this.itemSettings = new Item.Settings();
            return this;
        }

        public Block register() {
            RegistryKey<Block> key = createBlockKey(modId, name);
            // Apply registry key to block settings
            blockSettings = blockSettings.registryKey(key);

            Block block = new Block(blockSettings);
            return BPuRegister.registerBlock(block, key, itemSettings);
        }
    }


    public static BlockBuilder block(String modId, String name) {
        return new BlockBuilder(modId, name);
    }

    // The original register method
    public static Block registerBlock(Block block, RegistryKey<Block> blockKey, @Nullable Item.Settings settings) {
        if (settings != null) {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, blockKey.getValue());
            BlockItem blockItem = new BlockItem(block, new Item.Settings().registryKey(itemKey));
            Registry.register(Registries.ITEM, itemKey, blockItem);
        }
        return Registry.register(Registries.BLOCK, blockKey, block);
    }


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
