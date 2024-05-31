package me.zaksen.oneblocked.block.custom;

import me.zaksen.oneblocked.block.BlocksRegister;
import me.zaksen.oneblocked.network.NetworkConstants;
import me.zaksen.oneblocked.stage.Stage;
import me.zaksen.oneblocked.stage.StagesRegister;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class OneblockGeneratorEntity extends BlockEntity {

    private long brokenBlocks = 0;
    private long maxBrokenBlocks = 0;
    private long totalBrokenBlocks = 0;
    private boolean countBlocks = true;
    private Stage lastStage = null;

    public OneblockGeneratorEntity(BlockPos pos, BlockState state) {
        super(BlocksRegister.ONEBLOCK_GENERATOR_ENTITY, pos, state);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if(world.isClient) {
            return;
        }

        if(world.getBlockState(pos.up()).isAir()) {
            if(countBlocks) {
                brokenBlocks++;
            }
            totalBrokenBlocks++;

            if(brokenBlocks > maxBrokenBlocks) {
                maxBrokenBlocks = brokenBlocks;
            }

            stageUpdate(world, pos);
            placeStageBlock(world, pos.up(), lastStage);
            spawnStageMob(world, pos.up(), lastStage);
            world.updateListeners(pos, state, world.getBlockState(pos), 0);
        }
    }

    public void setBrokenBlocks(long brokenBlocks) {
        this.brokenBlocks = brokenBlocks;
        stageUpdate(world, pos);
    }

    public long getBrokenBlocks() {
        return brokenBlocks;
    }

    public void setMaxBrokenBlocks(long maxBrokenBlocks) {
        this.maxBrokenBlocks = maxBrokenBlocks;
    }

    public long getMaxBrokenBlocks() {
        return maxBrokenBlocks;
    }

    public void setTotalBrokenBlocks(long totalBrokenBlocks) {
        this.totalBrokenBlocks = totalBrokenBlocks;
    }

    public long getTotalBrokenBlocks() {
        return totalBrokenBlocks;
    }

    public void setCountBlocks(boolean countBlocks) {
        this.countBlocks = countBlocks;
    }

    public boolean isCountBlocks() {
        return countBlocks;
    }

    private void stageUpdate(World world, BlockPos pos) {
        Stage stage = StagesRegister.getStage((int) brokenBlocks);

        if(stage != lastStage) {
            lastStage = stage;
            onStageUpdate(world, pos.up(), stage);
        }
    }

    private void placeStageBlock(World world, BlockPos pos, Stage stage) {
        if(stage == null) {
            return;
        }
        Block newBlock = stage.getRandomBlock().getAsBlock();
        world.setBlockState(pos, newBlock.getDefaultState());
    }

    private void spawnStageMob(World world, BlockPos pos, Stage stage) {
        if(stage == null) {
            return;
        }
        EntityType entityType = stage.getRandomMob().getAsEntityType();
        entityType.spawn((ServerWorld) world, pos.add(0, 1, 0), SpawnReason.NATURAL);
    }

    private void onStageUpdate(World world, BlockPos pos, Stage stage) {
        PacketByteBuf bytes = PacketByteBufs.create();
        bytes.writeBlockPos(pos);

        for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
            ServerPlayNetworking.send(player, NetworkConstants.STAGE_UPDATE_PACKET_ID, bytes);
        }
    }

    @Override
    public void writeNbt(NbtCompound nbt) {
        nbt.putLong("broken_blocks", brokenBlocks);
        nbt.putLong("max_broken_blocks", maxBrokenBlocks);
        nbt.putLong("total_broken_blocks", totalBrokenBlocks);
        nbt.putBoolean("count_blocks", isCountBlocks());
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        brokenBlocks = nbt.getLong("broken_blocks");
        maxBrokenBlocks = nbt.getLong("max_broken_blocks");
        totalBrokenBlocks = nbt.getLong("total_broken_blocks");
        countBlocks = nbt.getBoolean("count_blocks");

        Stage stage = StagesRegister.getStage((int) brokenBlocks);
        if(stage != lastStage) {
            lastStage = stage;
        }
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }
}
