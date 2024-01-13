package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityBunny;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BunnyRenderer extends LivingRenderer<EntityBunny> {
	public BunnyRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/bunny.json", BunnyModel.class), 0.25F);
	}
}
