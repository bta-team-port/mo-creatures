package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityHorseUnicorn;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class HorseUnicornRenderer extends LivingRenderer<EntityHorseUnicorn> {
	public HorseUnicornRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_unicorn.json", HorseModel.class), 0.5F);
		setRenderPassModel(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_unicorn.json", HorseModel.class));
	}

	private boolean renderSaddle(EntityHorseUnicorn entity, int renderPass) {
		this.loadTexture("/assets/creatures/entity/horse/saddle.png");
		return renderPass == 0 && entity != null && entity.saddled;
	}

	@Override
	protected boolean shouldRenderPass(EntityHorseUnicorn entity, int renderPass, float partialTick) {
		return this.renderSaddle(entity, renderPass);
	}
}
