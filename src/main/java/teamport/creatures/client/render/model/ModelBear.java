package teamport.creatures.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelBear extends ModelBase {
	public Cube bearHead;
	public Cube bearSnout;
	public Cube bearEarLeft;
	public Cube bearEarRight;

	public Cube bearBody;
	public Cube bearTail;

	public Cube bearLeg1; //Left Front
	public Cube bearLeg2; //Right Front
	public Cube bearLeg3; //Left Back
	public Cube bearLeg4; //Right Back

	public ModelBear() {
		bearHead = new Cube(0, 0, 128, 64);
		bearHead.addBox(-4.5f, -9, -6, 9, 9, 6);
		bearHead.setRotationPoint(0, 6, -14);

		bearSnout = new Cube(3, 20, 128, 64);
		bearSnout.addBox(-2, -4, -9, 4, 4, 4);
		bearSnout.setRotationPoint(0, 6, -14);

		bearEarLeft = new Cube(20, 23, 128, 64);
		bearEarLeft.addBox(-6.5f, -12, -1.5f, 4, 4, 1);
		bearEarLeft.setRotationPoint(0, 6, -14);

		bearEarRight = new Cube(20, 23, 128, 64);
		bearEarRight.addBox(2.5f, -12, -1.5f, 4, 4, 1);
		bearEarRight.setRotationPoint(0, 6, -14);

		bearBody = new Cube(2, 0, 128, 64);
		bearBody.addBox(-8, -16, -21, 16, 16, 28);
		bearBody.setRotationPoint(0, 12, 7);

		bearTail = new Cube(20, 44, 128, 64);
		bearTail.addBox(-2, -2, 0, 4, 4, 2);
		bearTail.setRotationPoint(0, 3, 14);

		bearLeg1 = new Cube(2, 44, 128, 64);
		bearLeg1.addBox(3, 0, 0, 6, 12, 6);
		bearLeg1.setRotationPoint(-1, 12, -13);

		bearLeg2 = new Cube(2, 44, 128, 64);
		bearLeg2.addBox(3, 0, 0, 6, 12, 6);
		bearLeg2.setRotationPoint(-11, 12, -13);

		bearLeg3 = new Cube(2, 44, 128, 64);
		bearLeg3.addBox(3, 0, 0, 6, 12, 6);
		bearLeg3.setRotationPoint(-1, 12, 7);

		bearLeg4 = new Cube(2, 44, 128, 64);
		bearLeg4.addBox(3, 0, 0, 6, 12, 6);
		bearLeg4.setRotationPoint(-11, 12, 7);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		bearHead.render(scale);
		bearSnout.render(scale);
		bearEarLeft.render(scale);
		bearEarRight.render(scale);

		bearBody.render(scale);
		bearTail.render(scale);

		bearLeg1.render(scale);
		bearLeg2.render(scale);
		bearLeg3.render(scale);
		bearLeg4.render(scale);
	}

	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		bearHead.xRot = headPitch / (float) (180 / Math.PI);
		bearHead.yRot = headYaw / (float) (180 / Math.PI);
		bearEarRight.yRot = bearHead.yRot;
		bearEarRight.xRot = bearHead.xRot;
		bearEarLeft.yRot = bearHead.yRot;
		bearEarLeft.xRot = bearHead.xRot;
		bearSnout.yRot = bearHead.yRot;
		bearSnout.xRot = bearHead.xRot;

		bearTail.xRot = bearBody.xRot;
		bearTail.yRot = bearBody.yRot;

		bearLeg1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4f * limbYaw;
		bearLeg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4f * limbYaw;
		bearLeg3.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4f * limbYaw;
		bearLeg4.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4f * limbYaw;
	}
}
