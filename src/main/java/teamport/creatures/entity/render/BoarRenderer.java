package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityBoar;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BoarRenderer extends LivingRenderer<EntityBoar> {
	public BoarRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/boar.json", BoarModel.class), 0.25F);
	}
}
