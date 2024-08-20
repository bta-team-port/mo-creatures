package teamport.creatures;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.creatures.core.MCBlocks;
import teamport.creatures.core.MCEntities;
import teamport.creatures.core.MCItems;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class MoCreatures implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
    public static final String MOD_ID = "creatures";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// IDEAS LIST
	// Baby animals
	// Handcannon bunnies
	// TNT bunnies

	@Override
	public void onInitialize() {
		LOGGER.info("Mo Creatures has been initialized.");
	}

	@Override
	public void beforeGameStart() {
		MCBlocks.initializeBlocks();
		MCBlocks.initializeTiles();
		MCItems.initializeItems();

		MCEntities.initEntities();
		MCSounds.initializeSounds();
	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void beforeClientStart() {
		MCBlocks.initializeTiles();
	}

	@Override
	public void afterClientStart() {

	}
}
