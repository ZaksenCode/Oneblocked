package me.zaksen.oneblocked.item.custom;

import me.zaksen.oneblocked.block.custom.OneblockGenerator;
import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import me.zaksen.oneblocked.screen.OneblockAnalyzerScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OneblockGeneratorAnalyzer extends Item {
    public OneblockGeneratorAnalyzer(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if(world.isClient && world.getBlockState(pos).getBlock() instanceof OneblockGenerator) {
            OneblockGeneratorEntity entity = (OneblockGeneratorEntity) world.getBlockEntity(pos);
            if(entity != null) {
                MinecraftClient.getInstance().setScreen(new OneblockAnalyzerScreen(entity));
            }
        }

        return ActionResult.PASS;
    }
}
