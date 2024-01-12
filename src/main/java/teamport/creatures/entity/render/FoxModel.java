package teamport.creatures.entity.render;

import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.model.entity.BenchEntityModel;

public class FoxModel extends BenchEntityModel {
	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> benchEntityBones.resetPose());
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		if (this.getIndexBones().containsKey("head")) {
			this.getIndexBones().get("head")
				.setRotationAngle((float)Math.toRadians(headPitch), (float)Math.toRadians(headYaw), 0.0F);
		}

		if (this.getIndexBones().containsKey("legFrontLeft")) {
			this.getIndexBones().get("legFrontLeft")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("legFrontRight")) {
			this.getIndexBones().get("legFrontRight")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("legBackLeft")) {
			this.getIndexBones().get("legBackLeft")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("legBackRight")) {
			this.getIndexBones().get("legBackRight")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("tail")) {
			this.getIndexBones().get("tail")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 0.7F * -0.55F;
		}
	}
}
