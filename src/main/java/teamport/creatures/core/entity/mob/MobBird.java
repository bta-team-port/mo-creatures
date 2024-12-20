package teamport.creatures.core.entity.mob;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.creatures.MoreMobs;
import teamport.creatures.core.item.MMItemTags;

import java.util.List;

public class MobBird extends MobAnimal {
	public static final int MASK_AFRAID = 0b0000_0001;
	public static final int MASK_FED = 0b0000_0010;
	public static final int MASK_FLYING = 0b0000_0100;
	public static final int DATA_GENERIC_FLAGS = 16;
	public float flap = 0.0F;
	public float flapSpeed = 0.0F;
	public float oFlapSpeed;
	public float oFlap;
	public float flapping = 1.0F;
	private int flyingTimer = 300 + random.nextInt(300);

	public MobBird(World world) {
		super(world);

		heartsHalvesLife = 4;
		scoreValue = 50;
		textureIdentifier = new NamespaceID(MoreMobs.MOD_ID, "bird");

		setSize(0.25f, 0.25f);
	}

	@Override
	protected void causeFallDamage(float distance) {
	}

	@Override
	public boolean canClimb() {
		return false;
	}

	public void setBirdAfraid(boolean flag) {
		byte data = entityData.getByte(DATA_GENERIC_FLAGS);
		if (flag) {
			entityData.set(DATA_GENERIC_FLAGS,(byte) (data | MASK_AFRAID));
		} else {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data & ~MASK_AFRAID));
		}
	}

	public void setBirdFed(boolean flag) {
		byte data = entityData.getByte(DATA_GENERIC_FLAGS);
		if (flag) {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data | MASK_FED));
		} else {
			entityData.set(DATA_GENERIC_FLAGS,(byte) (data & ~MASK_FED));
		}
	}

	public void setBirdFlying(boolean flag) {
		byte data = entityData.getByte(DATA_GENERIC_FLAGS);
		if (flag) {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data | MASK_FLYING));
		} else {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data & ~MASK_FLYING));
		}
	}

	@Override
	protected void checkForPlayerHoldingItem() {
		if (!isBirdAfraid()) {
			super.checkForPlayerHoldingItem();

			if (closestPlayer != null) {
				if (closestPlayer.getHeldItem() != null &&
					closestPlayer.getHeldItem().getItem().hasTag(MMItemTags.BIRDS_FAVORITE_ITEM)) {
					if (getHealth() >= getMaxHealth()) {
						setBirdFlying(false);
					}
				}
			}
		}
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_GENERIC_FLAGS, (byte) 0, Byte.class);
	}

	@Override
	protected boolean makeStepSound() {
		return false;
	}

	@Override
	protected String getDeathSound() {
		return null;
	}

	@Override
	protected String getHurtSound() {
		return null;
	}

	@Override
	public String getLivingSound() {
		switch (getSkinVariant() % 6) {
			case 5:
				return "creatures:mob.bird.yellow";
			case 4:
				return "creatures:mob.bird.white";
			case 3:
				return "creatures:mob.bird.red";
			case 2:
				return "creatures:mob.bird.green";
			case 1:
				return "creatures:mob.bird.blue";
			case 0:
			default:
				return "creatures:mob.bird.black";
		}
	}

	public boolean isBirdAfraid() {
		return (entityData.getByte(DATA_GENERIC_FLAGS) & MASK_AFRAID) != 0;
	}

	public boolean isBirdFed() {
		return (entityData.getByte(DATA_GENERIC_FLAGS) & MASK_FED) != 0;
	}

	public boolean isBirdFlying() {
		return (entityData.getByte(DATA_GENERIC_FLAGS) & MASK_FLYING) != 0;
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("IsAfraid", isBirdAfraid());
		tag.putBoolean("IsFed", isBirdFed());
		tag.putBoolean("IsFlying", isBirdFlying());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setBirdAfraid(tag.getBoolean("IsAfraid"));
		setBirdFed(tag.getBoolean("IsFed"));
		setBirdFlying(tag.getBoolean("IsFlying"));
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		oFlap = flap;
		oFlapSpeed = flapSpeed;
		flapSpeed = (float) ((double) flapSpeed + (double) (onGround ? -1 : 4) * 0.3);
		if (flapSpeed < 0.0F) {
			flapSpeed = 0.0F;
		}

		if (flapSpeed > 1.0F) {
			flapSpeed = 1.0F;
		}

		if (!onGround && flapping < 1.0F) {
			flapping = 1.0F;
		}

		flapping = (float) ((double) flapping * 0.9);
		if (!onGround && yd < (double) 0.0F) {
			yd *= 0.6;
		}

		flap += flapping * 2.0F;
	}

	public void checkForSeedsNearBird() {
		List<Entity> entitiesNearBird = world.getEntitiesWithinAABBExcludingEntity(this,
			bb.grow(10, 8, 10));

		if (!entitiesNearBird.isEmpty() && isAlive()) {
			for (Entity entity : entitiesNearBird) {
				if (entity instanceof EntityItem &&
					((EntityItem) entity).item.getItem().hasTag(MMItemTags.BIRDS_FAVORITE_ITEM)) {
					setTarget(entity);
					break;
				}
			}
		}

		if (target != null && target instanceof EntityItem) {
			if (bb.intersects(target.bb.grow(3.5, 1, 3.5))) {
				setBirdFed(true);
				target.remove();
				target = null;
			}
		}
	}

	@Override
	protected void updateAI() {
		super.updateAI();
		checkForSeedsNearBird();

		if (flyingTimer-- <= -300) {
			flyingTimer = 300 + random.nextInt(300);
		}

		setBirdFlying((flyingTimer > 0 || isBirdAfraid()) && target == null);
		setBirdAfraid(getHealth() < getMaxHealth());
	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		super.moveEntityWithHeading(moveStrafing, moveForward);

		if (isInWater()) {
			this.yd += 0.25f;
		}

		if (target != null && target.bb.minY > y) {
			this.yd += 0.25f;
		}

		if (!isBirdFlying()) {
			if (!onGround) {
				this.yd -= 0.15f;
			}
		} else {
			if (world.getBlock((int) x, (int) (y - 4), (int) z) != null) {
				roamRandomPath();

				if (world.canBlockSeeTheSky((int) x, (int) (y - 4), (int) z)) {
					this.yd -= 0.2f;
				} else {
					this.yd += 0.1f;
				}

				float f2 = 0.91F;

				float f3 = 0.1627714F / (f2 * f2 * f2);
				this.moveRelative(moveStrafing, moveForward, this.onGround ? 0.1F * f3 : 0.02F);

				move(this.xd, this.yd, this.zd);
				this.xd *= f2;
				this.yd *= f2;
				this.zd *= f2;
			}
		}
	}

	@Override
	public boolean isFavouriteItem(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem().hasTag(MMItemTags.BIRDS_FAVORITE_ITEM);
	}

	@Override
	protected void jump() {
	}
}
