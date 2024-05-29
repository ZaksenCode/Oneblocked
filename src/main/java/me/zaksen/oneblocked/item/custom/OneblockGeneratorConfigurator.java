package me.zaksen.oneblocked.item.custom;

import me.zaksen.oneblocked.block.custom.OneblockGenerator;
import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import me.zaksen.oneblocked.screen.OneblockConfiguratorScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OneblockGeneratorConfigurator extends Item {
    public OneblockGeneratorConfigurator(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos clickPos = context.getBlockPos();

        if(world.getBlockState(clickPos).getBlock() instanceof OneblockGenerator) {
            if(world.isClient) {
                OneblockGeneratorEntity entity = (OneblockGeneratorEntity) world.getBlockEntity(clickPos);
                if(entity != null) {
                    MinecraftClient.getInstance().setScreen(new OneblockConfiguratorScreen(entity));
                }
                return ActionResult.PASS;
            }

            context.getStack().decrement(1);
            return ActionResult.CONSUME;
        }

        return ActionResult.PASS;
    }
}
