package teamport.creatures.core;

import turniplabs.halplibe.helper.RecipeBuilder;
import turniplabs.halplibe.util.RecipeEntrypoint;

import static teamport.creatures.MoreMobs.MOD_ID;

public class MMRecipes implements RecipeEntrypoint {
	@Override
	public void onRecipesReady() {
	}

	@Override
	public void initNamespaces() {
		RecipeBuilder.initNameSpace(MOD_ID);
	}
}
