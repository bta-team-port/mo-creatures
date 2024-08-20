package teamport.creatures.client.render.entity;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import org.useless.dragonfly.model.entity.BenchEntityModel;
import teamport.creatures.core.entity.KittyEntity;

public class KittyModel extends BenchEntityModel {
	private KittyEntity entity;

	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
		this.entity = (KittyEntity) entityliving;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> benchEntityBones.resetPose());
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		if (this.getIndexBones().containsKey("head")) {
			this.getIndexBones().get("head")
				.setRotationAngle((float)Math.toRadians(headPitch), (float)Math.toRadians(headYaw), 0.0F);
		}

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

		if (getIndexBones().containsKey("tail")) {
			getIndexBones().get("tail")
				.rotateAngleX = entity.getTailRotation() / 5;
		}
	}
}
