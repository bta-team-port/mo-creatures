package teamport.creatures.client.render.entity;

import net.minecraft.client.render.entity.LivingRenderer;
import org.useless.dragonfly.helper.ModelHelper;
import teamport.creatures.client.render.entity.dragonfly.KittyModel;
import teamport.creatures.core.entity.KittyEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class KittyRenderer extends LivingRenderer<KittyEntity> {
	public KittyRenderer() {
		super(ModelHelper.getOrCreateEntityModel(MOD_ID, "entity/kitty.json", KittyModel.class), 0.25F);
	}
}
