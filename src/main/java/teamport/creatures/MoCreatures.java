package teamport.creatures;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.gui.guidebook.mobs.MobInfoRegistry;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import teamport.creatures.core.MCBlocks;
import teamport.creatures.core.MCEntities;
import teamport.creatures.core.MCItems;
import teamport.creatures.core.MCSounds;
import teamport.creatures.core.entity.*;
import turniplabs.halplibe.util.ClientStartEntrypoint;
import turniplabs.halplibe.util.GameStartEntrypoint;


public class MoCreatures implements ModInitializer, GameStartEntrypoint, ClientStartEntrypoint {
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
		MCBlocks.initializeBlocks();
		MCBlocks.initializeTiles();
		MCItems.initializeItems();

		MCEntities.initEntities();
		MCSounds.initializeSounds();
	}

	@Override
	public void afterGameStart() {
		MobInfoRegistry.register(BearEntity.class,
			"guidebook.section.mob.bear.name",
			"guidebook.section.mob.bear.desc",
			40,
			10,
			null);

		MobInfoRegistry.register(PolarBearEntity.class,
			"guidebook.section.mob.polarbear.name",
			"guidebook.section.mob.polarbear.desc",
			40,
			10,
			null);

		MobInfoRegistry.register(BirdEntity.class,
			"guidebook.section.mob.bird.name",
			"guidebook.section.mob.bird.desc",
			5,
			10,
			null);

		MobInfoRegistry.register(BoarEntity.class,
			"guidebook.section.mob.boar.name",
			"guidebook.section.mob.boar.desc",
			10,
			10,
			new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(Item.foodPorkchopRaw.getDefaultStack(), 1.0f, 1, 2)});

		MobInfoRegistry.register(BunnyEntity.class,
			"guidebook.section.mob.bunny.name",
			"guidebook.section.mob.bunny.desc",
			5,
			10,
			null);

		MobInfoRegistry.register(DeerEntity.class,
			"guidebook.section.mob.deer.name",
			"guidebook.section.mob.deer.desc",
			10,
			10,
			new MobInfoRegistry.MobDrop[]{new MobInfoRegistry.MobDrop(Item.foodPorkchopRaw.getDefaultStack(), 1.0f, 1, 2)});

		MobInfoRegistry.register(FoxEntity.class,
			"guidebook.section.mob.fox.name",
			"guidebook.section.mob.fox.desc",
			10,
			10,
			null);

		MobInfoRegistry.register(ArcticFoxEntity.class,
			"guidebook.section.mob.arcticfox.name",
			"guidebook.section.mob.arcticfox.desc",
			10,
			10,
			null);

		MobInfoRegistry.register(HorseEntity.class,
			"guidebook.section.mob.horse.name",
			"guidebook.section.mob.horse.desc",
			20,
			10,
			null);

		MobInfoRegistry.register(PegasusEntity.class,
			"guidebook.section.mob.pegasus.name",
			"guidebook.section.mob.pegasus.desc",
			20,
			10,
			null);

		MobInfoRegistry.register(UnicornEntity.class,
			"guidebook.section.mob.unicorn.name",
			"guidebook.section.mob.unicorn.desc",
			20,
			10,
			null);

		MobInfoRegistry.register(KittyEntity.class,
			"guidebook.section.mob.kitty.name",
			"guidebook.section.mob.kitty.desc",
			10,
			10,
			null);
	}

	@Override
	public void beforeClientStart() {
		MCBlocks.initializeTiles();
	}

	@Override
	public void afterClientStart() {

	}
}
