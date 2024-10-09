package teamport.creatures.client.render.entity.dragonfly;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.MathHelper;
import org.useless.dragonfly.helper.AnimationHelper;
import org.useless.dragonfly.model.entity.BenchEntityModel;
import org.useless.dragonfly.model.entity.animation.Animation;
import teamport.creatures.core.entity.KittyEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

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

		if (this.getIndexBones().containsKey("body")) {
			if (entity.isSitting) {
				this.getIndexBones().get("body")
					.setRotationAngle(-0.40f, 0.0f, 0.0f);
			}
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

			if (entity.isSitting) {
				this.getIndexBones().get("legLeftBack")
					.setRotationPoint(0, 2, 0);

				this.getIndexBones().get("legLeftBack")
					.setRotationAngle(-1.5f, 0, 0);
			}
		}

		if (this.getIndexBones().containsKey("legRightBack")) {
			this.getIndexBones().get("legRightBack")
				.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbYaw;

			if (entity.isSitting) {
				this.getIndexBones().get("legRightBack")
					.setRotationPoint(0, 2, 0);

				this.getIndexBones().get("legRightBack")
					.setRotationAngle(-1.5f, 0, 0);
			}
		}

		if (getIndexBones().containsKey("tail")) {
			getIndexBones().get("tail")
				.rotateAngleX = entity.getTailRotation() / 5;

			if (entity.isSitting) {
				this.getIndexBones().get("tail")
					.setRotationPoint(0, 4.5f, -2.0f);
			}
		}
	}
}
