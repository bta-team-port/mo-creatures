package teamport.creatures;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.creatures.core.block.MMBlocks;
import teamport.creatures.core.entity.MMEntities;
import teamport.creatures.core.item.MMItemTags;
import teamport.creatures.core.item.MMItems;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class MoreMobs implements ModInitializer, GameStartEntrypoint {
    public static final String MOD_ID = "creatures";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// IDEAS LIST
	// Baby animals
	// Handcannon bunnies
	// TNT bunnies
	// Seahorses
	// Horse armor
	// Bee swarms

	@Override
	public void onInitialize() {
		LOGGER.info("Mo Creatures has been initialized.");
	}

	@Override
	public void beforeGameStart() {
		new MMBlocks();
		new MMItems();

		MMEntities.initEntities();
	}

	@Override
	public void afterGameStart() {
		MMItems.addItemsToTags();
	}
}
