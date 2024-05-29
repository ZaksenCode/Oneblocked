package me.zaksen.oneblocked.network;

import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ServerReceivers {

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(NetworkConstants.CHANGE_BLOCKS_BROKEN_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            BlockPos generatorPos = buf.readBlockPos();
            long receivedStageBlocks = buf.readLong();
            server.execute(() -> processChangeBlocksBroken(receivedStageBlocks, generatorPos, server, player));
        });

        ServerPlayNetworking.registerGlobalReceiver(NetworkConstants.CHANGE_BLOCKS_COUNT_PACKET_ID, (server, player, handler, buf, responseSender) -> {
            BlockPos generatorPos = buf.readBlockPos();
            boolean countBlocks = buf.readBoolean();
            server.execute(() -> processChangeBlocksCount(countBlocks, generatorPos, server, player));
        });
    }

    private static void processChangeBlocksBroken(long blocksBroken, BlockPos pos, MinecraftServer server, PlayerEntity player) {
        World receivedWorld = server.getWorld(player.getWorld().getRegistryKey());
        double distance = player.getPos().distanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
        if(distance > 10) {
            return;
        }
        if(receivedWorld == null) {
            return;
        }
        BlockEntity receivedEntity = receivedWorld.getBlockEntity(pos);
        if (receivedEntity instanceof OneblockGeneratorEntity generatorEntity) {
            long maxBrokenBlocks = generatorEntity.getMaxBrokenBlocks();
            if(blocksBroken >= maxBrokenBlocks) {
                return;
            }
            generatorEntity.setBrokenBlocks(blocksBroken);
        }
    }

    private static void processChangeBlocksCount(boolean value, BlockPos pos, MinecraftServer server, PlayerEntity player) {
        World receivedWorld = server.getWorld(player.getWorld().getRegistryKey());
        double distance = player.getPos().distanceTo(new Vec3d(pos.getX(), pos.getY(), pos.getZ()));
        if(distance > 10) {
            return;
        }
        if(receivedWorld == null) {
            return;
        }
        BlockEntity receivedEntity = receivedWorld.getBlockEntity(pos);
        if (receivedEntity instanceof OneblockGeneratorEntity generatorEntity) {
            generatorEntity.setCountBlocks(value);
        }
    }
}
