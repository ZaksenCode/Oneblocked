package me.zaksen.oneblocked;

import me.zaksen.oneblocked.block.BlocksRegister;
import me.zaksen.oneblocked.item.ItemsRegister;
import me.zaksen.oneblocked.network.ServerReceivers;
import me.zaksen.oneblocked.resource.ResourceListener;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Oneblocked implements ModInitializer {

    private final Logger logger = LoggerFactory.getLogger(Oneblocked.class);

    @Override
    public void onInitialize() {
        ItemsRegister.register();
        BlocksRegister.register();
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new ResourceListener(logger));
        ServerReceivers.register();
    }
}

