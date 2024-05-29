package me.zaksen.oneblocked.item;

import me.zaksen.oneblocked.block.BlocksRegister;
import me.zaksen.oneblocked.item.custom.OneblockGeneratorAnalyzer;
import me.zaksen.oneblocked.item.custom.OneblockGeneratorConfigurator;
import me.zaksen.oneblocked.item.custom.OneblockGeneratorMover;
import me.zaksen.oneblocked.item.custom.OneblockGeneratorRemover;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ItemsRegister {
    public static final Item ONEBLOCK_GENERATOR_MOVER = registerItem(
            "oneblock_generator_mover",
            new OneblockGeneratorMover(new FabricItemSettings().maxCount(1).rarity(Rarity.RARE))
    );

    public static final Item ONEBLOCK_GENERATOR_REMOVER = registerItem(
            "oneblock_generator_remover",
            new OneblockGeneratorRemover(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON))
    );

    public static final Item ONEBLOCK_GENERATOR_CONFIGURATOR = registerItem(
            "oneblock_generator_configurator",
            new OneblockGeneratorConfigurator(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON))
    );

    public static final Item ONEBLOCK_GENERATOR_ANALYZER = registerItem(
            "oneblock_generator_analyzer",
            new OneblockGeneratorAnalyzer(new FabricItemSettings().maxCount(1).rarity(Rarity.UNCOMMON))
    );

    private static Item registerItem(String id, Item item) {
        return Registry.register(
                Registries.ITEM,
                new Identifier("oneblocked", id),
                item
        );
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(ONEBLOCK_GENERATOR_MOVER);
            content.add(ONEBLOCK_GENERATOR_REMOVER);
            content.add(ONEBLOCK_GENERATOR_CONFIGURATOR);
            content.add(ONEBLOCK_GENERATOR_ANALYZER);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(content -> {
            content.add(BlocksRegister.ONEBLOCK_GENERATOR_ITEM);
        });
    }
}
