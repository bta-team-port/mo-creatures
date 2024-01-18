package teamport.creatures.entity.render;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import teamport.creatures.entity.EntityBird;
import useless.dragonfly.helper.AnimationHelper;
import useless.dragonfly.model.entity.BenchEntityModel;
import useless.dragonfly.model.entity.animation.Animation;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BirdModel extends BenchEntityModel {
	public static EntityBird bird;

	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
		if (entityliving instanceof EntityBird) bird = (EntityBird) entityliving;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> benchEntityBones.resetPose());
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		if (this.getIndexBones().containsKey("head")) {
			this.getIndexBones().get("head")
				.setRotationAngle((float)Math.toRadians(headPitch), (float)Math.toRadians(headYaw), 0.0F);
		}

		if (this.getIndexBones().containsKey("body")) {
			this.getIndexBones().get("body")
				.rotateAngleX = -0.20F;
		}

		if (this.getIndexBones().containsKey("tail")) {
			this.getIndexBones().get("tail")
				.rotateAngleX = 0.25F;
		}

		if (this.getIndexBones().containsKey("legLeft")) {
			this.getIndexBones().get("legLeft")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("legRight")) {
			this.getIndexBones().get("legRight")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbYaw;
		}

		Animation flyAnim = AnimationHelper.getOrCreateEntityAnimation(MOD_ID, "bird.animation");
		if (bird != null)
			animate(bird.flyState, flyAnim.getAnimations().get("animation.bird.flying"), limbPitch, 1.0F);
	}
}
