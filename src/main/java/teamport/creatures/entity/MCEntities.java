package teamport.creatures.entity;

import teamport.creatures.entity.render.*;
import turniplabs.halplibe.helper.EntityHelper;

public class MCEntities {
	public static void initEntityCore() {
		EntityHelper.Core.createEntity(EntityFox.class, 255, "fox");
		EntityHelper.Core.createEntity(EntityFoxArctic.class, 256, "arcticfox");
		EntityHelper.Core.createEntity(EntityBoar.class, 257, "boar");
		EntityHelper.Core.createEntity(EntityBunny.class, 258, "bunny");
		EntityHelper.Core.createEntity(EntityBird.class, 259, "bird");
		EntityHelper.Core.createEntity(EntityHorse.class, 260, "horse");
		EntityHelper.Core.createEntity(EntityHorseUnicorn.class, 261, "unicorn");
	}

	public static void initEntityClient() {
		EntityHelper.Client.assignEntityRenderer(EntityFox.class, new FoxRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityFoxArctic.class, new FoxRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBoar.class, new BoarRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBunny.class, new BunnyRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityBird.class, new BirdRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityHorse.class, new HorseRenderer());
		EntityHelper.Client.assignEntityRenderer(EntityHorseUnicorn.class, new HorseUnicornRenderer());
	}
}
