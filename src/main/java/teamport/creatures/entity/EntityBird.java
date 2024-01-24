package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import useless.dragonfly.model.entity.AnimationState;

import java.util.List;

public class EntityBird extends EntityAnimal {
	public AnimationState flyState = new AnimationState();
	int courseChangeCoolDown = 0;
	int courseCoolDown = 200;
	int skinVariant;
	double waypointX;
	double waypointY;
	double waypointZ;
	boolean fed;

	public EntityBird(World world) {
		super(world);
		this.health = 5;
		this.setSize(0.25F, 0.25F);
		this.skinVariant = random.nextInt(6);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/bird/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/bird/0.png";
	}

	@Override
	public String getLivingSound() {
		switch (skinVariant) {
			default:
			case 0:
				return "creatures.birdred";
			case 1:
				return "creatures.birdblack";
			case 2:
				return "creatures.birdyellow";
			case 3:
				return "creatures.birdgreen";
			case 4:
				return "creatures.birdwhite";
			case 5:
				return "creatures.birdblue";
		}
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	// Copied code from EntityFlying because I hated extending it.
	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		if (this.isInWater()) {
			this.moveRelative(moveStrafing, moveForward, 0.02F);
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 0.8;
			this.yd *= 0.8;
			this.zd *= 0.8;
		} else if (this.isInLava()) {
			this.moveRelative(moveStrafing, moveForward, 0.02F);
			this.move(this.xd, this.yd, this.zd);
			this.xd *= 0.5;
			this.yd *= 0.5;
			this.zd *= 0.5;
		} else {
			float moveScale = 0.91F;
			if (this.onGround) {
				moveScale = 0.5460001F;
				int i = this.world.getBlockId(MathHelper.floor_double(this.x), MathHelper.floor_double(this.bb.minY) - 1, MathHelper.floor_double(this.z));
				if (i > 0) {
					moveScale = Block.blocksList[i].movementScale * 1.85F;
				}
			}

			float f3 = 0.1627714F / (moveScale * moveScale * moveScale);
			this.moveRelative(moveStrafing, moveForward, this.onGround ? 0.1F * f3 : 0.02F);
			moveScale = 0.91F;
			if (this.onGround) {
				moveScale = 0.5460001F;
				int blockID = this.world.getBlockId(MathHelper.floor_double(this.x), MathHelper.floor_double(this.bb.minY) - 1, MathHelper.floor_double(this.z));
				if (blockID > 0) {
					moveScale = Block.blocksList[blockID].movementScale * 0.91F;
				}
			}

			this.move(this.xd, this.yd, this.zd);
			this.xd *= moveScale;
			this.yd *= moveScale;
			this.zd *= moveScale;
		}

		this.prevLimbYaw = this.limbYaw;
		double x2 = this.x - this.xo;
		double z2 = this.z - this.zo;
		float f4 = MathHelper.sqrt_double(x2 * x2 + z2 * z2) * 2.0F;
		if (f4 > 1.0F) {
			f4 = 1.0F;
		}

		this.limbYaw += (f4 - this.limbYaw) * 0.4F;
		this.limbSwing += this.limbYaw;
	}

	// Copied from the Ghast code.
	private boolean isCourseTraversable(double x, double y, double z, double multiplier) {
		double wX = (this.waypointX - x) / multiplier;
		double wY = (this.waypointY - y) / multiplier;
		double wZ = (this.waypointZ - z) / multiplier;
		AABB aabb = this.bb.copy();

		for (int i = 1; (double) i < multiplier; ++i) {
			aabb.offset(wX, wY, wZ);
			if (!this.world.getCubes(this, aabb).isEmpty()) {
				return false;
			}
		}

		return true;
	}

	// TODO - Make them afraid of entities unless fed.
	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		this.moveEntityWithHeading(moveStrafing, moveForward);
		this.flyState.animateWhen(courseCoolDown >= 0, tickCount);

		// Seed check
		List<Entity> nearbyItems = this.world
			.getEntitiesWithinAABB(
				EntityItem.class, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 16.0, 16.0)
			);
		if (!nearbyItems.isEmpty()) {
			Entity entityItem = null;
			for (Entity nearbyItem : nearbyItems) {
				entityItem = nearbyItem;
			}
			if (entityItem != null) {
				if (((EntityItem) entityItem).item.itemID == Item.seedsWheat.id ||
					((EntityItem) entityItem).item.itemID == Item.seedsPumpkin.id) {
					this.setTarget(entityItem);

					// If the item is seeds and the entities are colliding, feed the birb.
					if (this.bb.intersectsWith(entityItem.bb)) {
						entityItem.remove();
						this.fed = true;
					}
				}
			}
		}

		// This is also copied from the Ghast code.
		double wX = this.waypointX - this.x;
		double wY = this.waypointY - this.y;
		double wZ = this.waypointZ - this.z;
		double wD = MathHelper.sqrt_double(wX * wX + wY * wY + wZ * wZ);
		if (wD < 1.0 || wD > 60.0) {
			this.waypointX = this.x + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 2.0F);
			this.waypointY = this.y + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 2.0F);
			this.waypointZ = this.z + (double)((this.random.nextFloat() * 2.0F - 1.0F) * 2.0F);
		}

		if (this.courseCoolDown-- >= 0) {
			if (this.courseChangeCoolDown-- <= 0) {
				this.courseChangeCoolDown += this.random.nextInt(5) + 2;
				this.renderYawOffset = this.yRot = -((float)Math.atan2(this.xd, this.zd)) * 180.0F / 3.141593F;

				if (this.isCourseTraversable(this.waypointX, this.waypointY, this.waypointZ, wD)) {
					this.xd += wX / wD * 0.1;
					this.yd += wY / wD * 0.05;
					this.zd += wZ / wD * 0.1;
				} else {
					this.waypointX = this.x;
					this.waypointY = this.y;
					this.waypointZ = this.z;
				}
			}
		} else {
			flyState.stop();
			this.yd -= 0.05F;
			if (courseCoolDown <= -200) courseCoolDown = random.nextInt(200) + 200;
		}
	}

	@Override
	protected void causeFallDamage(float f) {
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SkinVariant", skinVariant);
		tag.putInt("CourseCoolDown", courseCoolDown);
		tag.putInt("CourseChangeCoolDown", courseChangeCoolDown);
		tag.putBoolean("Fed", fed);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		skinVariant = tag.getInteger("SkinVariant");
		courseCoolDown = tag.getInteger("CourseCoolDown");
		courseChangeCoolDown = tag.getInteger("CourseChangeCoolDown");
		fed = tag.getBoolean("Fed");
	}
}
