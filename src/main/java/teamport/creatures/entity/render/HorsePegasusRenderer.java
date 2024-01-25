package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityHorsePegasus;
import teamport.creatures.entity.EntityHorseUnicorn;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class HorsePegasusRenderer extends LivingRenderer<EntityHorsePegasus> {
	public HorsePegasusRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_pegasus.json", HorsePegasusModel.class), 0.5F);
		setRenderPassModel(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_pegasus.json", HorsePegasusModel.class));
	}

	private boolean renderSaddle(EntityHorsePegasus entity, int renderPass) {
		this.loadTexture("/assets/creatures/entity/horse/saddle_4.png");
		return renderPass == 0 && entity != null && entity.saddled;
	}

	@Override
	protected boolean shouldRenderPass(EntityHorsePegasus entity, int renderPass, float partialTick) {
		return this.renderSaddle(entity, renderPass);
	}
}
