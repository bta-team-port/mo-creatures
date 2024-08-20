package teamport.creatures.core;

import teamport.creatures.client.render.entity.*;
import teamport.creatures.core.entity.*;
import turniplabs.halplibe.helper.EntityHelper;

public class MCEntities {
	public static void initEntities() {
		EntityHelper.createEntity(FoxEntity.class, 100, "Fox", FoxRenderer::new);
		EntityHelper.createEntity(ArcticFoxEntity.class, 101, "ArcticFox", FoxRenderer::new);
		EntityHelper.createEntity(BoarEntity.class, 102, "Boar", BoarRenderer::new);
		EntityHelper.createEntity(BunnyEntity.class, 103, "Bunny", BunnyRenderer::new);
		EntityHelper.createEntity(BirdEntity.class, 104, "Bird", BirdRenderer::new);
		EntityHelper.createEntity(HorseEntity.class, 105, "Horse", HorseRenderer::new);
		EntityHelper.createEntity(UnicornEntity.class, 106, "Unicorn", UnicornRenderer::new);
		EntityHelper.createEntity(PegasusEntity.class, 107, "Pegasus", PegasusRenderer::new);
		EntityHelper.createEntity(BearEntity.class, 108, "Bear", BearRenderer::new);
		EntityHelper.createEntity(PolarBearEntity.class, 109, "PolarBear", BearRenderer::new);
		EntityHelper.createEntity(DeerEntity.class, 110, "Deer", DeerRenderer::new);
		EntityHelper.createEntity(DuckEntity.class, 111, "Duck", DuckRenderer::new);
		EntityHelper.createEntity(KittyEntity.class, 112, "Kitty", KittyRenderer::new);
	}
}
