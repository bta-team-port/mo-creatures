package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemSeeds;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;
import org.useless.dragonfly.model.entity.AnimationState;

import java.util.List;

public class BirdEntity extends EntityAnimal {
	public AnimationState flyState = new AnimationState();
	private int courseChangeCoolDown = 0;
	private int courseCoolDown = 200;
	private int skinVariant;
	public boolean isFed;

	public BirdEntity(World world) {
		super(world);
		setSize(0.25F, 0.25F);

		skinVariant = random.nextInt(6);
		speed = 0.05f;
		isFed = false;
	}

	@Override
	public int getMaxHealth() {
		return 5;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/textures/entity/bird/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/textures/entity/bird/0.png";
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

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		flyState.animateWhen(!onGround, tickCount);

		if (courseCoolDown-- <= 0 && courseCoolDown > -400 && getTarget() == null) {
			moveForward = 1.2F;

			if (courseChangeCoolDown++ >= 100) {
				courseChangeCoolDown = 0;
				roamRandomPath();
			}

			// Very simple flying code - if a block is or isn't null 6 blocks below the bird
			// then raise or lower the Y double.
			if (world.getBlock((int) x, (int) (bb.minY - 6), (int) z) == null) {
				this.yd -= 0.1;
			} else if (world.getBlock((int) x, (int) (bb.minY - 6), (int) z) != null) {
				this.yd += 0.1;
			}
		} else if (courseCoolDown > 0) {
			flyState.stop();

			if (!onGround) {
				moveForward = 1.0F;
				this.yd -= 0.1;
			}
		}


		if (courseCoolDown <= -400) {
			courseCoolDown = random.nextInt(400) + 400;
		}

		if (isInWater() || isInLava()) this.yd = 0.005;

		// SEED CODE
		// Check for nearby items within the AABB (expanded by 16 blocks)
		// If the item isn't null and is an instance of anything that extends seeds it will set the target to the item.
		if (entityToAttack == null) {
			List<Entity> nearbyItems = world
				.getEntitiesWithinAABB(EntityItem.class, AABB.getBoundingBoxFromPool(x,
						y,
						z,
						x + 1.0,
						y + 1.0,
						z + 1.0)
					.expand(16.0, 4.0, 16.0)
				);

			if (!nearbyItems.isEmpty()) {
				for (Entity nearbySeeds : nearbyItems) {
					if (nearbySeeds instanceof EntityItem && ((EntityItem) nearbySeeds).item.getItem() instanceof ItemSeeds) {
						setTarget(nearbySeeds);
					}
				}
			}
		}

		if (getTarget() != null && getTarget() instanceof EntityItem) {
			if (bb.expand(0.5, 2.0, 0.5).intersectsWith(getTarget().bb)) {
				getTarget().remove();
				isFed = true;
			}
		}

		// Simple spooking code. If there's any 'living' entities within the AABB expanded by 16 it will fly rapidly.
		// The 'faceEntity' call should make it face the opposite direction.
		// UNLESS if it's a player! If they're in creative or sneaking then the bird will act as normal.
		// Another exception is if it's fed.
		List<Entity> nearbyLiving = world
			.getEntitiesWithinAABB(EntityLiving.class, AABB.getBoundingBoxFromPool(x,
					y,
					z,
					x + 1.0,
					y + 1.0,
					z + 1.0)
				.expand(8.0, 6.0, 8.0)
			);

		if (!nearbyLiving.isEmpty()) {
			for (Entity entity : nearbyLiving) {
				if (!(entity instanceof EntityPlayer) && !(entity instanceof BirdEntity) ||
					(entity instanceof EntityPlayer && !entity.isSneaking() &&
						((EntityPlayer) entity).gamemode.areMobsHostile()) &&
						!isFed) {
					courseCoolDown = -250;
					moveForward = 2.0F;

					faceEntity(entity, 0.0F, 0.0F);
				}
			}
		} else {
			moveForward = 1.2F;
		}

		if (getHealth() < getMaxHealth()) {
			courseCoolDown = -250;
			moveForward = 2.0F;
			isFed = false;

			roamRandomPath();
		}

		// EXPERIMENTAL //
		// Player follow code for the upcoming 7.3 release. Follow items are: Seeds.
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);
		if (isFed && player != null && (player.distanceToSqr(x, y, z) > 4.0)) {
			ItemStack heldStack = player.getCurrentEquippedItem();
			if (heldStack != null && heldStack.getItem() instanceof ItemSeeds) {
				faceEntity(player, 30.0F, 30.0F);
				moveForward = 1.0F;

				if (player.distanceToSqr(this) <= 12.0)
					moveForward = 0.0F;
			}
		}
	}


	@Override
	protected void causeFallDamage(float f) {
	}

	@Override
	protected void jump() {
		this.yd = 0.84;
		super.jump();
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SkinVariant", skinVariant);
		tag.putInt("CourseCoolDown", courseCoolDown);
		tag.putInt("CourseChangeCoolDown", courseChangeCoolDown);
		tag.putBoolean("IsFed", isFed);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		skinVariant = tag.getInteger("SkinVariant");
		courseCoolDown = tag.getInteger("CourseCoolDown");
		courseChangeCoolDown = tag.getInteger("CourseChangeCoolDown");
		isFed = tag.getBoolean("IsFed");
	}
}
