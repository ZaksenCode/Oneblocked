package me.zaksen.oneblocked.screen;

import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import me.zaksen.oneblocked.network.NetworkConstants;
import me.zaksen.oneblocked.screen.widget.StagesWidget;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;

public class OneblockConfiguratorScreen extends Screen {

    private final OneblockGeneratorEntity oneblockGeneratorEntity;

    public OneblockConfiguratorScreen(OneblockGeneratorEntity oneblockGeneratorEntity) {
        super(Text.translatable("screen.oneblocked.oneblock_configurator.screen"));
        this.oneblockGeneratorEntity = oneblockGeneratorEntity;
    }

    @Override
    protected void init() {
        addDrawableChild(ButtonWidget.builder(Text.translatable("lang.oneblocked.count_blocks"), (widget) -> {
            PacketByteBuf bytes = PacketByteBufs.create();
            bytes.writeBlockPos(oneblockGeneratorEntity.getPos());
            bytes.writeBoolean(true);
            ClientPlayNetworking.send(NetworkConstants.CHANGE_BLOCKS_COUNT_PACKET_ID, bytes);
        }).dimensions(210, 10, 150, 20).build());

        addDrawableChild(ButtonWidget.builder(Text.translatable("lang.oneblocked.not_count_blocks"), (widget) -> {
            PacketByteBuf bytes = PacketByteBufs.create();
            bytes.writeBlockPos(oneblockGeneratorEntity.getPos());
            bytes.writeBoolean(false);
            ClientPlayNetworking.send(NetworkConstants.CHANGE_BLOCKS_COUNT_PACKET_ID, bytes);
        }).dimensions(210, 40, 150, 20).build());

        addDrawableChild(new StagesWidget(10, 10, 180, this.height - 20, Text.empty(), textRenderer, oneblockGeneratorEntity));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
