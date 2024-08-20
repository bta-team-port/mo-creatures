package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.core.entity.BoarEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BoarRenderer extends LivingRenderer<BoarEntity> {
	public BoarRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/boar.json", BoarModel.class), 0.25F);
	}
}
