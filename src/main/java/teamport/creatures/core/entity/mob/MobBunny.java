package teamport.creatures.core.entity.mob;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.Items;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.UUIDHelper;
import net.minecraft.core.world.World;
import org.jetbrains.annotations.NotNull;
import teamport.creatures.MoreMobs;
import teamport.creatures.core.item.MMItemTags;

import java.util.UUID;

public class MobBunny extends MobAnimal {
	public static final int MASK_TAMED = 0b0000_0001;
	public static final int DATA_GENERIC_FLAGS = 16;
	public static final int DATA_OWNER_UUID = 17;
	private int bunnyJumpTimer;
	private int timesBunnyShouldJump;
	private int whenBunnyShouldJump;

	public MobBunny(World world) {
		super(world);
		textureIdentifier = new NamespaceID(MoreMobs.MOD_ID, "bunny");
		scoreValue = -10;
		bunnyJumpTimer = 10 + random.nextInt(10);
		timesBunnyShouldJump = random.nextInt(8) + 1;
		whenBunnyShouldJump = random.nextInt(80) + 80;

		setSize(0.5f, 0.5f);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_GENERIC_FLAGS, (byte) 0, Byte.class);
		entityData.define(DATA_OWNER_UUID, null, UUID.class);
	}

	@Override
	protected boolean makeStepSound() {
		return false;
	}

	public boolean isBunnyTamed() {
		return entityData.getByte((DATA_GENERIC_FLAGS) & MASK_TAMED) != 0;
	}

	public UUID getBunnyOwner() {
		return entityData.getUUID(DATA_OWNER_UUID);
	}

	@Override
	public boolean sendDeathMessage(Entity entityKilledBy) {
		return super.sendDeathMessage(entityKilledBy) || isBunnyTamed();
	}

	@Override
	public String getEntityTexture() {
		return isBunnyTamed() ? "/assets/creatures/textures/entity/bunny_tamed/" + getTextureReference() + ".png" :
			"/assets/creatures/textures/entity/bunny/" + getTextureReference() + ".png";
	}

	public void setBunnyTamed(boolean flag) {
		byte data = entityData.getByte(DATA_GENERIC_FLAGS);
		if (flag) {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data | MASK_TAMED));
		} else {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data & ~MASK_TAMED));
		}
	}

	public void setBunnyOwner(UUID uuid) {
		entityData.set(DATA_OWNER_UUID, uuid);
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("JumpTimer", bunnyJumpTimer);
		tag.putInt("TimesShouldJump", timesBunnyShouldJump);
		tag.putInt("WhenShouldJump", whenBunnyShouldJump);

		UUIDHelper.writeToTag(tag, getBunnyOwner(), "OwnerUUID");
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		bunnyJumpTimer = tag.getInteger("JumpTimer");
		timesBunnyShouldJump = tag.getInteger("TimesShouldJump");
		whenBunnyShouldJump = tag.getInteger("WhenShouldJump");

		UUID ownerUUID = UUIDHelper.readFromTag(tag, "OwnerUUID");

		if (ownerUUID == null) {
			String s = tag.getString("Owner");
			if (!s.isEmpty()) {
				UUIDHelper.runConversionAction(s, (uuid) -> {
					setBunnyOwner(uuid);
					setBunnyTamed(true);
				}, null);
			}
		} else {
			this.setBunnyOwner(ownerUUID);
			this.setBunnyTamed(true);
		}
	}

	@Override
	protected String getDeathSound() {
		return "creatures:mob.bunny.death";
	}

	@Override
	protected String getHurtSound() {
		return "creatures:mob.bunny.hurt";
	}

	private String getLandSound() {
		return "creatures:mob.bunny.land";
	}

	private String getLiftSound() {
		return "creatures:mob.bunny.lift";
	}

	@Override
	public String getLivingSound() {
		return null;
	}

	@Override
	protected float getSoundVolume() {
		return 0.4f;
	}

	@Override
	public int getMaxHealth() {
		return isBunnyTamed() ? 10 : 4;
	}

	@Override
	protected void updateAI() {
		tryToDespawn();
		checkForPlayerHoldingItem();

		if (isInWater() || isInLava()) {
			yd += 0.15f;
		}

		// Reduces and checks the 'whenToJump' timer. If below zero then choose a random amount of times to jump
		// and reset the timer.
		if (whenBunnyShouldJump-- <= 0 && timesBunnyShouldJump <= 0) {
			whenBunnyShouldJump = random.nextInt(80) + 80;
			timesBunnyShouldJump = random.nextInt(8) + 1;
		}

		if (onGround && timesBunnyShouldJump > 0) {
			if (bunnyJumpTimer-- <= 0) {
				bunnyJumpTimer = 10 + random.nextInt(10);
				timesBunnyShouldJump -= 1;

				isJumping = true;
				world.playSoundEffect(
					null,
					SoundCategory.ENTITY_SOUNDS,
					x,
					y,
					z,
					getLiftSound(),
					1.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F
				);
			} else {
				isJumping = false;
			}
		} else if (onGround) {
			isJumping = false;
		}

		moveForward = onGround ? 0 : 1;

		// Randomly turn and look around.
		if (random.nextFloat() < 0.05F) {
			randomYawVelocity = (random.nextFloat() - 0.5F) * 20.0F;
		}

		yRot += randomYawVelocity;
		xRot = defaultPitch;

		if (target != null) {
			lookAt(target, 30, 30);

			timesBunnyShouldJump = target.distanceTo(this) > 3 ? 1 : 0;
		}
	}

	private void showHeartsOrSmokeFX(boolean doHearts) {
		String s = "heart";
		if (!doHearts) {
			s = "smoke";
		}

		for(int i = 0; i < 7; ++i) {
			double motionX = random.nextGaussian() * 0.02;
			double motionY = random.nextGaussian() * 0.02;
			double motionZ = random.nextGaussian() * 0.02;
			world.spawnParticle(s,
				x + (double)(random.nextFloat() * bbWidth * 2) - (double)bbWidth,
				y + (double)0.5F + (double)(random.nextFloat() * this.bbHeight),
				z + (double)(this.random.nextFloat() * this.bbWidth * 2) - (double)bbWidth,
				motionX,
				motionY,
				motionZ,
				0);
		}
	}

	@Override
	public boolean interact(@NotNull Player player) {
		ItemStack item = player.inventory.getCurrentItem();
		if (item == null && player.passenger == null) {
			startRiding(player);
			world.playSoundAtEntity(null,
				this,
				getLandSound(),
				1.0F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		} else if (player.passenger != null) {
			player.ejectRider();
			world.playSoundAtEntity(null,
				this,
				getLandSound(),
				1.0F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

			setPos(player.x, player.y, player.z);
			moveForward = 1.0F;
			jump();
		}

		if (!world.isClientSide) {
			if (item != null && item.itemID == Items.WHEAT.id && !isBunnyTamed()) {
				item.consumeItem(player);

				if (item.stackSize <= 0) {
					player.inventory.setItem(player.inventory.getCurrentItemIndex(), null);
				}

				if (random.nextInt(3) != 0) {
					showHeartsOrSmokeFX(false);
				} else {
					showHeartsOrSmokeFX(true);
					setBunnyTamed(true);
					setBunnyOwner(player.uuid);
					setHealthRaw(getMaxHealth());
				}
			}
		}

		return super.interact(player);
	}

	@Override
	public double getRidingHeight() {
		return heightOffset - 1f;
	}

	@Override
	protected void causeFallDamage(float distance) {
		int i = (int)Math.ceil(distance - 3);
		if (i > 6) {
			super.causeFallDamage(distance);
		}
	}

	@Override
	public boolean isFavouriteItem(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem().hasTag(MMItemTags.BUNNIES_FAVORITE_ITEM);
	}
}
