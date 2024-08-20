package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.BlockGrass;
import net.minecraft.core.block.BlockIce;
import net.minecraft.core.block.BlockTallGrass;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.item.block.ItemBlock;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.world.World;
import org.useless.dragonfly.model.entity.AnimationState;
import teamport.creatures.MoCreatures;

public class BunnyEntity extends EntityAnimal {
	public AnimationState jumpState = new AnimationState();
	private int skinVariant;
	private int whenToJump;
	private int timesToJump;
	private int jumpDelay;

	public BunnyEntity(World world) {
		super(world);
		setSize(0.4F, 0.4F);

		heartsHalvesLife = 5;
		whenToJump = random.nextInt(200) + 200;
		skinVariant = random.nextInt(5);
		timesToJump = 0;
		jumpDelay = random.nextInt(10) + 10;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/textures/entity/bunny/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/textures/entity/bunny/0.png";
	}

	@Override
	protected void updatePlayerActionState() {
		++entityAge;
		tryToDespawn();

		// Increase height when in a fluid.
		if (isInWater() || isInLava()) yd += 0.15F;

		// Reduces and checks the 'whenToJump' timer. If below zero then choose a random amount of times to jump
		// and reset the timer.
		if (whenToJump-- <= 0) {
			whenToJump = random.nextInt(200) + 200;
			timesToJump = random.nextInt(8) + 1;
		}

		// Checks if the bunny is on the ground and if times to jump is above 0.
		// If it passes then check and decrease the jump delay. When it's 0 it sets the jump state,
		// resets the jump delay, and removes a number from 'times to jump'.
		// If the times to jump is instead below 0 then the jump state will stop and the bunny won't move.
		if (onGround && timesToJump > 0) {
			if (jumpDelay-- <= 0) {
				jumpDelay = random.nextInt(10) + 10;
				timesToJump -= 1;

				isJumping = true;
				jumpState.start(tickCount);
				world.playSoundEffect(
					null,
					SoundCategory.ENTITY_SOUNDS,
					x,
					y,
					z,
					"creatures.rabbitland",
					1.0F,
					(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F
				);
			} else {
				isJumping = false;
			}
		} else if (onGround) {
			isJumping = false;
			jumpState.stop();
		}

		if (!onGround) moveForward = 1.0F;
		else moveForward = 0.0F;

		// Randomly turn and look around.
		if (random.nextFloat() < 0.05F) randomYawVelocity = (random.nextFloat() - 0.5F) * 20.0F;
		yRot += randomYawVelocity;
		xRot = defaultPitch;

		// EXPERIMENTAL //
		// Player follow code for the upcoming 7.3 release. Follow item is: tall grass (and friends).
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);
		if (player != null) {
			ItemStack heldStack = player.getCurrentEquippedItem();

			if (heldStack != null && player.distanceToSqr(x, y, z) > 3.0) {
				if (heldStack.getItem() instanceof ItemBlock &&
					((ItemBlock) heldStack.getItem()).getBlock() instanceof BlockTallGrass) {
					faceEntity(player, 30.0F, 30.0F);
					timesToJump = 1;
				}
			}
		}
	}

	// Simple player passenger function, if the player right-clicks a bunny it will be picked up.
	// This required a mixin, due to the broken player passenger positioning.
	@Override
	public boolean interact(EntityPlayer entityplayer) {
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item == null && entityplayer.passenger == null) {
			startRiding(entityplayer);
			world.playSoundAtEntity(null,
				this,
				"creatures.rabbitland",
				1.0F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
		} else if (entityplayer.passenger != null) {
			entityplayer.ejectRider();
			world.playSoundAtEntity(null,
				this,
				"creatures.rabbitland",
				1.0F,
				(random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

			setPos(entityplayer.x, entityplayer.y, entityplayer.z);
			moveForward = 1.0F;
			jump();
			jumpState.start(tickCount);
		}

		return super.interact(entityplayer);
	}

	@Override
	protected String getDeathSound() {
		return "creatures.rabbitdeath";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.rabbithurt";
	}

	@Override
	public String getLivingSound() {
		return null;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SkinVariant", skinVariant);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		skinVariant = tag.getInteger("SkinVariant");
	}
}
