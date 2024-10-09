package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.world.World;

import java.util.Objects;

public class HorseEntity extends EntityAnimal {
	boolean isTamed;
	int annoyance = 0;
	int chanceForTame = 0;
	int tameCounter = 0;
	private int skinVariant;
	public boolean isSaddled;
	public String ownerName;

	public HorseEntity(World world) {
		super(world);
		skinVariant = random.nextInt(3);
		setSize(0.8F, 2.0F);
	}

	@Override
	public int getMaxHealth() {
		return 20;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/textures/entity/horse/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/textures/entity/horse/0.png";
	}

	@Override
	public boolean interact(EntityPlayer player) {
		super.interact(player);
		ItemStack item = player.inventory.getCurrentItem();
		if (item != null) {
			if (!isTamed) {
				if (item.itemID == Item.wheat.id) {
					chanceForTame += 1;
					item.consumeItem(player);
					world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
				}
				if (item.itemID == Item.foodApple.id) {
					chanceForTame += random.nextInt(4) + 1;
					item.consumeItem(player);
					world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

				}
				if (item.itemID == Item.dustSugar.id) {
					chanceForTame += random.nextInt(8) + 1;
					item.consumeItem(player);
					world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
				}
			}

			if (isTamed && Objects.equals(player.username, ownerName)) {
				if (item.itemID == Item.saddle.id) {
					isSaddled = true;
					item.consumeItem(player);
				}

				if (getHealth() < getMaxHealth()) {
					if (item.itemID == Item.wheat.id) {
						heal(2);
						item.consumeItem(player);
						world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
					}
					if (item.itemID == Item.foodApple.id) {
						heal(4);
						item.consumeItem(player);
						world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
					}
				}
			}
		} else {
			player.startRiding(this);
		}
		return false;
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		if (passenger != null && !isTamed) {
			EntityPlayer player = (EntityPlayer) passenger;

			if (random.nextInt(6) == 0) {
				annoyance += 20;
			}
			if (random.nextInt(10) == 0) {
				tameCounter += 20 * chanceForTame;
			}

			if (annoyance >= 300) {
				annoyance = 0;
				player.yd += 0.75F;
				player.xd -= yRot * 0.0015F;
				ejectRider();
				world.playSoundAtEntity(null,
					this,
					"creatures.horsemad",
					getSoundVolume(),
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}

			if (tameCounter++ >= 1000) {
				isTamed = true;
				ownerName = ((EntityPlayer) passenger).username;

				for (int i = 0; i < 8; i++) {
					double randX = x + random.nextDouble();
					double randY = y + random.nextDouble();
					double randZ = z + random.nextDouble();

					world.spawnParticle("heart", randX, randY + 0.22, randZ, 0.0, 0.2, 0.0, 0);
				}
			}
		}

		// EXPERIMENTAL //
		// Player follow code for the upcoming 7.3 release. Follow items are: Wheat and Sugar.
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);
		if (player != null && (player.distanceToSqr(x, y, z) > 4.0)) {
			ItemStack heldStack = player.getCurrentEquippedItem();
			if (heldStack != null && (heldStack.itemID == Item.wheat.id || heldStack.itemID == Item.dustSugar.id)) {
				faceEntity(player, 30.0F, 30.0F);
				moveForward = 1.0F;

				if (player.distanceToSqr(this) <= 12.0)
					moveForward = 0.0F;
			}
		}
	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		if (passenger != null) {
			if (isInWater() || isInLava()) ejectRider();

			if (isSaddled) {
				if (passenger instanceof EntityPlayerSP) {
					PlayerInput passengerInput = ((EntityPlayerSP) passenger).input;
					if (passengerInput.jump && !noPhysics && onGround) yd = 0.42;
					yRot = passenger.yRot;
					if (isInWater() || isInLava()) ejectRider();

					if (!onGround) {
						super.moveRelative(passengerInput.moveStrafe, passengerInput.moveForward, moveSpeed / 16);
					} else {
						super.moveRelative(passengerInput.moveStrafe, passengerInput.moveForward, moveSpeed / 6);
					}

					super.moveEntityWithHeading(passengerInput.moveStrafe, passengerInput.moveForward);
				}
			}else super.moveEntityWithHeading(moveStrafing, moveForward);
		} else {
			super.moveEntityWithHeading(moveStrafing, moveForward);
		}
	}

	@Override
	public float getYRotDelta(){
		return 0;
	}

	@Override
	public float getXRotDelta(){
		return 0;
	}

	@Override
	protected boolean canDespawn() {
		return !isTamed || super.canDespawn();
	}

	@Override
	public String getLivingSound() {
		return "creatures.horsegrunt";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.horsehurt";
	}

	@Override
	protected String getDeathSound() {
		return "creatures.horsedying";
	}

	@Override
	protected void dropFewItems() {
		super.dropFewItems();
		if (isSaddled) spawnAtLocation(Item.saddle.id, 1);
	}

	@Override
	public double getRideHeight() {
		return (double)bbHeight * 0.7;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Tamed", isTamed);
		tag.putBoolean("Saddled", isSaddled);
		tag.putInt("ChanceForTame", chanceForTame);
		tag.putInt("Annoyance", annoyance);
		tag.putInt("TameCounter", tameCounter);
		tag.putInt("SkinVariant", skinVariant);

		if (isTamed) {
			tag.putString("Owner", ownerName);
		}
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		isTamed = tag.getBoolean("Tamed");
		isSaddled = tag.getBoolean("Saddled");
		chanceForTame = tag.getInteger("ChanceForTame");
		annoyance = tag.getInteger("Annoyance");
		tameCounter = tag.getInteger("TameCounter");
		skinVariant = tag.getInteger("SkinVariant");

		if (isTamed) {
			ownerName = tag.getString("Owner");
		}
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (!world.isClientSide) {
			if (attacker instanceof EntityPlayer && !isTamed) {
				tameCounter -= 150;
			}
		}
		return super.hurt(attacker, damage, type);
	}
}
