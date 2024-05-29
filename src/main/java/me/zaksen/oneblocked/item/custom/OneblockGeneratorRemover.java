package me.zaksen.oneblocked.item.custom;

import me.zaksen.oneblocked.block.custom.OneblockGenerator;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OneblockGeneratorRemover extends Item {
    public OneblockGeneratorRemover(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos clickPos = context.getBlockPos();

        if(world.getBlockState(clickPos).getBlock() instanceof OneblockGenerator) {
            world.setBlockState(clickPos, Blocks.AIR.getDefaultState());
            context.getStack().decrement(1);
            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }
}
