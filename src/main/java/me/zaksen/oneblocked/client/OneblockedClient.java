package me.zaksen.oneblocked.client;

import me.zaksen.oneblocked.item.ItemsRegister;
import me.zaksen.oneblocked.network.ClientReceivers;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

public class OneblockedClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModelPredicateProviderRegistry.register(ItemsRegister.ONEBLOCK_GENERATOR_MOVER, new Identifier("hold_block"), (itemStack, clientWorld, livingEntity, seed) -> {
            if(livingEntity == null) {
                return 0.0f;
            }

            if(itemStack.getNbt() == null) {
                return 0.0f;
            }

            return itemStack.getNbt().getBoolean("hold_block") ? 1.0f : 0.0f;
        });
        ClientReceivers.register();
    }
}

