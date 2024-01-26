package teamport.creatures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.creatures.entity.MCEntities;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;


public class MoCreatures implements GameStartEntrypoint, ClientStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "creatures";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// IDEAS LIST //
	// Hand Cannon Bunnies
	// TNT Bunnies

	@Override
	public void beforeGameStart() {
		MCEntities.initEntityCore();

		LOGGER.info("Mo Creatures has been initialized.");
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void beforeClientStart() {
		MCSounds.initializeSounds();
		MCEntities.initEntityClient();
	}

	@Override
	public void afterClientStart() {

	}

	@Override
	public void onRecipesReady() {

	}
}
