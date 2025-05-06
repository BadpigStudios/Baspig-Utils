package baspig.apis.utils.register;

import baspig.apis.utils.register.tags.BlockTagProvider;
import baspig.apis.utils.register.tags.ItemTagProvider;
import baspig.apis.utils.register.trimPattern.TrimPattern;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(BlockTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.TRIM_PATTERN, TrimPattern::registerPatterns);
    }
}