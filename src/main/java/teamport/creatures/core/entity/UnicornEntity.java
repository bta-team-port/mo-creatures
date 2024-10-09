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
	public boolean interact(EntityPlayer player) {
		super.interact(player);
		ItemStack item = player.inventory.getCurrentItem();
		if (item != null) {
			if (item.itemID == Item.foodAppleGold.id) {
				chanceForTame += random.nextInt(100) + 1;
				item.consumeItem(player);
				world.playSoundAtEntity(null, this, "creatures.eating", 1.0f, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F);
			}
		}
		return false;
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		if (passenger != null && !isTamed) {
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
				isTamed = true;

				double randX = x + random.nextDouble();
				double randY = y + random.nextDouble();
				double randZ = z + random.nextDouble();

				world.spawnParticle("heart", randX, randY + 0.22, randZ, 0.0, 0.2, 0.0, 0);
			}
		}

		// EXPERIMENTAL //
		// Player follow code for the upcoming 7.3 release. Follow item is: Golden Apple.
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);
		if (player != null && (player.distanceToSqr(x, y, z) > 4.0)) {
			ItemStack heldStack = player.getCurrentEquippedItem();
			if (heldStack != null && heldStack.itemID == Item.foodAppleGold.id) {
				faceEntity(player, 30.0F, 30.0F);
				moveForward = 1.0F;

				if (player.distanceToSqr(this) <= 12.0)
					moveForward = 0.0F;
			}
		}
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 1;
	}
}
