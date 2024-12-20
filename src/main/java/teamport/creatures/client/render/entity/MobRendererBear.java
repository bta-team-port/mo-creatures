package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import teamport.creatures.core.entity.mob.MobBear;

public class MobRendererBear extends MobRenderer<MobBear> {
	public MobRendererBear(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	protected float limbSway(MobBear entity, float partialTick) {
		return entity.isBearAngry() ? (float) ((22 * Math.PI) / 45) : (float) (Math.PI / 5);
	}
}
