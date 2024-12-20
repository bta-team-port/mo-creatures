package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;
import teamport.creatures.core.entity.mob.MobBird;

public class MobRendererBird extends MobRenderer<MobBird> {
	public MobRendererBird(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}

	@Override
	protected float limbSway(MobBird entity, float partialTick) {
		float flap = entity.oFlap + (entity.flap - entity.oFlap) * partialTick;
		float flapSpeed = entity.oFlapSpeed + (entity.flapSpeed - entity.oFlapSpeed) * partialTick;
		return (MathHelper.sin(flap) + 1) * flapSpeed;
	}
}
