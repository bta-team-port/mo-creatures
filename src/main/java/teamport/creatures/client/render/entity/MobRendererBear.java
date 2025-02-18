package teamport.creatures.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import teamport.creatures.core.entity.mob.MobBear;

@Environment(EnvType.CLIENT)
public class MobRendererBear extends MobRenderer<MobBear> {
	public MobRendererBear(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	protected float limbSway(MobBear entity, float partialTick) {
		return entity.isBearAngry() ? (float) ((22 * Math.PI) / 45) : (float) (Math.PI / 5);
	}
}
