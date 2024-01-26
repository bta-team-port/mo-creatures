package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityBear;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BearRenderer extends LivingRenderer<EntityBear> {
	public BearRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/bear.json", BearModel.class), 0.75F);
	}
}
