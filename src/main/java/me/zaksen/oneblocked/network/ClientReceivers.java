package me.zaksen.oneblocked.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;

public class ClientReceivers {

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(NetworkConstants.STAGE_UPDATE_PACKET_ID, (client, handler, buf, responseSender) -> {
            BlockPos generatorPos = buf.readBlockPos();

            if(client.world == null) {
                System.out.println("Client world is null!");
                return;
            }

            for(int x = 0; x < 22; x++) {
                client.world.addParticle(
                        ParticleTypes.HAPPY_VILLAGER,
                        generatorPos.getX() + 0.5 + Math.sin(x * 2) * 1.1,
                        generatorPos.getY() + 0.5,
                        generatorPos.getZ() + 0.5 + Math.cos(x * 2) * 1.1,
                        0,
                        0,
                        0
                );
            }

            for(int x = 0; x < 22; x++) {
                client.world.addParticle(
                        ParticleTypes.HAPPY_VILLAGER,
                        generatorPos.getX() + 0.5 + Math.sin(x * 2) * 0.5,
                        generatorPos.getY() + 1.5,
                        generatorPos.getZ() + 0.5 + Math.cos(x * 2) * 0.5,
                        0,
                        0,
                        0
                );
            }
        });
    }
}
