package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.core.entity.PegasusEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class PegasusRenderer extends LivingRenderer<PegasusEntity> {
	public PegasusRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_pegasus.json", PegasusModel.class), 0.5F);
		setRenderPassModel(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_pegasus.json", PegasusModel.class));
	}

	private boolean renderSaddle(PegasusEntity entity, int renderPass) {
		this.loadTexture("/assets/creatures/textures/entity/horse/saddle_4.png");
		return renderPass == 0 && entity != null && entity.saddled;
	}

	@Override
	protected boolean shouldRenderPass(PegasusEntity entity, int renderPass, float partialTick) {
		return this.renderSaddle(entity, renderPass);
	}
}
