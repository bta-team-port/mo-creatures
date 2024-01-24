package teamport.creatures.entity.render;

import net.minecraft.core.util.helper.MathHelper;
import useless.dragonfly.model.entity.BenchEntityModel;

public class HorseModel extends BenchEntityModel {
	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> benchEntityBones.resetPose());
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		if (this.getIndexBones().containsKey("head")) {
			this.getIndexBones().get("head")
				.rotateAngleX = 0.30F;
		}

		if (this.getIndexBones().containsKey("neck")) {
			this.getIndexBones().get("neck")
				.rotateAngleX = 0.60F;
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
				.rotateAngleX = -0.90F;

			this.getIndexBones().get("tail")
				.rotateAngleY = MathHelper.cos(limbSwing * 0.6662F) * 0.5F * -0.55F;
		}
	}
}
