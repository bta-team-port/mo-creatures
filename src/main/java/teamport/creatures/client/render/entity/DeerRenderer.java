package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.core.entity.DeerEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class DeerRenderer extends LivingRenderer<DeerEntity> {

	public DeerRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/deer.json", DeerModel.class), 0.75f);
	}
}
