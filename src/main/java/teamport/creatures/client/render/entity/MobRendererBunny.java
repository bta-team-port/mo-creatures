package teamport.creatures.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import net.minecraft.client.render.model.ModelBase;
import teamport.creatures.core.entity.mob.MobBunny;

@Environment(EnvType.CLIENT)
public class MobRendererBunny extends MobRenderer<MobBunny> {
	public MobRendererBunny(ModelBase model, float shadowSize) {
		super(model, shadowSize);
	}
}
