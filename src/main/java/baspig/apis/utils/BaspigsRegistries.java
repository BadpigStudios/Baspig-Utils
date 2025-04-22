package baspig.apis.utils;

import baspig.apis.utils.client.render.draw_entity.DrawEntity;
import baspig.apis.utils.client.render.draw_entity.edit.DrawEntityEditTool;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class BaspigsRegistries {

    private static final Identifier MY_CUSTOM_ENTITY_ID = Identifier.of(Baspig_utils.MOD_ID, "draw_entity");

    private static final RegistryKey<EntityType<?>> MY_CUSTOM_ENTITY_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, MY_CUSTOM_ENTITY_ID);

    public static final EntityType<DrawEntity> DRAW_ENTITY = Registry.register(
            Registries.ENTITY_TYPE,
            Identifier.of(Baspig_utils.MOD_ID, "draw_entity"),
            EntityType.Builder.create(DrawEntity::new, SpawnGroup.MISC).dimensions(1.0f,1.0f).build(MY_CUSTOM_ENTITY_KEY)
    );

    private static final Identifier DRAW_ENTITY_EDIT_TOOL_ID = Identifier.of(Baspig_utils.MOD_ID,"draw_entity_edit_tool");

    public static final RegistryKey<Item> DRAW_ENTITY_EDIT_TOOL_KEY = RegistryKey.of(RegistryKeys.ITEM, DRAW_ENTITY_EDIT_TOOL_ID);

    public static final Item DRAW_ENTITY_EDIT_TOOL = registerItem(new DrawEntityEditTool(new Item.Settings().registryKey(DRAW_ENTITY_EDIT_TOOL_KEY)), DRAW_ENTITY_EDIT_TOOL_KEY);

    public static Item registerItem(Item item, RegistryKey<Item> registryKey) {
        return Registry.register(Registries.ITEM, registryKey.getValue(), item);
    }



    protected static void reg(){

    }
}
