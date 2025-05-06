package baspig.apis.utils.register.trimPattern;

import baspig.apis.utils.register.RegistryKeyOf;
import net.minecraft.item.Item;
import net.minecraft.item.SmithingTemplateItem;
import net.minecraft.item.equipment.trim.ArmorTrimPattern;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class TrimPattern {
    private static final List<ArmorTrimPatternEntry> ENTRIES = new ArrayList<>();

    public static void registerPatterns(Registerable<ArmorTrimPattern> registry){
        for (ArmorTrimPatternEntry entry : ENTRIES){
            ArmorTrimPattern pattern  = new ArmorTrimPattern(
                    entry.id,
                    Text.translatable(Util.createTranslationKey("trim_pattern", entry.id)),
                    false
            );
            registry.register(entry.key, pattern);
        }
    }

    /**
     * This will add a new Trim Pattern linked to the item
     *
     * @param identifier The identifier of the template, to be referenced in textures and more
     * @param templateItem The item to link the new pattern
     */
    public static void addTrimPattern(Identifier identifier, Item templateItem){
        RegistryKey<ArmorTrimPattern> key = RegistryKey.of(RegistryKeys.TRIM_PATTERN, identifier);
        ENTRIES.add(new ArmorTrimPatternEntry(identifier, key, templateItem));
    }

    private record ArmorTrimPatternEntry(Identifier id, RegistryKey<ArmorTrimPattern> key, Item templateItem) {}

    /**
     * Class to register the base item for the trim pattern
     */
    public record ItemRegistry() {

        /**
         * This creates
         *
         * @param settings The Item Settings of the new trim pattern base item
         * @param identifier The identifier of the new item
         * @return A new SmithingTemplateItem to use in trim pattern registry
         */
        public Item create(Item.Settings settings, Identifier identifier) {
            return Registry.register(Registries.ITEM, identifier,
                    SmithingTemplateItem.of(
                            settings.registryKey(
                                    RegistryKeyOf.item(identifier)
                            )
                    )
            );
        }
    }
}
