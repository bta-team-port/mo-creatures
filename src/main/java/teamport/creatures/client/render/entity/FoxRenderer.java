package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.client.render.entity.dragonfly.FoxModel;
import teamport.creatures.core.entity.FoxEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class FoxRenderer extends LivingRenderer<FoxEntity> {
	public FoxRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/fox.json", FoxModel.class), 0.25F);
	}
}
