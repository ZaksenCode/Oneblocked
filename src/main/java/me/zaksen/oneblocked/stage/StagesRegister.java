package me.zaksen.oneblocked.stage;

import me.zaksen.oneblocked.util.WeightedList;

public class StagesRegister {
    private static final WeightedList<Stage> stages = new WeightedList<>();

    public static void addStage(Stage stage, int limit) {
        stages.addEntry(stage, limit);
    }

    public static Stage getStage(int brokenBlocks) {
        return stages.get(brokenBlocks);
    }

    public static void clear() {
        stages.clear();
    }

    public static WeightedList<Stage> getStages() {
        return stages;
    }
}
