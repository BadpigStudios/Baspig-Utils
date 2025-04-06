package baspig.apis.utils.register.easifier;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import java.util.function.Supplier;

class BpuEntityRegisterInDev {
    /// Entities------------------------------------ ->
    private static RegistryKey<EntityType<?>> createEntityKey(String modId, String name) {
        return RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(modId, name));
    }

    public static class EntityBuilder<T extends LivingEntity> {

        private Supplier<DefaultAttributeContainer.Builder> attributesSupplier;
        private final String name;
        private final String modId;
        private final EntityType.Builder<T> entityTypeBuilder;
        private SpawnGroup spawnGroup = SpawnGroup.MISC;

        private EntityBuilder(String modId, String name, EntityFactory<T> factory, float width, float height) {
            this.name = name;
            this.modId = modId;
            this.entityTypeBuilder = EntityType.Builder.create(factory::create, spawnGroup)
                    .dimensions(width, height);
        }

        public EntityBuilder<T> eyeHeight(float height){
            this.entityTypeBuilder.eyeHeight(height);
            return this;
        }

        public EntityBuilder<T> dropsNothing(){
            this.entityTypeBuilder.dropsNothing();
            return this;
        }

        public EntityBuilder<T> fireImmune() {
            this.entityTypeBuilder.makeFireImmune();
            return this;
        }

        public EntityBuilder<T> fireImmune(float height) {
            this.entityTypeBuilder.nameTagAttachment(height);
            return this;
        }

        public EntityBuilder<T> dimensions(float width, float height) {
            this.entityTypeBuilder.dimensions(width, height);
            return this;
        }

        public EntityBuilder<T> trackRangeBlocks(int trackRange) {
            this.entityTypeBuilder.maxTrackingRange(trackRange);
            return this;
        }

        public EntityBuilder<T> trackedUpdateRate(int updateRate) {
            this.entityTypeBuilder.trackingTickInterval(updateRate);
            return this;
        }

        public EntityBuilder<T> specificSpawnBlocks(Block... blocks) {
            this.entityTypeBuilder.allowSpawningInside(blocks);
            return this;
        }

        public EntityBuilder<T> spawneableFarFromPlayer() {
            this.entityTypeBuilder.spawnableFarFromPlayer();
            return this;
        }

        public EntityBuilder<T> attributes(Supplier<DefaultAttributeContainer.Builder> attributesSupplier) {
            this.attributesSupplier = attributesSupplier;
            return this;
        }

        public EntityType<T> register() {
            RegistryKey<EntityType<?>> key = createEntityKey(modId, name);
            EntityType<T> entityType = this.entityTypeBuilder.build(RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(modId, name)));

            EntityType<T> registeredEntityType = Registry.register(Registries.ENTITY_TYPE, key, entityType);

            if (this.attributesSupplier != null) {
                FabricDefaultAttributeRegistry.register(registeredEntityType, this.attributesSupplier.get());
            }
            return registeredEntityType;
        }

        public EntityBuilder<T> spawnGroup(SpawnGroup spawnGroup) {
            this.spawnGroup = spawnGroup;
            return this;
        }
    }

    @FunctionalInterface
    public interface EntityFactory<T extends LivingEntity> {
        T create(EntityType<T> entityType, World world);
    }

    public static <T extends LivingEntity> EntityBuilder<T> entity(String modId, String name, EntityFactory<T> factory, float width, float height) {
        return new EntityBuilder<>(modId, name, factory, width, height);
    }

    public static <T extends LivingEntity> EntityType<T> register(String modId, String name, EntityType<T> entityType) {
        return Registry.register(Registries.ENTITY_TYPE, Identifier.of(modId, name), entityType);
    }
}
