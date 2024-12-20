package teamport.creatures.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.util.helper.MathHelper;

@Environment(EnvType.CLIENT)
public class ModelBird extends ModelBase {
	public Cube birdHead;
	public Cube birdBeak;
	public Cube birdBody;
	public Cube birdTail;
	public Cube birdWing1; //Left wing
	public Cube birdWing2; //Right wing
	public Cube birdLeg1;  // Left Leg
	public Cube birdLeg2;  // Right Leg

	public ModelBird() {
		birdHead = new Cube(0, 0, 32, 32);
		birdHead.addBox(-1, 1, -2, 2, 2, 2);
		birdHead.setRotationPoint(0, 17, -2.5f);

		birdBeak = new Cube(0, 4, 32, 32);
		birdBeak.addBox(-0.5f, 2,-3, 1, 1, 1);
		birdBeak.setRotationPoint(0, 17, -2.5f);

		birdBody = new Cube(4, 2, 32, 32);
		birdBody.addBox(-1, 0, -3, 2, 3, 7);
		birdBody.setRotationPoint(0, 19, 0);

		birdTail = new Cube(15, 4, 32, 32);
		birdTail.addBox(-1, 1, 2, 2, 1, 4);
		birdTail.setRotationPoint(0, 19, 0);

		birdWing1 = new Cube(0, 12, 32, 32);
		birdWing1.addBox(0, 0, -2, 1, 3, 6);
		birdWing1.setRotationPoint(1, 18, 0);

		birdWing2 = new Cube(0, 12, 32, 32);
		birdWing2.addBox(-1, 0, -2, 1, 3, 6);
		birdWing2.setRotationPoint(-1, 18, 0);

		birdLeg1 = new Cube(0, 9, 32, 32);
		birdLeg1.addBox(0.5f, 0, 0, 1, 2, 1);
		birdLeg1.setRotationPoint(0, 22, 0);

		birdLeg2 = new Cube(0, 9, 32, 32);
		birdLeg2.addBox(-1.5f, 0, 0, 1, 2, 1);
		birdLeg2.setRotationPoint(0, 22, 0);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);

		birdHead.render(scale);
		birdBeak.render(scale);
		birdBody.render(scale);
		birdTail.render(scale);

		birdWing1.render(scale);
		birdWing2.render(scale);

		birdLeg1.render(scale);
		birdLeg2.render(scale);
	}

	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		birdHead.xRot = headPitch / (float) (180 / Math.PI);
		birdHead.yRot = headYaw / (float) (180 / Math.PI);

		birdBeak.xRot = birdHead.xRot;
		birdBeak.yRot = birdHead.yRot;

		birdBody.xRot = -0.30f;
		birdTail.xRot = 0.20f;

		birdWing1.zRot = -limbPitch;
		birdWing2.zRot = limbPitch;

		birdLeg1.xRot = MathHelper.cos(limbSwing * 0.6662F) * 1.4f * limbYaw;
		birdLeg2.xRot = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 1.4f * limbYaw;
	}
}
