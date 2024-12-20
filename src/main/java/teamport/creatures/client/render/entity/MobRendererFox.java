package teamport.creatures.client.render.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.MobRenderer;
import teamport.creatures.client.render.model.ModelFox;
import teamport.creatures.core.entity.mob.MobFox;

@Environment(EnvType.CLIENT)
public class MobRendererFox extends MobRenderer<MobFox> {
	private ModelFox modelFox;

	public MobRendererFox(ModelFox modelFox, float shadowSize) {
		super(modelFox, shadowSize);
		this.modelFox = modelFox;
	}

	@Override
	protected float limbSway(MobFox entity, float partialTick) {
		return entity.isFoxAngry() ? 1.535F : (float) (Math.PI / 5);
	}
}
