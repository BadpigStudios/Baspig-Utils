package baspig.apis.utils.register.tags;

import baspig.apis.utils.Baspig_utils;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ExtraItemTags {

    public static final TagKey<Item> WOODEN_AXE_TOOL_LEVEL = of("wooden_axe_tool_level");
    public static final TagKey<Item> STONE_AXE_TOOL_LEVEL = of("stone_axe_tool_level");
    public static final TagKey<Item> GOLD_AXE_TOOL_LEVEL = of("gold_axe_tool_level");
    public static final TagKey<Item> IRON_AXE_TOOL_LEVEL = of("iron_axe_tool_level");
    public static final TagKey<Item> DIAMOND_AXE_TOOL_LEVEL = of("diamond_axe_tool_level");
    public static final TagKey<Item> NETHERITE_AXE_TOOL_LEVEL = of("netherite_axe_tool_level");

    public static final TagKey<Item> WOODEN_PICKAXE_TOOL_LEVEL = of("wooden_pickaxe_tool_level");
    public static final TagKey<Item> STONE_PICKAXE_TOOL_LEVEL = of("stone_pickaxe_tool_level");
    public static final TagKey<Item> GOLD_PICKAXE_TOOL_LEVEL = of("gold_pickaxe_tool_level");
    public static final TagKey<Item> IRON_PICKAXE_TOOL_LEVEL = of("iron_pickaxe_tool_level");
    public static final TagKey<Item> DIAMOND_PICKAXE_TOOL_LEVEL = of("diamond_pickaxe_tool_level");
    public static final TagKey<Item> NETHERITE_PICKAXE_TOOL_LEVEL = of("netherite_pickaxe_tool_level");

    public static final TagKey<Item> WOODEN_SHOVEL_TOOL_LEVEL = of("wooden_shovel_tool_level");
    public static final TagKey<Item> STONE_SHOVEL_TOOL_LEVEL = of("stone_shovel_tool_level");
    public static final TagKey<Item> GOLD_SHOVEL_TOOL_LEVEL = of("gold_shovel_tool_level");
    public static final TagKey<Item> IRON_SHOVEL_TOOL_LEVEL = of("iron_shovel_tool_level");
    public static final TagKey<Item> DIAMOND_SHOVEL_TOOL_LEVEL = of("diamond_shovel_tool_level");
    public static final TagKey<Item> NETHERITE_SHOVEL_TOOL_LEVEL = of("netherite_shovel_tool_level");

    public static final TagKey<Item> WOODEN_HOE_TOOL_LEVEL = of("wooden_hoe_tool_level");
    public static final TagKey<Item> STONE_HOE_TOOL_LEVEL = of("stone_hoe_tool_level");
    public static final TagKey<Item> GOLD_HOE_TOOL_LEVEL = of("gold_hoe_tool_level");
    public static final TagKey<Item> IRON_HOE_TOOL_LEVEL = of("iron_hoe_tool_level");
    public static final TagKey<Item> DIAMOND_HOE_TOOL_LEVEL = of("diamond_hoe_tool_level");
    public static final TagKey<Item> NETHERITE_HOE_TOOL_LEVEL = of("netherite_hoe_tool_level");

    public static final TagKey<Item> WOODEN_SWORD_TOOL_LEVEL = of("wooden_sword_tool_level");
    public static final TagKey<Item> STONE_SWORD_TOOL_LEVEL = of("stone_sword_tool_level");
    public static final TagKey<Item> GOLD_SWORD_TOOL_LEVEL = of("gold_sword_tool_level");
    public static final TagKey<Item> IRON_SWORD_TOOL_LEVEL = of("iron_sword_tool_level");
    public static final TagKey<Item> DIAMOND_SWORD_TOOL_LEVEL = of("diamond_sword_tool_level");
    public static final TagKey<Item> NETHERITE_SWORD_TOOL_LEVEL = of("netherite_sword_tool_level");

    public static final TagKey<Item> ANY_WOODEN_TOOL = of("wooden_any_tool");
    public static final TagKey<Item> ANY_STONE_TOOL = of("stone_any_tool");
    public static final TagKey<Item> ANY_GOLD_TOOL = of("gold_any_tool");
    public static final TagKey<Item> ANY_IRON_TOOL = of("iron_any_tool");
    public static final TagKey<Item> ANY_DIAMOND_TOOL = of("diamond_any_tool");
    public static final TagKey<Item> ANY_NETHERITE_TOOL = of("netherite_any_tool");

    /**
     * !!DO NOT USE THIS TAG OUTSIDE BASPIG UTILS LIBRARY!!
     * <p>
     * You need special config to make it work properly!!
     */
    public static final TagKey<Item> NO_TOOL_LEVEL = of("no_item_tag");

    public ExtraItemTags() {
    }

    private static TagKey<Item> of(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(Baspig_utils.MOD_ID,id));
    }
}
