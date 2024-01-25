package teamport.creatures.entity;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class EntityHorseUnicorn extends EntityHorse {
	public EntityHorseUnicorn(World world) {
		super(world);
		this.moveSpeed = 1.4F;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/horse/3.png";
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		super.interact(entityplayer);
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item != null) {
			if (item.itemID == Item.foodApple.id) {
				this.chanceForTame += random.nextInt(3) + 1;
				item.consumeItem(entityplayer);
			}
			if (item.itemID == Item.dustSugar.id) {
				this.chanceForTame += random.nextInt(6) + 1;
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
				this.annoyance += 30;
			}
			if (this.random.nextInt(10) == 0) {
				this.tameCounter += 15 * chanceForTame;
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

			if (tameCounter++ >= 1200) {
				this.tamed = true;
			}
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
}
