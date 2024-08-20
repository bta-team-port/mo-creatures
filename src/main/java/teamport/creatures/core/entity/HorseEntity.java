package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class HorseEntity extends EntityAnimal {
	boolean tamed;
	int annoyance = 0;
	int chanceForTame = 0;
	int tameCounter = 0;
	private int skinVariant;
	public boolean saddled;

	public HorseEntity(World world) {
		super(world);
		heartsHalvesLife = 30;
		skinVariant = random.nextInt(3);
		setSize(0.8F, 2.0F);
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
	public boolean interact(EntityPlayer entityplayer) {
		super.interact(entityplayer);
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item != null) {
			if (item.itemID == Item.wheat.id) {
				chanceForTame += 1;
				item.consumeItem(entityplayer);
				world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}
			if (item.itemID == Item.foodApple.id) {
				chanceForTame += random.nextInt(4) + 1;
				item.consumeItem(entityplayer);
				world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);

			}
			if (item.itemID == Item.dustSugar.id) {
				chanceForTame += random.nextInt(8) + 1;
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
				tamed = true;

				for (int i = 0; i < 8; i++) {
					double randX = x + random.nextDouble();
					double randY = y + random.nextDouble();
					double randZ = z + random.nextDouble();

					world.spawnParticle("heart", randX, randY + 0.22, randZ, 0.0, 0.2, 0.0, 0);
				}
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
		return !tamed && super.canDespawn();
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
		if (saddled) spawnAtLocation(Item.saddle.id, 1);
	}

	@Override
	public double getRideHeight() {
		return (double)bbHeight * 0.7;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Tamed", tamed);
		tag.putBoolean("Saddled", saddled);
		tag.putInt("ChanceForTame", chanceForTame);
		tag.putInt("Annoyance", annoyance);
		tag.putInt("TameCounter", tameCounter);
		tag.putInt("SkinVariant", skinVariant);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		tamed = tag.getBoolean("Tamed");
		saddled = tag.getBoolean("Saddled");
		chanceForTame = tag.getInteger("ChanceForTame");
		annoyance = tag.getInteger("Annoyance");
		tameCounter = tag.getInteger("TameCounter");
		skinVariant = tag.getInteger("SkinVariant");
	}
}
