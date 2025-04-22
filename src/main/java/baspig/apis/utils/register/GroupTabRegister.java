package baspig.apis.utils.register;

import baspig.apis.utils.Baspig_utils;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Baspig_
 * This class is made for fast and easier creative inventory registry. <p>
 * Atention: it MAY be NOT complatible with any inventory tab management or external mixing modifier.
 */
@SuppressWarnings("unused")
public class GroupTabRegister {
    private final String translatableText;
    private final Item tabIcon;
    private final List<ItemStack> listItems;
    private final RegistryKey<ItemGroup> customItemGroupKey;
    private final ItemGroup customItemGroup;

    private GroupTabRegister(@NotNull String mod_id, String tabName, Item tabIcon, List<ItemStack> itemsToAdd, byte tabNumber) {
        String modId = mod_id + tabNumber;
        this.translatableText = tabName;
        this.tabIcon = tabIcon;
        this.listItems = itemsToAdd;

        this.customItemGroupKey = RegistryKey.of(
                Registries.ITEM_GROUP.getKey(),
                Identifier.of(modId, "item_group")
        );

        this.customItemGroup = FabricItemGroup.builder()
                .icon(() -> new ItemStack(this.tabIcon))
                .displayName(Text.translatable(this.translatableText))
                .build();
    }

    public void register() {

        Baspig_utils.LOGGER.info("Adding Tab: {}", translatableText);

        Registry.register(Registries.ITEM_GROUP, customItemGroupKey, customItemGroup);
        ItemGroupEvents.modifyEntriesEvent(customItemGroupKey).register(itemGroup -> itemGroup.addAll(listItems));
    }

    /**This class add your items to a creative inventory tab.
     * @param mod_id Your mod-id
     * @param tabName the name of the tab.
     * @param tabIcon The icon item that will be shown in the tab
     * @param itemsToAdd A list of items that will be added to this specific tab
     * @param tabNumber This handles up to 256 unique tabs per mod-id (-128 to 127), you must choose one id per tab.
     *                  This is important, because you can't add more than one tab with the same mod-id
     *                  But this already handles it
     */
    public static void create(String mod_id, String tabName, Item tabIcon,List<ItemStack> itemsToAdd, byte tabNumber) {
        new GroupTabRegister(mod_id, tabName, tabIcon, itemsToAdd, tabNumber).register();
    }
}