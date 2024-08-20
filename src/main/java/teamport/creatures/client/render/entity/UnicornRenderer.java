package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.core.entity.UnicornEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class UnicornRenderer extends LivingRenderer<UnicornEntity> {
	public UnicornRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_unicorn.json", HorseModel.class), 0.5F);
		setRenderPassModel(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/horse_unicorn.json", HorseModel.class));
	}

	private boolean renderSaddle(UnicornEntity entity, int renderPass) {
		this.loadTexture("/assets/creatures/textures/entity/horse/saddle.png");
		return renderPass == 0 && entity != null && entity.saddled;
	}

	@Override
	protected boolean shouldRenderPass(UnicornEntity entity, int renderPass, float partialTick) {
		return this.renderSaddle(entity, renderPass);
	}
}
