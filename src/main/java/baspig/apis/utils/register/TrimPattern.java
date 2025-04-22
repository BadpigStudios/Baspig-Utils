package baspig.apis.utils.register;

import baspig.apis.utils.Baspig_utils;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import org.jetbrains.annotations.NotNull;

/**This class is an additions for a faster trim pattern registering.
 * <p>
 * This MAY actually cause problems if you try to modify it later, but I'm not sure.
 *
 * @author Baspig_
 */
@SuppressWarnings("unused")
public class TrimPattern implements DataGeneratorEntrypoint {
    private static String ModID;
    private static boolean Decal;
    private static net.minecraft.item.Item Item;
    private static RegistryKey<ArmorTrimPattern> PATTERN_REGISTRY_KEY;
    private static RegistryKey<ArmorTrimPattern> KEY;

    /**
     * @param mod_id Your mod id.
     * @param patternName Name of the pattern. E.g: "spirit"
     * @param templateItem The base item. IT MUST BE AND SPECIAL ITEM TYPE. <p>
     *                     You must register at first an SmithingTableItem,
     *                     see <a href="https://github.com/BadpigStudios/Baspig-Utils/wiki/Trim-pattern-register">Trim Item Register</a> for more info:
     */
    public TrimPattern(@NotNull String mod_id, @NotNull String patternName, @NotNull Item templateItem){
        Baspig_utils.LOGGER.info("Adding PATTERN: {}...", patternName);
        ModID = mod_id;
        Item = templateItem;
        PATTERN_REGISTRY_KEY = of(patternName);
    }

    private static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, Item, PATTERN_REGISTRY_KEY);
    }

    private static void register(Registerable<ArmorTrimPattern> registry, Item item, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(
                key.getValue(),
                Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())),
                false
        );
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        assert ModID != null;
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(ModID,id));
    }

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {}

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        registryBuilder.addRegistry(RegistryKeys.TRIM_PATTERN, TrimPattern::bootstrap);
    }
}
