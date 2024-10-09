package teamport.creatures.client.render.entity.dragonfly;

import net.minecraft.core.entity.EntityLiving;
import org.useless.dragonfly.helper.AnimationHelper;
import org.useless.dragonfly.model.entity.BenchEntityModel;
import org.useless.dragonfly.model.entity.animation.Animation;
import teamport.creatures.core.entity.BunnyEntity;

import static teamport.creatures.MoCreatures.MOD_ID;

public class BunnyModel extends BenchEntityModel {
	private BunnyEntity entity;

	@Override
	public void setLivingAnimations(EntityLiving entityliving, float limbSwing, float limbYaw, float partialTick) {
		super.setLivingAnimations(entityliving, limbSwing, limbYaw, partialTick);
		if (entityliving instanceof BunnyEntity) entity = (BunnyEntity) entityliving;
	}

	@Override
	public void setRotationAngles(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		this.getIndexBones().forEach((s, benchEntityBones) -> benchEntityBones.resetPose());
		super.setRotationAngles(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		if (this.getIndexBones().containsKey("head")) {
			this.getIndexBones().get("head")
				.setRotationAngle((float)Math.toRadians(headPitch), (float)Math.toRadians(headYaw), 0.0F);
		}

		Animation jumpAnim = AnimationHelper.getOrCreateEntityAnimation(MOD_ID, "bunny.animation");
		if (entity != null)
			animate(entity.jumpState, jumpAnim.getAnimations().get("animation.bunny.jump"), limbPitch, 1.0F);
	}
}
