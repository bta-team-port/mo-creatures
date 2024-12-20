package teamport.creatures.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.Mob;
import net.minecraft.core.util.helper.MathHelper;
import teamport.creatures.core.entity.mob.MobFox;

@Environment(EnvType.CLIENT)
public class ModelFox extends ModelBase {
	public Cube foxHead;
	public Cube foxSnout;
	public Cube foxEarLeft;
	public Cube foxEarRight;
	public Cube foxBody;
	public Cube foxLeg1;	// Left Front
	public Cube foxLeg2;	// Right Front
	public Cube foxLeg3;	// Left Back
	public Cube foxLeg4;	// Right Back
	public Cube foxTail;

	public ModelFox(float expandAmount) {
		foxHead = new Cube(0, 0);
		foxHead.addBox(-3, -3, -5, 6, 6, 4, expandAmount);
		foxHead.setRotationPoint(0, 12, -7);

		foxSnout = new Cube(0, 10);
		foxSnout.addBox(-1, 0, -9, 2, 3, 4, expandAmount);
		foxSnout.setRotationPoint(0, 12, -7);

		foxEarLeft = new Cube(20, 4);
		foxEarLeft.addBox(1, -5, -2, 3, 4, 1);
		foxEarLeft.setRotationPoint(0, 12, -7);
		foxEarLeft.mirror = true;

		foxEarRight = new Cube(20, 4);
		foxEarRight.addBox(-4, -5, -2, 3, 4, 1);
		foxEarRight.setRotationPoint(0, 12, -7);

		foxBody = new Cube(18, 4);
		foxBody.addBox(-3, 10, -8, 6, 7, 15);
		foxBody.setRotationPoint(0, 0, 0);

		foxLeg1 = new Cube(0, 17);
		foxLeg1.addBox(-1, 0, -1, 2, 7, 2);
		foxLeg1.setRotationPoint(-2, 17, -5);

		foxLeg2 = new Cube(0, 17);
		foxLeg2.addBox(-1, 0, -1, 2, 7, 2);
		foxLeg2.setRotationPoint(2, 17, -5);

		foxLeg3 = new Cube(0, 17);
		foxLeg3.addBox(-1, 0, -2, 2, 7, 2);
		foxLeg3.setRotationPoint(2, 17, 6);

		foxLeg4 = new Cube(0, 17);
		foxLeg4.addBox(-1, 0, -2, 2, 7, 2);
		foxLeg4.setRotationPoint(-2, 17, 6);

		foxTail = new Cube(0, 14);
		foxTail.addBox(-1.5f, 0, 0, 3, 3, 12);
		foxTail.setRotationPoint(0, 10, 6);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		foxHead.render(scale);
		foxSnout.render(scale);
		foxEarLeft.render(scale);
		foxEarRight.render(scale);

		foxBody.render(scale);
		foxLeg1.render(scale);
		foxLeg2.render(scale);
		foxLeg3.render(scale);
		foxLeg4.render(scale);

		foxTail.render(scale);
	}

	@Override
	public void setLivingAnimations(Mob mob, float limbSwing, float limbYaw, float partialTick) {
		MobFox fox = (MobFox) mob;
		foxTail.yRot = fox.isFoxAngry() ? 0 : MathHelper.cos(limbSwing * 0.6662F + (float) Math.PI) * 0.225f * limbYaw;
		foxTail.xRot = fox.isFoxAngry() ? 0.45f : -0.45f;
	}

	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		foxHead.xRot = headPitch / (float) (180 / Math.PI);
		foxHead.yRot = headYaw / (float) (180 / Math.PI);

		foxEarRight.yRot = foxHead.yRot;
		foxEarRight.xRot = foxHead.xRot;

		foxEarLeft.yRot = foxHead.yRot;
		foxEarLeft.xRot = foxHead.xRot;

		foxSnout.yRot = foxHead.yRot;
		foxSnout.xRot = foxHead.xRot;

		foxLeg1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4f * limbYaw;
		foxLeg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4f * limbYaw;
		foxLeg3.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4f * limbYaw;
		foxLeg4.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4f * limbYaw;
	}
}
