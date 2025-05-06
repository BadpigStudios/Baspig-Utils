package baspig.apis.utils.register;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class RegistryKeyOf {
    /**
     * Creates the Registry Key only giving the Identifier.
     * @param identifier The identifier of the block
     * @return The registry key for Block register
     */
    public static RegistryKey<Block> block(Identifier identifier){
        return RegistryKey.of(RegistryKeys.BLOCK, identifier);
    }
    /**
     * Creates the Registry Key only giving the Identifier.
     * @param identifier The identifier of the entity type
     * @return The registry key for EntityType register
     */
    public static RegistryKey<EntityType<?>> entity(Identifier identifier){
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, identifier);
    }
    /**
     * Creates the Registry Key only giving the Identifier.
     * @param identifier The identifier of the item
     * @return The registry key for Item register
     */
    public static RegistryKey<Item> item(Identifier identifier){
        return RegistryKey.of(RegistryKeys.ITEM, identifier);
    }
}
