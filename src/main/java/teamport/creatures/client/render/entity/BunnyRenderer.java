package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.client.render.entity.dragonfly.BunnyModel;
import teamport.creatures.core.entity.BunnyEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BunnyRenderer extends LivingRenderer<BunnyEntity> {
	public BunnyRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/bunny.json", BunnyModel.class), 0.25F);
	}
}
