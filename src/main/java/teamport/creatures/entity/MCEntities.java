package teamport.creatures.entity;

import teamport.creatures.entity.render.BirdRenderer;
import teamport.creatures.entity.render.BoarRenderer;
import teamport.creatures.entity.render.BunnyRenderer;
import teamport.creatures.entity.render.FoxRenderer;
import turniplabs.halplibe.helper.EntityHelper;

public class MCEntities {

	public static void initializeEntities() {
		// Foxes
		EntityHelper.Core.createEntity(EntityFox.class, 255, "fox");
		EntityHelper.Client.assignEntityRenderer(EntityFox.class, new FoxRenderer());
		EntityHelper.Core.createEntity(EntityFoxArctic.class, 256, "arcticfox");
		EntityHelper.Client.assignEntityRenderer(EntityFoxArctic.class, new FoxRenderer());

		// THE HOG
		EntityHelper.Core.createEntity(EntityBoar.class, 257, "boar");
		EntityHelper.Client.assignEntityRenderer(EntityBoar.class, new BoarRenderer());

		// Bunnies
		EntityHelper.Core.createEntity(EntityBunny.class, 258, "bunny");
		EntityHelper.Client.assignEntityRenderer(EntityBunny.class, new BunnyRenderer());

		// Birds
		EntityHelper.Core.createEntity(EntityBird.class, 259, "bird");
		EntityHelper.Client.assignEntityRenderer(EntityBird.class, new BirdRenderer());
	}
}
