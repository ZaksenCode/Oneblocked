package me.zaksen.oneblocked.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.zaksen.oneblocked.stage.Stage;
import me.zaksen.oneblocked.stage.StagesRegister;
import me.zaksen.oneblocked.stage.block.StageBlock;
import me.zaksen.oneblocked.stage.mob.StageMob;
import me.zaksen.oneblocked.util.WeightedRandomList;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ResourceListener implements SimpleSynchronousResourceReloadListener {

    private final Logger logger;

    public ResourceListener(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier("oneblocked", "stages");
    }

    @Override
    public void reload(ResourceManager manager) {
        StagesRegister.clear();

        Map<Integer, Stage> stages = new HashMap<>();

        manager.findResources("oneblock_stages", path -> path.toString().endsWith(".json")).forEach((id, res) -> {
            try(BufferedReader reader = res.getReader()) {
                JsonObject stageFile = JsonHelper.deserialize(reader);

                int length = getInt("length", stageFile);
                int order = getInt("order", stageFile);
                String identifier = getString("id", stageFile);
                String name = getString("name", stageFile);
                JsonArray blockList = getArray("block_list", stageFile);
                JsonArray mobList = getArray("mob_list", stageFile);

                Stage newStage = createStage(length, identifier, name, blockList, mobList);

                if(!stages.containsKey(order)) {
                    stages.put(order, newStage);
                    return;
                }
                logger.warn("Unable to register stage with order {} because this order is occupied", order);
            } catch (Exception e) {
                logger.error("Error occurred while loading stage {}", id.toString(), e);
            }
        });

        Map<Integer, Stage> stagesNew = new TreeMap<>(stages);

        stagesNew.values().forEach(stage -> {
            StagesRegister.addStage(stage, stage.getLength());
        });
    }

    private String getString(String name, JsonObject object) {
        JsonElement getted = object.get(name);
        return getted != null ? getted.getAsString() : null;
    }

    private int getInt(String name, JsonObject object) {
        JsonElement getted = object.get(name);
        return getted != null ? getted.getAsInt() : 0;
    }

    private double getDouble(String name, JsonObject object) {
        JsonElement getted = object.get(name);
        return getted != null ? getted.getAsDouble() : 0;
    }

    private JsonArray getArray(String name, JsonObject object) {
        JsonElement getted = object.get(name);
        return getted != null ? getted.getAsJsonArray() : new JsonArray();
    }

    private Stage createStage(int length, String id, String name, JsonArray blockList, JsonArray mobList) {
        Text stageName = Text.translatable(name);
        WeightedRandomList<StageBlock> blocks = new WeightedRandomList<>();

        blockList.forEach(blockEntry -> {
            if(blockEntry.isJsonObject()) {
                JsonObject blockObject = blockEntry.getAsJsonObject();

                String entryName = getString("entry", blockObject);
                double chance = getDouble("chance", blockObject);

                if(entryName == null) {
                    return;
                }

                if(entryName.equalsIgnoreCase("void")) {
                    blocks.addEntry(StageBlock.EMPTY, chance);
                    return;
                }

                String[] parts = entryName.split(":");

                if(parts.length != 2) {
                    throw new IllegalArgumentException("StageBlock entry name should be separated with `:` symbol");
                }

                blocks.addEntry(new StageBlock(new Identifier(parts[0], parts[1])), chance);
            }
        });

        blocks.getEntries().forEach(blockEntry -> {
            blockEntry.getObject().setPercentageChance(blockEntry.getWeight(), blocks.getAccumulatedWeight());
        });

        WeightedRandomList<StageMob> mobs = new WeightedRandomList<>();

        mobList.forEach(mobEntry -> {
            if(mobEntry.isJsonObject()) {
                JsonObject mobObject = mobEntry.getAsJsonObject();

                String entryName = getString("entry", mobObject);
                double chance = getDouble("chance", mobObject);

                if(entryName == null) {
                    return;
                }

                if(entryName.equalsIgnoreCase("void")) {
                    mobs.addEntry(StageMob.EMPTY, chance);
                    return;
                }

                String[] parts = entryName.split(":");

                if(parts.length != 2) {
                    throw new IllegalArgumentException("StageMob entry name should be separated with `:` symbol");
                }

                mobs.addEntry(new StageMob(new Identifier(parts[0], parts[1])), chance);
            }
        });

        mobs.getEntries().forEach(mobEntry -> {
            mobEntry.getObject().setPercentageChance(mobEntry.getWeight(), blocks.getAccumulatedWeight());
        });

        return new Stage(stageName, new Identifier(id), blocks, mobs, length);
    }
}
