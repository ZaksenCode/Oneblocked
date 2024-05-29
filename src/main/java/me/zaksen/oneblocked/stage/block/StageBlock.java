package me.zaksen.oneblocked.stage.block;

import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class StageBlock {
    public final static StageBlock EMPTY = new StageBlock(new Identifier("minecraft", "air"));

    private final Identifier blockId;
    private double percentageChance = 0;
    private Text percentageText = Text.of("");

    public StageBlock(Identifier identifier) {
        this.blockId = identifier;
    }

    public Block getAsBlock() {
        return Registries.BLOCK.get(blockId);
    }

    public Text getPercentageText() {
        return percentageText;
    }

    public double getPercentageChance() {
        return percentageChance;
    }

    public void setPercentageChance(double chance, double maxWeight) {
        this.percentageChance = chance / maxWeight;
        this.percentageText = Text.of((int) Math.ceil(percentageChance * 100) + "%");
    }
}
