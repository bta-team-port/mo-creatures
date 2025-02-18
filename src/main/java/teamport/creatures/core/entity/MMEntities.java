package teamport.creatures.core.entity;

import net.minecraft.core.util.collection.NamespaceID;
import teamport.creatures.client.render.entity.MobRendererBear;
import teamport.creatures.client.render.entity.MobRendererBird;
import teamport.creatures.client.render.entity.MobRendererBunny;
import teamport.creatures.client.render.entity.MobRendererFox;
import teamport.creatures.client.render.model.ModelBear;
import teamport.creatures.client.render.model.ModelBird;
import teamport.creatures.client.render.model.ModelBunny;
import teamport.creatures.client.render.model.ModelFox;
import teamport.creatures.core.entity.mob.MobBear;
import teamport.creatures.core.entity.mob.MobBird;
import teamport.creatures.core.entity.mob.MobBunny;
import teamport.creatures.core.entity.mob.MobFox;
import turniplabs.halplibe.helper.EntityHelper;

public final class MMEntities {
	public static void initEntities() {
		EntityHelper.createEntity(MobBear.class,
			new NamespaceID("creatures", "bear"),
			"Bear",
			() -> new MobRendererBear(new ModelBear(), 1));

		EntityHelper.createEntity(MobBird.class,
			new NamespaceID("creatures", "bird"),
			"Bird",
			() -> new MobRendererBird(new ModelBird(), 0.25f));

		EntityHelper.createEntity(MobFox.class,
			new NamespaceID("creatures", "fox"),
			"Fox",
			() -> new MobRendererFox(new ModelFox(0), 0.5f));

		EntityHelper.createEntity(MobBunny.class,
			new NamespaceID("creatures", "bunny"),
			"Bunny",
			() -> new MobRendererBunny(new ModelBunny(), 0.3f));

		/*
		EntityHelper.createEntity(MobHorse.class, nextID(), "Horse", HorseRenderer::new);
		EntityHelper.createEntity(MobHorseUnicorn.class, nextID(), "Unicorn", UnicornRenderer::new);
		EntityHelper.createEntity(MobHorsePegasus.class, nextID(), "Pegasus", PegasusRenderer::new);
		EntityHelper.createEntity(MobDeer.class, nextID(), "Deer", DeerRenderer::new);
		EntityHelper.createEntity(MobKitty.class, nextID(), "Kitty", KittyRenderer::new);
		*/
	}
}
