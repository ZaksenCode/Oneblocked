package me.zaksen.oneblocked.stage;

import me.zaksen.oneblocked.stage.block.StageBlock;
import me.zaksen.oneblocked.stage.mob.StageMob;
import me.zaksen.oneblocked.util.WeightedRandomList;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Stage {
    private final Text stageName;
    private final Identifier stageId;
    private final WeightedRandomList<StageBlock> stageBlocks;
    private final WeightedRandomList<StageMob> stageMobs;
    private final int length;

    public Stage(Text stageName, Identifier stageId, WeightedRandomList<StageBlock> stageBlocks, WeightedRandomList<StageMob> stageMobs, int length) {
        this.stageName = stageName;
        this.stageId = stageId;
        this.stageBlocks = stageBlocks;
        this.stageMobs = stageMobs;
        this.length = length;
    }

    public Text getStageName() {
        return stageName;
    }

    public Identifier getStageId() {
        return stageId;
    }

    public StageBlock getRandomBlock() {
        return stageBlocks.getRandom();
    }

    public StageMob getRandomMob() {
        return stageMobs.getRandom();
    }

    public WeightedRandomList<StageBlock> getStageBlocks() {
        return stageBlocks;
    }

    public WeightedRandomList<StageMob> getStageMobs() {
        return stageMobs;
    }

    public int getLength() {
        return length;
    }
}
