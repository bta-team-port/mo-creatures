package teamport.creatures.entity.render;

import net.minecraft.core.entity.EntityLiving;
import teamport.creatures.entity.EntityHorsePegasus;
import useless.dragonfly.helper.AnimationHelper;
import useless.dragonfly.model.entity.animation.Animation;

import static teamport.creatures.MoCreatures.MOD_ID;

public class HorsePegasusModel extends HorseModel {
	public static EntityHorsePegasus pegasus;
	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
		if (entityliving instanceof EntityHorsePegasus) pegasus = (EntityHorsePegasus) entityliving;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		Animation flyAnim = AnimationHelper.getOrCreateEntityAnimation(MOD_ID, "horse_pegasus.animation");
		if (pegasus != null)
			animate(pegasus.flyState, flyAnim.getAnimations().get("animation.horse.pegasus"), limbPitch, 1.0F);
	}
}
