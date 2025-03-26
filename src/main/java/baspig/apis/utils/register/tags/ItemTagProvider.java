package baspig.apis.utils.register.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider{
    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        /// -----------------------------------------------------------------------------------

        getOrCreateTagBuilder(ExtraItemTags.ANY_WOODEN_TOOL)
                .addTag(ExtraItemTags.WOODEN_AXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.WOODEN_SWORD_TOOL_LEVEL)
                .addTag(ExtraItemTags.WOODEN_PICKAXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.WOODEN_SHOVEL_TOOL_LEVEL)
                .addTag(ExtraItemTags.ANY_STONE_TOOL);

        getOrCreateTagBuilder(ExtraItemTags.ANY_STONE_TOOL)
                .addTag(ExtraItemTags.STONE_AXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.STONE_SWORD_TOOL_LEVEL)
                .addTag(ExtraItemTags.STONE_PICKAXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.STONE_SHOVEL_TOOL_LEVEL)
                .addTag(ExtraItemTags.STONE_HOE_TOOL_LEVEL)
                .addTag(ExtraItemTags.ANY_GOLD_TOOL);

        getOrCreateTagBuilder(ExtraItemTags.ANY_GOLD_TOOL)
                .addTag(ExtraItemTags.GOLD_AXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.GOLD_SWORD_TOOL_LEVEL)
                .addTag(ExtraItemTags.GOLD_PICKAXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.GOLD_SHOVEL_TOOL_LEVEL)
                .addTag(ExtraItemTags.GOLD_HOE_TOOL_LEVEL)
                .addTag(ExtraItemTags.ANY_IRON_TOOL);

        getOrCreateTagBuilder(ExtraItemTags.ANY_IRON_TOOL)
                .addTag(ExtraItemTags.IRON_AXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.IRON_SWORD_TOOL_LEVEL)
                .addTag(ExtraItemTags.IRON_PICKAXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.IRON_SHOVEL_TOOL_LEVEL)
                .addTag(ExtraItemTags.IRON_HOE_TOOL_LEVEL)
                .addTag(ExtraItemTags.ANY_DIAMOND_TOOL);

        getOrCreateTagBuilder(ExtraItemTags.ANY_DIAMOND_TOOL)
                .addTag(ExtraItemTags.DIAMOND_AXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.DIAMOND_SWORD_TOOL_LEVEL)
                .addTag(ExtraItemTags.DIAMOND_PICKAXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.DIAMOND_SHOVEL_TOOL_LEVEL)
                .addTag(ExtraItemTags.DIAMOND_HOE_TOOL_LEVEL)
                .addTag(ExtraItemTags.ANY_NETHERITE_TOOL);

        getOrCreateTagBuilder(ExtraItemTags.ANY_NETHERITE_TOOL)
                .addTag(ExtraItemTags.NETHERITE_AXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.NETHERITE_SWORD_TOOL_LEVEL)
                .addTag(ExtraItemTags.NETHERITE_PICKAXE_TOOL_LEVEL)
                .addTag(ExtraItemTags.NETHERITE_SHOVEL_TOOL_LEVEL)
                .addTag(ExtraItemTags.NETHERITE_HOE_TOOL_LEVEL);


        /// -----------------------------------------------------------------------------------

        getOrCreateTagBuilder(ExtraItemTags.WOODEN_AXE_TOOL_LEVEL)
                .add(Items.WOODEN_AXE)
                .addTag(ExtraItemTags.STONE_AXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.STONE_AXE_TOOL_LEVEL)
                .add(Items.STONE_AXE)
                .addTag(ExtraItemTags.GOLD_AXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.GOLD_AXE_TOOL_LEVEL)
                .add(Items.GOLDEN_AXE)
                .addTag(ExtraItemTags.IRON_AXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.IRON_AXE_TOOL_LEVEL)
                .add(Items.IRON_AXE)
                .addTag(ExtraItemTags.DIAMOND_AXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.DIAMOND_AXE_TOOL_LEVEL)
                .add(Items.DIAMOND_AXE)
                .addTag(ExtraItemTags.NETHERITE_AXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.NETHERITE_AXE_TOOL_LEVEL)
                .add(Items.NETHERITE_AXE);


        /// -----------------------------------------------------------------------------------

        getOrCreateTagBuilder(ExtraItemTags.WOODEN_PICKAXE_TOOL_LEVEL)
                .add(Items.WOODEN_PICKAXE)
                .addTag(ExtraItemTags.STONE_PICKAXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.STONE_PICKAXE_TOOL_LEVEL)
                .add(Items.STONE_PICKAXE)
                .addTag(ExtraItemTags.GOLD_PICKAXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.GOLD_PICKAXE_TOOL_LEVEL)
                .add(Items.GOLDEN_PICKAXE)
                .addTag(ExtraItemTags.IRON_PICKAXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.IRON_PICKAXE_TOOL_LEVEL)
                .add(Items.IRON_PICKAXE)
                .addTag(ExtraItemTags.DIAMOND_PICKAXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.DIAMOND_PICKAXE_TOOL_LEVEL)
                .add(Items.DIAMOND_PICKAXE)
                .addTag(ExtraItemTags.NETHERITE_PICKAXE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.NETHERITE_PICKAXE_TOOL_LEVEL)
                .add(Items.NETHERITE_PICKAXE);



        /// -----------------------------------------------------------------------------------

        getOrCreateTagBuilder(ExtraItemTags.WOODEN_SHOVEL_TOOL_LEVEL)
                .add(Items.WOODEN_SHOVEL)
                .addTag(ExtraItemTags.STONE_SHOVEL_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.STONE_SHOVEL_TOOL_LEVEL)
                .add(Items.STONE_SHOVEL)
                .addTag(ExtraItemTags.GOLD_SHOVEL_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.GOLD_SHOVEL_TOOL_LEVEL)
                .add(Items.GOLDEN_SHOVEL)
                .addTag(ExtraItemTags.IRON_SHOVEL_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.IRON_SHOVEL_TOOL_LEVEL)
                .add(Items.IRON_SHOVEL)
                .addTag(ExtraItemTags.DIAMOND_SHOVEL_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.DIAMOND_SHOVEL_TOOL_LEVEL)
                .add(Items.DIAMOND_SHOVEL)
                .addTag(ExtraItemTags.NETHERITE_SHOVEL_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.NETHERITE_SHOVEL_TOOL_LEVEL)
                .add(Items.NETHERITE_SHOVEL);

        /// ------------------------------------------------------------------------------------

        getOrCreateTagBuilder(ExtraItemTags.WOODEN_HOE_TOOL_LEVEL)
                .add(Items.WOODEN_HOE)
                .addTag(ExtraItemTags.STONE_HOE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.STONE_HOE_TOOL_LEVEL)
                .add(Items.STONE_HOE)
                .addTag(ExtraItemTags.GOLD_HOE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.GOLD_HOE_TOOL_LEVEL)
                .add(Items.GOLDEN_HOE)
                .addTag(ExtraItemTags.IRON_HOE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.IRON_HOE_TOOL_LEVEL)
                .add(Items.IRON_HOE)
                .addTag(ExtraItemTags.DIAMOND_HOE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.DIAMOND_HOE_TOOL_LEVEL)
                .add(Items.DIAMOND_HOE)
                .addTag(ExtraItemTags.NETHERITE_HOE_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.NETHERITE_HOE_TOOL_LEVEL)
                .add(Items.NETHERITE_HOE);

        /// ------------------------------------------------------------------------------------

        getOrCreateTagBuilder(ExtraItemTags.WOODEN_SWORD_TOOL_LEVEL)
                .add(Items.WOODEN_SWORD)
                .addTag(ExtraItemTags.STONE_SWORD_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.STONE_SWORD_TOOL_LEVEL)
                .add(Items.STONE_SWORD)
                .addTag(ExtraItemTags.GOLD_SWORD_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.GOLD_SWORD_TOOL_LEVEL)
                .add(Items.GOLDEN_SWORD)
                .addTag(ExtraItemTags.IRON_SWORD_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.IRON_SWORD_TOOL_LEVEL)
                .add(Items.IRON_SWORD)
                .addTag(ExtraItemTags.DIAMOND_SWORD_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.DIAMOND_SWORD_TOOL_LEVEL)
                .add(Items.DIAMOND_SWORD)
                .addTag(ExtraItemTags.NETHERITE_SWORD_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.NETHERITE_SWORD_TOOL_LEVEL)
                .add(Items.NETHERITE_SWORD);

        getOrCreateTagBuilder(ExtraItemTags.NO_TOOL_LEVEL);

        getOrCreateTagBuilder(ExtraItemTags.MOB_HEADS)
                .add(Items.CREEPER_HEAD)
                .add(Items.DRAGON_HEAD)
                .add(Items.PIGLIN_HEAD)
                .add(Items.ZOMBIE_HEAD)
                .add(Items.SKELETON_SKULL)
                .add(Items.WITHER_SKELETON_SKULL)
                .add(Items.PLAYER_HEAD);

        getOrCreateTagBuilder(ExtraItemTags.TORCHES)
                .add(Items.TORCH)
                .add(Items.REDSTONE_TORCH)
                .add(Items.SOUL_TORCH);

        getOrCreateTagBuilder(ExtraItemTags.EXPLOSIVE_BLOCKS)
                .add(Items.TNT)
                .add(Items.RESPAWN_ANCHOR);

        getOrCreateTagBuilder(ExtraItemTags.ALL_MINERAL)
                .add(Items.COAL)
                .add(Items.RAW_COPPER)
                .add(Items.RAW_IRON)
                .add(Items.RAW_GOLD)
                .add(Items.LAPIS_LAZULI)
                .add(Items.DIAMOND)
                .add(Items.REDSTONE)
                .add(Items.NETHERITE_SCRAP)
                .add(Items.EMERALD)

                .add(Items.COPPER_INGOT)
                .add(Items.IRON_INGOT)
                .add(Items.GOLD_INGOT)

                .add(Items.NETHERITE_INGOT);

        getOrCreateTagBuilder(ExtraItemTags.RAW_MINERAL)
                .add(Items.COAL)
                .add(Items.RAW_COPPER)
                .add(Items.RAW_IRON)
                .add(Items.RAW_GOLD)
                .add(Items.LAPIS_LAZULI)
                .add(Items.DIAMOND)
                .add(Items.REDSTONE)
                .add(Items.NETHERITE_SCRAP)
                .add(Items.EMERALD);

        getOrCreateTagBuilder(ExtraItemTags.SMELTED_MINERAL)

                .add(Items.COPPER_INGOT)
                .add(Items.IRON_INGOT)
                .add(Items.GOLD_INGOT)

                .add(Items.NETHERITE_INGOT);

        getOrCreateTagBuilder(ExtraItemTags.SMELTED_AND_NATURAL_MINERAL)
                .add(Items.COAL)
                .add(Items.LAPIS_LAZULI)
                .add(Items.DIAMOND)
                .add(Items.REDSTONE)
                .add(Items.EMERALD)

                .add(Items.COPPER_INGOT)
                .add(Items.IRON_INGOT)
                .add(Items.GOLD_INGOT)

                .add(Items.NETHERITE_INGOT);

        getOrCreateTagBuilder(ExtraItemTags.NATURAL_MINERAL)
                .add(Items.COAL)
                .add(Items.LAPIS_LAZULI)
                .add(Items.DIAMOND)
                .add(Items.REDSTONE)
                .add(Items.EMERALD);

    }
}
