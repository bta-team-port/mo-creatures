package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityHorse;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class HorseRenderer extends LivingRenderer<EntityHorse> {
	public HorseRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse.json", HorseModel.class), 0.5F);
		setRenderPassModel(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse.json", HorseModel.class));
	}

	private boolean renderSaddle(EntityHorse entity, int renderPass) {
		this.loadTexture("/assets/creatures/entity/horse/saddle.png");
		return renderPass == 0 && entity != null && entity.saddled;
	}

	@Override
	protected boolean shouldRenderPass(EntityHorse entity, int renderPass, float partialTick) {
		return this.renderSaddle(entity, renderPass);
	}
}
