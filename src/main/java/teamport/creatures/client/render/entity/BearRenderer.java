package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.core.entity.BearEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BearRenderer extends LivingRenderer<BearEntity> {
	public BearRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/bear.json", BearModel.class), 0.75F);
	}
}
