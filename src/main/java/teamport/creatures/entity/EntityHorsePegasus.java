package teamport.creatures.entity;

import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import useless.dragonfly.model.entity.AnimationState;

public class EntityHorsePegasus extends EntityHorse {
	public AnimationState flyState = new AnimationState();
	public EntityHorsePegasus(World world) {
		super(world);
		this.moveSpeed = 0.45F;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/horse/4.png";
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		super.interact(entityplayer);
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item != null) {
			if (item.itemID == Item.foodApple.id) {
				this.chanceForTame += random.nextInt(2) + 1;
				item.consumeItem(entityplayer);
			}
			if (item.itemID == Item.dustSugar.id) {
				this.chanceForTame += random.nextInt(5) + 1;
				item.consumeItem(entityplayer);
			}
			if (item.itemID == Item.foodAppleGold.id) {
				this.chanceForTame += random.nextInt(100) + 1;
				item.consumeItem(entityplayer);
			}

			if (this.tamed && item.itemID == Item.saddle.id) {
				this.saddled = true;
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
		if (this.passenger != null && !this.tamed) {
			EntityPlayer player = (EntityPlayer) this.passenger;

			if (this.random.nextInt(6) == 0) {
				this.annoyance += 40;
			}
			if (this.random.nextInt(10) == 0) {
				this.tameCounter += 15 * chanceForTame;
			}

			if (this.annoyance >= 400) {
				this.annoyance = 0;
				player.yd += 1F;
				player.xd -= this.yRot * 0.0015F;
				this.ejectRider();
				this.world.playSoundAtEntity(this,
					"creatures.horsemad",
					this.getSoundVolume(),
					(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			}

			if (this.passenger != null && !this.tamed) {
				this.jump();
			}

			if (tameCounter++ >= 1600) {
				this.tamed = true;
			}
		}
	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		if (this.passenger != null && this.saddled) {
				PlayerInput passengerInput = ((EntityPlayerSP) passenger).input;
				if (passengerInput.jump && this.y < (double) this.world.getHeightBlocks() / 2) this.jump();
				this.yRot = passenger.yRot;
				if (this.isInWater() || this.isInLava()) this.ejectRider();

				// Move faster in the air.
				if (!this.onGround) {
					super.moveRelative(passengerInput.moveStrafe, passengerInput.moveForward, this.moveSpeed / 6);
				} else {
					super.moveRelative(passengerInput.moveStrafe, passengerInput.moveForward, this.moveSpeed / 10);
				}

				super.moveEntityWithHeading(passengerInput.moveStrafe, passengerInput.moveForward);
			} else {
			super.moveEntityWithHeading(moveStrafing, moveForward);
		}
	}

	@Override
	protected void jump() {
		if (this.passenger == null) {
			super.jump();
		}
		else {
			this.yd = 0.42;
			if (this.isSprinting()) {
				float f = this.yRot * 0.01745329F;
				this.xd -= MathHelper.sin(f) * 0.2F;
				this.zd += MathHelper.cos(f) * 0.2F;
			}
		}
	}

	@Override
	public void onLivingUpdate() {
		if (!this.onGround && (yd < 0.0 || this.yd > 0.0)) {
			this.yd *= 0.75;
			this.flyState.animateWhen(true, tickCount);
		} else {
			this.flyState.stop();
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
