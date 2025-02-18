package teamport.creatures.client.render.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.model.Cube;
import net.minecraft.client.render.model.ModelBase;
import net.minecraft.core.entity.Mob;
import teamport.creatures.core.entity.mob.MobBunny;

@Environment(EnvType.CLIENT)
public class ModelBunny extends ModelBase {
	public Cube bunnyHead;
	public Cube bunnyEar1; // Left ear
	public Cube bunnyEar2; // Right ear
	public Cube bunnyWhiskers;

	public Cube bunnyBody;
	public Cube bunnyTail;

	public Cube bunnyLeg1; // Left front
	public Cube bunnyLeg2; // Right front
	public Cube bunnyLeg3; // Left back
	public Cube bunnyLeg4; // Right back

	public ModelBunny() {

		bunnyHead = new Cube(0, 0, 32, 32);
		bunnyHead.addBox(-2, 0, -4, 4, 4, 4);
		bunnyHead.setRotationPoint(0, 17, -3);

		bunnyEar1 = new Cube(0, 10, 32, 32);
		bunnyEar1.addBox(1, -4, -3, 1, 4, 2);
		bunnyEar1.setRotationPoint(0, 17, -3);

		bunnyEar2 = new Cube(0, 10, 32, 32);
		bunnyEar2.addBox(-2, -4, -3, 1, 4, 2);
		bunnyEar2.setRotationPoint(0, 17, -3);

		bunnyWhiskers = new Cube(0, 22, 32, 32);
		bunnyWhiskers.addBox(-4, 1, -3, 8, 3, 0);
		bunnyWhiskers.setRotationPoint(0, 17, -3);

		bunnyBody = new Cube(0, 8, 32, 32);
		bunnyBody.addBox(-3, 0, -3, 6, 6, 8);
		bunnyBody.setRotationPoint(0, 16, 0);

		bunnyTail = new Cube(16, 2, 32, 32);
		bunnyTail.addBox(-1.5f, 2, 5, 3, 3, 3);
		bunnyTail.setRotationPoint(0, 16, 0);

		bunnyLeg1 = new Cube(20, 8, 32, 32);
		bunnyLeg1.addBox(1, 6, -3, 2, 2, 2);
		bunnyLeg1.setRotationPoint(0, 16, 0);

		bunnyLeg2 = new Cube(20, 8, 32, 32);
		bunnyLeg2.addBox(-3, 6, -3, 2, 2, 2);
		bunnyLeg2.setRotationPoint(0, 16, 0);

		bunnyLeg3 = new Cube(4, 22, 32, 32);
		bunnyLeg3.addBox(1, 2, -2, 2, 2, 4);
		bunnyLeg3.setRotationPoint(0, 20, 3);

		bunnyLeg4 = new Cube(4, 22, 32, 32);
		bunnyLeg4.addBox(-3, 2, -2, 2, 2, 4);
		bunnyLeg4.setRotationPoint(0, 20, 3);
	}

	@Override
	public void render(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		setupAnimation(limbSwing, limbYaw, limbPitch, headYaw, headPitch, scale);
		bunnyHead.render(scale);
		bunnyEar1.render(scale);
		bunnyEar2.render(scale);
		bunnyWhiskers.render(scale);
		bunnyBody.render(scale);
		bunnyTail.render(scale);
		bunnyLeg1.render(scale);
		bunnyLeg2.render(scale);
		bunnyLeg3.render(scale);
		bunnyLeg4.render(scale);
	}

	@Override
	public void setupAnimation(float limbSwing, float limbYaw, float limbPitch, float headYaw, float headPitch, float scale) {
		bunnyHead.xRot = headPitch / (float) (180 / Math.PI);
		bunnyHead.yRot = headYaw / (float) (180 / Math.PI);

		bunnyEar1.xRot = bunnyHead.xRot;
		bunnyEar1.yRot = bunnyHead.yRot;
		bunnyEar2.xRot = bunnyHead.xRot;
		bunnyEar2.yRot = bunnyHead.yRot;
		bunnyWhiskers.xRot = bunnyHead.xRot;
		bunnyWhiskers.yRot = bunnyHead.yRot;
	}

	@Override
	public void setLivingAnimations(Mob mob, float limbSwing, float limbYaw, float partialTick) {
		MobBunny bunny = (MobBunny) mob;

		if (!bunny.onGround && !isRiding) {
			bunnyBody.xRot = -0.225f;
			bunnyTail.xRot = -0.225f;
			bunnyLeg1.xRot = -0.225f;
			bunnyLeg2.xRot = -0.225f;
		} else {
			bunnyBody.xRot = 0;
			bunnyTail.xRot = 0;
			bunnyLeg1.xRot = 0;
			bunnyLeg2.xRot = 0;
		}
	}
}
