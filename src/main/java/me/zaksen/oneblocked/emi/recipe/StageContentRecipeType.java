package me.zaksen.oneblocked.emi.recipe;

import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import me.zaksen.oneblocked.block.BlocksRegister;
import me.zaksen.oneblocked.emi.OneblockedEmiPlugin;
import me.zaksen.oneblocked.stage.Stage;
import me.zaksen.oneblocked.stage.StagesRegister;
import net.minecraft.recipe.Ingredient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class StageContentRecipeType implements EmiRecipe {

    private static final int SLOT_SIZE = 18;
    private static final int ITEMS_ROW = 8;
    private static final int UP_OFFSET = 10;

    private final Stage stage;
    private final List<EmiStack> outputs;
    private final List<EmiIngredient> inputs = new ArrayList<>(){{
        add(EmiIngredient.of(Ingredient.ofStacks(BlocksRegister.ONEBLOCK_GENERATOR.asItem().getDefaultStack())).setChance(0.5f));
    }};

    public StageContentRecipeType(Stage stage) {
        this.stage = stage;

        List<EmiStack> outputs = new ArrayList<>();
        stage.getStageBlocks().getEntries().forEach(stageBlockEntry -> {
            outputs.add(EmiStack.of(stageBlockEntry.getObject().getAsBlock().asItem().getDefaultStack()).setChance((float) stageBlockEntry.getObject().getPercentageChance()));
        });
        this.outputs = outputs;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return OneblockedEmiPlugin.ONEBLOCK_CATEGORY;
    }

    @Override
    public @Nullable Identifier getId() {
        return stage.getStageId();
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return outputs;
    }

    @Override
    public int getDisplayWidth() {
        return 144;
    }

    @Override
    public int getDisplayHeight() {
        return UP_OFFSET + ((SLOT_SIZE) * (int) Math.ceil((double) outputs.size() / ITEMS_ROW));
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int rowsCount = (int) Math.ceil((double) outputs.size() / ITEMS_ROW);

        widgets.addText(stage.getStageName().copy().append(" - ").append(
                String.valueOf(StagesRegister.getStages().getObjectWeight(stage))
        ).append(" ").append(Text.translatable("lang.oneblocked.blocks")), 0, 0, 16777215, true);

        for(int y = 0; y < rowsCount; y++) {
            int completedRows = y * ITEMS_ROW;
            int maxRowIndex = Math.min(outputs.size() - completedRows, ITEMS_ROW);
            for (int x = 0; x < maxRowIndex; x++) {
                widgets.addSlot(outputs.get(completedRows + x), SLOT_SIZE * x, UP_OFFSET + (SLOT_SIZE * y)).recipeContext(this);
            }
        }
    }

}
