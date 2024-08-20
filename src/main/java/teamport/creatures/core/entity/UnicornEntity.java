package teamport.creatures.core.entity;

import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.world.World;

public class UnicornEntity extends HorseEntity {
	public UnicornEntity(World world) {
		super(world);
		moveSpeed = 1.4F;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/textures/entity/horse/3.png";
	}

	@Override
	public boolean interact(EntityPlayer entityplayer) {
		super.interact(entityplayer);
		ItemStack item = entityplayer.inventory.getCurrentItem();
		if (item != null) {
			if (item.itemID == Item.foodApple.id) {
				chanceForTame += random.nextInt(3) + 1;
				item.consumeItem(entityplayer);
				world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}
			if (item.itemID == Item.dustSugar.id) {
				chanceForTame += random.nextInt(6) + 1;
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
				annoyance += 30;
			}
			if (random.nextInt(10) == 0) {
				tameCounter += 15 * chanceForTame;
			}

			if (annoyance >= 300) {
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

			if (tameCounter++ >= 1200) {
				tamed = true;

				double randX = x + random.nextDouble();
				double randY = y + random.nextDouble();
				double randZ = z + random.nextDouble();

				world.spawnParticle("heart", randX, randY + 0.22, randZ, 0.0, 0.2, 0.0, 0);
			}
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
}
