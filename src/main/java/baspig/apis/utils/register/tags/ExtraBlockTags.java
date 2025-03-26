package baspig.apis.utils.register.tags;

import baspig.apis.utils.Baspig_utils;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ExtraBlockTags {


    public static final TagKey<Block> MOB_HEADS = of("mob_heads");
    public static final TagKey<Block> TORCHES = of("torches");
    public static final TagKey<Block> EXPLOSIVE_BLOCKS = of("explosive_blocks");

    public ExtraBlockTags() {
    }

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(Baspig_utils.MOD_ID,id));
    }
}
