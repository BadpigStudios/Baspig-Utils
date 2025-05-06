package baspig.apis.utils;

import baspig.apis.utils.events.block.BlockEvents;
import baspig.apis.utils.events.block.register.settings.ExtraBlockSettings;
import baspig.apis.utils.register.RegistryKeyOf;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class BaspigsRegistries {


    private final static RegistryKey<Block> tnt = RegistryKeyOf.block(Identifier.of(Baspig_utils.MOD_ID, "super_tnt"));

    public static Block Super_TNT =
            BlockEvents.Register.TntBlock(
                    tnt,
                    AbstractBlock.Settings.create().registryKey(tnt),
                    ExtraBlockSettings.create().fuse(2).build(),
                    true
            );

    protected static void reg(){}

}
