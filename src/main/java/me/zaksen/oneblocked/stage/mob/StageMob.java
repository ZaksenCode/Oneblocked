package me.zaksen.oneblocked.stage.mob;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class StageMob {

    public final static StageMob EMPTY = new StageMob(new Identifier("minecraft", "item"));

    private final Identifier mobId;
    private double percentageChance = 0;
    private Text percentageText = Text.of("");

    public StageMob(Identifier identifier) {
        this.mobId = identifier;
    }

    public EntityType getAsEntityType() {
        return Registries.ENTITY_TYPE.get(mobId);
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
