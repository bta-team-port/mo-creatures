package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.core.entity.BirdEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BirdRenderer extends LivingRenderer<BirdEntity> {
	public BirdRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/bird.json", BirdModel.class), 0.25F);
	}
}
