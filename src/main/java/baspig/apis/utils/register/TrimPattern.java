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

public class TrimPattern {

    private static String ModID;

    public TrimPattern(String mod_id){
        ModID = mod_id;
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
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.of(ModID,id));
        //return RegistryKey.of(RegistryKeys.TRIM_PATTERN, Identifier.ofVanilla(id));
    }
}
