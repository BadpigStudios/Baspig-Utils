package baspig.apis.utils.register.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public BlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(ExtraBlockTags.MOB_HEADS)
                .add(Blocks.CREEPER_HEAD)
                .add(Blocks.DRAGON_HEAD)
                .add(Blocks.PIGLIN_HEAD)
                .add(Blocks.ZOMBIE_HEAD)
                .add(Blocks.SKELETON_SKULL)
                .add(Blocks.WITHER_SKELETON_SKULL)
                .add(Blocks.PLAYER_HEAD)

                .add(Blocks.CREEPER_WALL_HEAD)
                .add(Blocks.DRAGON_WALL_HEAD)
                .add(Blocks.PIGLIN_WALL_HEAD)
                .add(Blocks.ZOMBIE_WALL_HEAD)
                .add(Blocks.SKELETON_WALL_SKULL)
                .add(Blocks.WITHER_SKELETON_WALL_SKULL)
                .add(Blocks.PLAYER_WALL_HEAD);

        getOrCreateTagBuilder(ExtraBlockTags.TORCHES)
                .add(Blocks.TORCH)
                .add(Blocks.WALL_TORCH)
                .add(Blocks.REDSTONE_TORCH)
                .add(Blocks.REDSTONE_WALL_TORCH)
                .add(Blocks.SOUL_TORCH)
                .add(Blocks.SOUL_WALL_TORCH);

        getOrCreateTagBuilder(ExtraBlockTags.EXPLOSIVE_BLOCKS)
                .add(Blocks.TNT)
                .add(Blocks.RESPAWN_ANCHOR);

    }
}
