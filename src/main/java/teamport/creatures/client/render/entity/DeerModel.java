package teamport.creatures.client.render.entity;

import net.minecraft.core.util.helper.MathHelper;
import org.useless.dragonfly.model.entity.BenchEntityModel;

public class DeerModel extends BenchEntityModel {
	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> benchEntityBones.resetPose());
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		if (this.getIndexBones().containsKey("legLeftFront")) {
			this.getIndexBones().get("legLeftFront")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("legRightFront")) {
			this.getIndexBones().get("legRightFront")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("legLeftBack")) {
			this.getIndexBones().get("legLeftBack")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.141593F) * 1.4F * limbYaw;
		}

		if (this.getIndexBones().containsKey("legRightBack")) {
			this.getIndexBones().get("legRightBack")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;
		}

		// Hardcoded antler rotation values due to Dragonfly bugs.
		if (getIndexBones().containsKey("antlerLeft")) {
			getIndexBones().get("antlerLeft")
				.setRotationAngle(-0.225F, 0.0F, 0.0F);
		}
		if (getIndexBones().containsKey("antlerRight")) {
			getIndexBones().get("antlerRight")
				.setRotationAngle(-0.225F, 0.0F, 0.0F);
		}
	}
}
