package teamport.creatures.entity.render;

import net.minecraft.client.render.entity.LivingRenderer;
import teamport.creatures.entity.EntityFox;
import useless.dragonfly.helper.ModelHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class FoxRenderer extends LivingRenderer<EntityFox> {
	public FoxRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/fox.json", FoxModel.class), 0.25F);
	}
}
