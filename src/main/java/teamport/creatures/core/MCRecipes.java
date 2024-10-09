package teamport.creatures.core;

import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.helper.recipeBuilders.RecipeBuilderShaped;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCRecipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
		new RecipeBuilderShaped(MOD_ID)
			.setShape("121", "111")
			.addInput('1', "minecraft:planks")
			.addInput('2', Block.sand)
			.create("litterbox", MCItems.LITTERBOX.getDefaultStack());
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}
}
