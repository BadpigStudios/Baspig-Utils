package baspig.apis.utils.register;

import net.minecraft.item.Item;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;


class TrimPattern {
    private static String ModID;
    private static Item Item;
    public TrimPattern(@NotNull @NotBlank String mod_id, @NotNull @NotBlank Item templateItem){
        ModID = mod_id;
        Item = templateItem;
    }

    public static final RegistryKey<ArmorTrimPattern> PATTERN_REGISTRY_KEY = of("origin");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, Item, PATTERN_REGISTRY_KEY);
    }

    public static void register(Registerable<ArmorTrimPattern> registry, Item item, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(
                key.getValue(),
                Registries.ITEM.getEntry(item),
                Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())),
                false
        );
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        assert ModID != null;
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(ModID,id));
    }
}
