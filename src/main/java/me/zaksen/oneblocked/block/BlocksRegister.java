package me.zaksen.oneblocked.block;

import me.zaksen.oneblocked.block.custom.OneblockGenerator;
import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class BlocksRegister {

    public static final Block ONEBLOCK_GENERATOR = registerBlock("oneblock_generator",
            new OneblockGenerator(FabricBlockSettings.create().strength(-1f, 3600000.0F)));
    public static final BlockEntityType<OneblockGeneratorEntity> ONEBLOCK_GENERATOR_ENTITY = registerBlockEntity(
            "oneblock_generator",
            FabricBlockEntityTypeBuilder.create(OneblockGeneratorEntity::new, ONEBLOCK_GENERATOR).build()
    );
    public static final Item ONEBLOCK_GENERATOR_ITEM = registerBlockItem("oneblock_generator", new BlockItem(ONEBLOCK_GENERATOR, new FabricItemSettings()));

    private static Block registerBlock(String id, Block block) {
        return Registry.register(
                Registries.BLOCK,
                new Identifier("oneblocked", id),
                block
        );
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerBlockEntity(String id, BlockEntityType<T> entityType) {
        return Registry.register(
                Registries.BLOCK_ENTITY_TYPE,
                new Identifier("oneblocked", id),
                entityType
        );
    }

    private static Item registerBlockItem(String id, BlockItem blockItem) {
        return Registry.register(
                Registries.ITEM,
                new Identifier("oneblocked", id),
                blockItem
        );
    }

    public static void register() {

    }
}
