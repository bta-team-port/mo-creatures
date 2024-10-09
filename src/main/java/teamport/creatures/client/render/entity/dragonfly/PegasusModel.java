package teamport.creatures.client.render.entity.dragonfly;

import net.minecraft.core.entity.EntityLiving;
import org.useless.dragonfly.helper.AnimationHelper;
import org.useless.dragonfly.model.entity.animation.Animation;
import teamport.creatures.core.entity.PegasusEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class PegasusModel extends HorseModel {
	public static PegasusEntity pegasus;

	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
		if (entityliving instanceof PegasusEntity) pegasus = (PegasusEntity) entityliving;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		Animation flyAnim = AnimationHelper.getOrCreateEntityAnimation(MOD_ID, "horse_pegasus.animation");
		if (pegasus != null)
			animate(pegasus.flyState, flyAnim.getAnimations().get("animation.horse.pegasus"), limbPitch, 1.0F);
	}
}
