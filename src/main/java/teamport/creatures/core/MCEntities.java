package teamport.creatures.core;

import teamport.creatures.MCConfig;
import teamport.creatures.client.render.entity.*;
import teamport.creatures.core.entity.*;
import turniplabs.halplibe.helper.EntityHelper;

public class MCEntities {
	private static int startingID = MCConfig.cfg.getInt("IDs.startingEntityID");
	private static int nextID() {
		return ++startingID;
	}

	public static void initEntities() {
		EntityHelper.createEntity(FoxEntity.class, nextID(), "Fox", FoxRenderer::new);
		EntityHelper.createEntity(ArcticFoxEntity.class, nextID(), "ArcticFox", FoxRenderer::new);
		EntityHelper.createEntity(BoarEntity.class, nextID(), "Boar", BoarRenderer::new);
		EntityHelper.createEntity(BunnyEntity.class, nextID(), "Bunny", BunnyRenderer::new);
		EntityHelper.createEntity(BirdEntity.class, nextID(), "Bird", BirdRenderer::new);
		EntityHelper.createEntity(HorseEntity.class, nextID(), "Horse", HorseRenderer::new);
		EntityHelper.createEntity(UnicornEntity.class, nextID(), "Unicorn", UnicornRenderer::new);
		EntityHelper.createEntity(PegasusEntity.class, nextID(), "Pegasus", PegasusRenderer::new);
		EntityHelper.createEntity(BearEntity.class, nextID(), "Bear", BearRenderer::new);
		EntityHelper.createEntity(PolarBearEntity.class, nextID(), "PolarBear", BearRenderer::new);
		EntityHelper.createEntity(DeerEntity.class, nextID(), "Deer", DeerRenderer::new);
		EntityHelper.createEntity(DuckEntity.class, nextID(), "Duck", DuckRenderer::new);
		EntityHelper.createEntity(KittyEntity.class, nextID(), "Kitty", KittyRenderer::new);
	}
}
