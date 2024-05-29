package me.zaksen.oneblocked.item.custom;

import me.zaksen.oneblocked.block.BlocksRegister;
import me.zaksen.oneblocked.block.custom.OneblockGenerator;
import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import me.zaksen.oneblocked.stage.Stage;
import me.zaksen.oneblocked.stage.StagesRegister;
import net.minecraft.block.Blocks;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OneblockGeneratorMover extends Item {
    public OneblockGeneratorMover(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();

        if(world.isClient) {
            return ActionResult.PASS;
        }

        NbtCompound itemNbt = context.getStack().getNbt();
        if(itemNbt == null) {
            itemNbt = new NbtCompound();
        }

        BlockPos pos = context.getBlockPos();

        if(itemNbt.getBoolean("hold_block")) {
            BlockPos placePos = pos.add(context.getSide().getVector());
            world.setBlockState(placePos, BlocksRegister.ONEBLOCK_GENERATOR.getDefaultState());
            OneblockGeneratorEntity oneblockGeneratorEntity = (OneblockGeneratorEntity) world.getBlockEntity(placePos);

            if(oneblockGeneratorEntity != null) {
                itemNbt.putBoolean("hold_block", false);

                long brokenBlocks = itemNbt.getLong("broken_blocks");
                long maxBrokenBlocks = itemNbt.getLong("max_broken_blocks");
                long totalBrokenBlocks = itemNbt.getLong("total_broken_blocks");
                boolean countBlocks = itemNbt.getBoolean("count_blocks");
                oneblockGeneratorEntity.setBrokenBlocks(brokenBlocks);
                oneblockGeneratorEntity.setMaxBrokenBlocks(maxBrokenBlocks);
                oneblockGeneratorEntity.setTotalBrokenBlocks(totalBrokenBlocks);
                oneblockGeneratorEntity.setCountBlocks(countBlocks);

                itemNbt.remove("broken_blocks");
                itemNbt.remove("total_broken_blocks");
                itemNbt.remove("count_blocks");

                context.getStack().setNbt(itemNbt);
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        }

        if (world.getBlockState(pos).getBlock() instanceof OneblockGenerator) {
            OneblockGeneratorEntity oneblockGeneratorEntity = (OneblockGeneratorEntity) world.getBlockEntity(pos);

            if(oneblockGeneratorEntity != null) {
                itemNbt.putBoolean("hold_block", true);

                long brokenBlocks = oneblockGeneratorEntity.getBrokenBlocks();
                long maxBrokenBlocks = oneblockGeneratorEntity.getMaxBrokenBlocks();
                long totalBrokenBlocks = oneblockGeneratorEntity.getTotalBrokenBlocks();
                boolean countBlocks = oneblockGeneratorEntity.isCountBlocks();
                itemNbt.putLong("broken_blocks", brokenBlocks);
                itemNbt.putLong("max_broken_blocks", maxBrokenBlocks);
                itemNbt.putLong("total_broken_blocks", totalBrokenBlocks);
                itemNbt.putBoolean("count_blocks", countBlocks);

                context.getStack().setNbt(itemNbt);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
                return ActionResult.SUCCESS;
            }

            return ActionResult.PASS;
        }

        return ActionResult.PASS;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if(stack.getNbt() == null) {
            return;
        }

        if(!stack.getNbt().getBoolean("hold_block")) {
            tooltip.add(Text.translatable("tooltip.oneblocked.oneblock_mover.empty"));
            return;
        }

        long broken_blocks = stack.getNbt().getLong("broken_blocks");
        long max_broken_blocks = stack.getNbt().getLong("max_broken_blocks");
        long total_broken_blocks = stack.getNbt().getLong("total_broken_blocks");
        Text isCountBlocks = stack.getNbt().getBoolean("count_blocks") ? Text.translatable("lang.oneblocked.yes") : Text.translatable("lang.oneblocked.no");

        Stage blockStage = StagesRegister.getStage((int) broken_blocks);
        tooltip.add(Text.translatable("tooltip.oneblocked.oneblock_mover.blocks_broken", broken_blocks));
        tooltip.add(Text.translatable("tooltip.oneblocked.oneblock_mover.max_blocks_broken", max_broken_blocks));
        tooltip.add(Text.translatable("tooltip.oneblocked.oneblock_mover.total_blocks_broken", total_broken_blocks));
        tooltip.add(Text.translatable("tooltip.oneblocked.oneblock_mover.count_blocks", isCountBlocks));
        tooltip.add(Text.translatable("tooltip.oneblocked.oneblock_mover.stage", blockStage.getStageName()));
    }
}
