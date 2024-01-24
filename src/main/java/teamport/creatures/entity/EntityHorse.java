package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.client.entity.player.EntityPlayerSP;
import net.minecraft.client.input.PlayerInput;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class EntityHorse extends EntityAnimal {
	boolean tamed;
	int annoyance = 0;
	private int chanceForTame = 0;
	private int tameCounter = 0;
	private int skinVariant;
	public boolean saddled;
	public EntityHorse(World world) {
		super(world);
		this.health = 30;
		this.skinVariant = random.nextInt(3);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/horse/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/horse/0.png";
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		super.interact(entityplayer);
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item != null) {
			if (item.itemID == Item.wheat.id) {
				this.chanceForTame += 1;
				item.consumeItem(entityplayer);
			}
			if (item.itemID == Item.foodApple.id) {
				this.chanceForTame += random.nextInt(4) + 1;
				item.consumeItem(entityplayer);
			}
			if (item.itemID == Item.dustSugar.id) {
				this.chanceForTame += random.nextInt(8) + 1;
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

			if (this.random.nextInt(5) == 0) {
				this.annoyance += 20;
			}
			if (this.random.nextInt(10) == 0) {
				this.tameCounter += 20 * chanceForTame;
			}

			if (this.annoyance >= 300) {
				this.annoyance = 0;
				player.yd += 1F;
				player.xd -= this.yRot * 0.0015F;
				this.ejectRider();
				this.world.playSoundAtEntity(this,
					"creatures.horsemad",
					this.getSoundVolume(),
					(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
			}

			if (tameCounter++ >= 1000) {
				this.tamed = true;
			}
		}
	}

	@Override
	public void moveEntityWithHeading(float moveStrafing, float moveForward) {
		if (this.saddled && this.passenger != null) {
			PlayerInput passengerInput = ((EntityPlayerSP) passenger).input;
			if (passengerInput.jump && !this.noPhysics && this.onGround) this.yd = 0.42;
			this.yRot = passenger.yRot;

			super.moveEntityWithHeading(passengerInput.moveStrafe, passengerInput.moveForward);
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
		return !this.tamed && super.canDespawn();
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
		if (saddled) this.spawnAtLocation(Item.saddle.id, 1);
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Tamed", this.tamed);
		tag.putBoolean("Saddled", this.saddled);
		tag.putInt("ChanceForTame", this.chanceForTame);
		tag.putInt("Annoyance", this.annoyance);
		tag.putInt("TameCounter", this.tameCounter);
		tag.putInt("SkinVariant", this.skinVariant);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.tamed = tag.getBoolean("Tamed");
		this.saddled = tag.getBoolean("Saddled");
		this.chanceForTame = tag.getInteger("ChanceForTame");
		this.annoyance = tag.getInteger("Annoyance");
		this.tameCounter = tag.getInteger("TameCounter");
		this.skinVariant = tag.getInteger("SkinVariant");
	}
}
