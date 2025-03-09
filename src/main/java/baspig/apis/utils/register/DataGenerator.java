package baspig.apis.utils.register;

import baspig.apis.utils.register.tags.ItemTagProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.registry.RegistryBuilder;

public class DataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ItemTagProvider::new);
    }

    @SuppressWarnings("unused")
    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {

    }
}
