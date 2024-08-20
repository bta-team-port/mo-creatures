package teamport.creatures.core.entity;

import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import org.useless.dragonfly.model.entity.AnimationState;

public class PegasusEntity extends HorseEntity {
	public AnimationState flyState = new AnimationState();
	public PegasusEntity(World world) {
		super(world);
		moveSpeed = 0.45F;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/textures/entity/horse/4.png";
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		super.interact(entityplayer);
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item != null) {
			if (item.itemID == Item.foodApple.id) {
				chanceForTame += random.nextInt(2) + 1;
				item.consumeItem(entityplayer);
				world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}
			if (item.itemID == Item.dustSugar.id) {
				chanceForTame += random.nextInt(5) + 1;
				item.consumeItem(entityplayer);
				world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}
			if (item.itemID == Item.foodAppleGold.id) {
				chanceForTame += random.nextInt(100) + 1;
				item.consumeItem(entityplayer);
				world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}

			if (tamed && item.itemID == Item.saddle.id) {
				saddled = true;
				item.consumeItem(entityplayer);
			}
		} else {
			entityplayer.startRiding(this);
		}
		return false;
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		if (passenger != null && !tamed) {
			EntityPlayer player = (EntityPlayer) passenger;

			if (random.nextInt(6) == 0) {
				annoyance += 40;
			}
			if (random.nextInt(10) == 0) {
				tameCounter += 15 * chanceForTame;
			}

			if (annoyance >= 400) {
				annoyance = 0;
				player.yd += 1F;
				player.xd -= yRot * 0.0015F;
				ejectRider();
				world.playSoundAtEntity(null,
					this,
					"creatures.horsemad",
					getSoundVolume(),
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}

			if (passenger != null && !tamed) {
				jump();
			}

			if (tameCounter++ >= 1600) {
				tamed = true;

				double randX = x + random.nextDouble();
				double randY = y + random.nextDouble();
				double randZ = z + random.nextDouble();

				world.spawnParticle("heart", randX, randY + 0.22, randZ, 0.0, 0.2, 0.0, 0);
			}
		}
	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		if (passenger != null) {
			if (isInWater() || isInLava()) ejectRider();

			if (saddled) {
				if (passenger instanceof EntityPlayerSP) {
					PlayerInput passengerInput = ((EntityPlayerSP) passenger).input;
					if (passengerInput.jump && y < (double) world.getHeightBlocks() / 2) jump();
					yRot = passenger.yRot;
					if (isInWater() || isInLava()) ejectRider();

					// Move faster in the air.
					if (!onGround) {
						super.moveRelative(passengerInput.moveStrafe, passengerInput.moveForward, moveSpeed / 6);
					} else {
						super.moveRelative(passengerInput.moveStrafe, passengerInput.moveForward, moveSpeed / 10);
					}

					super.moveEntityWithHeading(passengerInput.moveStrafe, passengerInput.moveForward);
				}
			} else super.moveEntityWithHeading(moveStrafing, moveForward);
		} else {
			super.moveEntityWithHeading(moveStrafing, moveForward);
		}
	}

	@Override
	protected void jump() {
		if (!world.isClientSide) {
			if (passenger == null) {
				super.jump();
			} else {
				yd = 0.21;
				if (isSprinting()) {
					float f = yRot * 0.01745329F;
					xd -= MathHelper.sin(f) * 0.2F;
					zd += MathHelper.cos(f) * 0.2F;
				}
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		if (!onGround && (yd < 0.0 || yd > 0.0)) {
			yd *= 0.75;
			flyState.animateWhen(true, tickCount);
		} else {
			flyState.stop();
		}
		super.onLivingUpdate();
	}

	@Override
	protected void causeFallDamage(float f) {

	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
}
