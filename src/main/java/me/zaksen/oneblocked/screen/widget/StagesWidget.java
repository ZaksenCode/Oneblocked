package me.zaksen.oneblocked.screen.widget;

import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import me.zaksen.oneblocked.network.NetworkConstants;
import me.zaksen.oneblocked.stage.Stage;
import me.zaksen.oneblocked.stage.StagesRegister;
import me.zaksen.oneblocked.util.WeightedList;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.widget.ScrollableWidget;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class StagesWidget extends ScrollableWidget {

    private static final Identifier AVIABLE_TEXTURE = new Identifier("oneblocked", "textures/gui/aviable_widget.png");
    private static final Identifier UNAVIABLE_TEXTURE = new Identifier("oneblocked", "textures/gui/unaviable_widget.png");
    private static final Identifier AVIABLE_TEXTURE_HOVER = new Identifier("oneblocked", "textures/gui/aviable_widget_hover.png");
    private static final Identifier UNAVIABLE_TEXTURE_HOVER = new Identifier("oneblocked", "textures/gui/unaviable_widget_hover.png");
    private static final Identifier SCROLL_BACKGROUND_WIDGET = new Identifier("oneblocked", "textures/gui/scrollable_widget.png");

    private final static int STAGE_ELEMENT_GAP = 4;

    private final static int STAGE_ELEMENT_HEIGHT = 32;
    private final static int STAGE_ELEMENT_WIDTH = 144;

    private final OneblockGeneratorEntity entity;
    private final TextRenderer textRenderer;

    private final List<StageSubWidget> stages = new ArrayList<>();

    public StagesWidget(int x, int y, int width, int height, Text text, TextRenderer textRenderer, OneblockGeneratorEntity entity) {
        super(x, y, width, height, text);
        this.textRenderer = textRenderer;
        this.entity = entity;

        for(int i = 0; i < StagesRegister.getStages().size(); i++) {
            WeightedList<Stage>.Entry stageEntry = StagesRegister.getStages().getEntryIndex(i);

            stages.add(new StageSubWidget(stageEntry.getObject(), 20, 20 + (i * (STAGE_ELEMENT_HEIGHT + STAGE_ELEMENT_GAP)), stageEntry.getAccumulatedWeight()));
        }
    }

    @Override
    protected int getContentsHeight() {
        return StagesRegister.getStages().size() * (STAGE_ELEMENT_HEIGHT + STAGE_ELEMENT_GAP) + 10;
    }

    @Override
    protected double getDeltaYPerScroll() {
        return STAGE_ELEMENT_HEIGHT + STAGE_ELEMENT_GAP;
    }

    @Override
    protected void renderContents(DrawContext context, int mouseX, int mouseY, float delta) {
        long blocksCount = entity.getMaxBrokenBlocks();
        stages.forEach(stageWidget -> stageWidget.render(context, mouseX, mouseY, blocksCount));
    }

    protected void renderMenuBackground(DrawContext context) {
        context.drawNineSlicedTexture(
                SCROLL_BACKGROUND_WIDGET,
                getX(),
                getY(),
                width,
                height,
                5,
                5,
                5,
                5,
                256,
                256,
                0,
                0
        );
    }

    @Override
    public void renderButton(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.visible) {
            renderMenuBackground(context);
            context.enableScissor(this.getX() + 5, this.getY() + 5, this.getX() + this.width - 5, this.getY() + this.height - 5);
            context.getMatrices().push();
            context.getMatrices().translate(0.0, -this.getScrollY(), 0.0);
            this.renderContents(context, mouseX, mouseY, delta);
            context.getMatrices().pop();
            context.disableScissor();
            this.renderOverlay(context);
        }
    }

    @Override
    protected void appendClickableNarrations(NarrationMessageBuilder builder) {

    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        stages.forEach(StageSubWidget::onClick);
        return super.mouseClicked(mouseX, mouseY, button);
    }

    private class StageSubWidget {
        private final Stage stage;
        private final int x;
        private final int y;
        private final int accumulatedWeight;
        private boolean hovered;

        public StageSubWidget(Stage stage, int x, int y, int accumulatedWeight) {
            this.stage = stage;
            this.x = x;
            this.y = y;
            this.accumulatedWeight = accumulatedWeight;
        }

        public Stage getStage() {
            return stage;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void render(DrawContext context, int mouseX, int mouseY, long blocksBroken) {
            hovered = mouseX >= this.getX() && mouseY >= this.getY() - getScrollY() && mouseX < this.getX() + STAGE_ELEMENT_WIDTH && mouseY < this.getY() + STAGE_ELEMENT_HEIGHT - getScrollY();

            if(blocksBroken >= accumulatedWeight) {
                if(isHovered()) {
                    context.drawNineSlicedTexture(
                            AVIABLE_TEXTURE_HOVER,
                            x,
                            y,
                            STAGE_ELEMENT_WIDTH,
                            STAGE_ELEMENT_HEIGHT,
                            4,
                            256,
                            256,
                            0,
                            0
                    );

                    context.drawText(
                            textRenderer,
                            stage.getStageName(),
                            x + 5,
                            y + 5,
                            16777215,
                            true
                    );

                    return;
                }

                context.drawNineSlicedTexture(
                        AVIABLE_TEXTURE,
                        x,
                        y,
                        STAGE_ELEMENT_WIDTH,
                        STAGE_ELEMENT_HEIGHT,
                        4,
                        256,
                        256,
                        0,
                        0
                );

                context.drawText(
                        textRenderer,
                        stage.getStageName(),
                        x + 5,
                        y + 5,
                        16777215,
                        true
                );

                return;
            }

            if(isHovered()) {
                context.drawNineSlicedTexture(
                        UNAVIABLE_TEXTURE_HOVER,
                        x,
                        y,
                        STAGE_ELEMENT_WIDTH,
                        STAGE_ELEMENT_HEIGHT,
                        4,
                        256,
                        256,
                        0,
                        0
                );

                context.drawText(
                        textRenderer,
                        stage.getStageName(),
                        x + 5,
                        y + 5,
                        11184810,
                        true
                );

                return;
            }

            context.drawNineSlicedTexture(
                    UNAVIABLE_TEXTURE,
                    x,
                    y,
                    STAGE_ELEMENT_WIDTH,
                    STAGE_ELEMENT_HEIGHT,
                    4,
                    256,
                    256,
                    0,
                    0
            );

            context.drawText(
                    textRenderer,
                    stage.getStageName(),
                    x + 5,
                    y + 5,
                    11184810,
                    true
            );

        }

        public boolean isHovered() {
            return hovered;
        }

        public void onClick() {
            if(isHovered()) {
                PacketByteBuf bytes = PacketByteBufs.create();
                bytes.writeBlockPos(entity.getPos());
                bytes.writeLong(accumulatedWeight - stage.getLength() + 1);
                ClientPlayNetworking.send(NetworkConstants.CHANGE_BLOCKS_BROKEN_PACKET_ID, bytes);
            }
        }
    }
}
