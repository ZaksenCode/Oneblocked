package me.zaksen.oneblocked.emi;

import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiStack;
import me.zaksen.oneblocked.block.BlocksRegister;
import me.zaksen.oneblocked.emi.recipe.StageContentRecipeType;
import me.zaksen.oneblocked.stage.Stage;
import me.zaksen.oneblocked.stage.StagesRegister;
import net.minecraft.util.Identifier;

public class OneblockedEmiPlugin implements EmiPlugin {
    public static final Identifier EMI_SPRITE_SHEET = new Identifier("oneblocked", "textures/gui/emi_simplified_textures.png");
    public static final EmiStack ONEBLOCK_STACK = EmiStack.of(BlocksRegister.ONEBLOCK_GENERATOR_ITEM);
    public static final EmiRecipeCategory ONEBLOCK_CATEGORY
            = new EmiRecipeCategory(new Identifier("oneblocked", "oneblock_generator"), ONEBLOCK_STACK, new EmiTexture(EMI_SPRITE_SHEET, 0, 0, 16, 16));

    @Override
    public void register(EmiRegistry registry) {
        registry.addCategory(ONEBLOCK_CATEGORY);
        registry.addWorkstation(ONEBLOCK_CATEGORY, ONEBLOCK_STACK);

        StagesRegister.getStages().forEach(stageEntry -> {
            Stage stage = stageEntry.getObject();
            registry.addRecipe(new StageContentRecipeType(stage));
        });
    }
}
