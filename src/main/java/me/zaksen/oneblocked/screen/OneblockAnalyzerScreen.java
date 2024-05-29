package me.zaksen.oneblocked.screen;

import me.zaksen.oneblocked.block.custom.OneblockGeneratorEntity;
import me.zaksen.oneblocked.stage.Stage;
import me.zaksen.oneblocked.stage.StagesRegister;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class OneblockAnalyzerScreen extends Screen {

    private static final Identifier BACKGROUND = new Identifier("oneblocked", "textures/gui/aviable_widget.png");
    private final OneblockGeneratorEntity oneblockGeneratorEntity;

    public OneblockAnalyzerScreen(OneblockGeneratorEntity oneblockGeneratorEntity) {
        super(Text.translatable("screen.oneblocked.oneblock_analyzer.screen"));
        this.oneblockGeneratorEntity = oneblockGeneratorEntity;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        int width = textRenderer.getWidth(Text.translatable("screen.oneblocked.oneblock_analyzer.screen.max_blocks_broken", oneblockGeneratorEntity.getMaxBrokenBlocks())) + 16;

        context.drawNineSlicedTexture(
                BACKGROUND,
                (this.width / 2) - width / 2,
                (this.height / 2) - 40,
                width,
                80,
                4,
                256,
                256,
                0,
                0
        );

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.translatable("screen.oneblocked.oneblock_analyzer.screen.title"),
                this.width / 2,
                this.height / 2 - 35,
                16777215
        );

        long brokenBlocks = oneblockGeneratorEntity.getBrokenBlocks();

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.translatable("screen.oneblocked.oneblock_analyzer.screen.blocks_broken", brokenBlocks),
                this.width / 2,
                this.height / 2 - 15,
                16777215
        );

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.translatable("screen.oneblocked.oneblock_analyzer.screen.max_blocks_broken", oneblockGeneratorEntity.getMaxBrokenBlocks()),
                this.width / 2,
                this.height / 2 - 5,
                16777215
        );

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.translatable("screen.oneblocked.oneblock_analyzer.screen.total_blocks_broken", oneblockGeneratorEntity.getTotalBrokenBlocks()),
                this.width / 2,
                this.height / 2 + 5,
                16777215
        );

        Text isCountBlocks = oneblockGeneratorEntity.isCountBlocks() ? Text.translatable("lang.oneblocked.yes") : Text.translatable("lang.oneblocked.no");

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.translatable("screen.oneblocked.oneblock_analyzer.screen.count_blocks", isCountBlocks),
                this.width / 2,
                this.height / 2 + 15,
                16777215
        );

        Stage lastStage = StagesRegister.getStage((int) brokenBlocks);

        if(lastStage == null) {
            return;
        }

        context.drawCenteredTextWithShadow(
                textRenderer,
                Text.translatable("screen.oneblocked.oneblock_analyzer.screen.stage", lastStage.getStageName()),
                this.width / 2,
                this.height / 2 + 25,
                16777215
        );
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
