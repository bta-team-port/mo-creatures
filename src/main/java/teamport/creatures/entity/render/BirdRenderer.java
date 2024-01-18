package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityBird;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BirdRenderer extends LivingRenderer<EntityBird> {
	public BirdRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/bird.json", BirdModel.class), 0.25F);
	}
}
