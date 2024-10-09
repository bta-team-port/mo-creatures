package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.sound.SoundCategory;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import teamport.creatures.core.block.entity.LitterboxTile;

import java.util.List;
import java.util.Objects;

public class KittyEntity extends EntityAnimal {
	public boolean isTamed = false;
	public boolean isSitting = false;
	public String ownerName;
	private int skin;
	private int potty;
	private int usingPottyTime = 0;
	private int boredom;
	private final float soundPitch = (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F;

	public KittyEntity(World world) {
		super(world);
		setSize(0.5F, 0.7F);

		heartsHalvesLife = 10;
		skin = random.nextInt(4);
		boredom = random.nextInt(400) + 800;
		potty = random.nextInt(6000) + 6000;
	}

	@Override
	public String getEntityTexture() {
		return isTamed ? "/assets/creatures/textures/entity/kitty/tamed_" + skin + ".png" : "/assets/creatures/textures/entity/kitty/" + skin + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/textures/entity/kitty/0.png";
	}

	void showHeartsOrSmokeFX(boolean hearts) {
		String particle = "heart";
		if (!hearts) {
			particle = "smoke";
		}

		for (int i = 0; i < 7; i++) {
			double motionX = random.nextGaussian() * 0.02;
			double motionY = random.nextGaussian() * 0.02;
			double motionZ = random.nextGaussian() * 0.02;
			world
				.spawnParticle(
					particle,
					x + (double) (random.nextFloat() * bbWidth * 2.0F) - (double) bbWidth,
					y + 0.5 + (double) (random.nextFloat() * bbHeight),
					z + (double) (random.nextFloat() * bbWidth * 2.0F) - (double) bbWidth,
					motionX,
					motionY,
					motionZ,
					0
				);
		}
	}

	@Override
	public boolean interact(EntityPlayer player) {
		if (!world.isClientSide) {
			ItemStack heldItem = player.getHeldItem();

			if (heldItem != null && heldItem.itemID == Item.foodFishRaw.id) {
				if (!isTamed) {
					world.playSoundEffect(player,
						SoundCategory.ENTITY_SOUNDS,
						x,
						y,
						z,
						"creatures.kittyeating",
						1.0F,
						soundPitch);

					faceEntity(player, 30.0F, 30.0F);
					heldItem.consumeItem(player);

					if (random.nextInt(3) == 0) {
						isTamed = true;
						showHeartsOrSmokeFX(true);
						ownerName = player.username;
					} else {
						showHeartsOrSmokeFX(false);
					}
				} else {
					if (!world.isClientSide) {
						heal(2);
						heldItem.consumeItem(player);
					}
				}
			}

			if (isTamed && Objects.equals(player.username, ownerName) && player.getHeldItem() == null) {
				isSitting = !isSitting;
			}
		}

		return true;
	}

	public float getTailRotation() {
		return isTamed ? (0.55F - (float) (getMaxHealth() - getHealth()) * 0.2F) * (float) Math.PI : (float) (Math.PI / 5);
	}

	@Override
	protected boolean canDespawn() {
		return !isTamed && super.canDespawn();
	}

	@Override
	protected String getDeathSound() {
		return "creatures.kittydying";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.kittyhurt";
	}

	@Override
	public String getLivingSound() {
		return isTamed && random.nextInt(3) == 0 ? "creatures.kittypurr" : "creatures.kittygrunt";
	}

	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (!(entity instanceof EntityItem)) {
			if (!(distance > 2.0F) || !(distance < 6.0F) || this.random.nextInt(10) != 0) {
				if ((double) distance < 1.5 && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
					attackTime = 20;
					entity.hurt(this, 2, DamageType.COMBAT);
				}
			} else if (this.onGround) {
				double d = entity.x - this.x;
				double d1 = entity.z - this.z;
				float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
				this.xd = d / (double) f1 * 0.5 * 0.8F + this.xd * 0.2F;
				this.zd = d1 / (double) f1 * 0.5 * 0.8F + this.zd * 0.2F;
				this.yd = 0.4F;
			}
		}
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();

		// String search code for when the kitty is bored.
		// When the boredom is below 0 and above -400 it will find for nearby item entities.
		// If it isn't null and the item is string, start 'playing' with it.
		// Once the boredom reaches -400 it will reset.
		if (entityToAttack == null && boredom-- <= 0) {
			List<Entity> nearbyEntities = world
				.getEntitiesWithinAABB(EntityItem.class, AABB.getBoundingBoxFromPool(bb.minX,
						bb.minY,
						bb.minZ,
						bb.maxX,
						bb.maxY,
						bb.maxZ)
					.expand(16.0, 4.0, 16.0)
				);

			if (!nearbyEntities.isEmpty()) {
				for (Entity entity : nearbyEntities) {
					if (entity instanceof EntityItem && ((EntityItem) entity).item.itemID == Item.string.id) {
						setTarget(entity);
					}
				}
			}
		}

		if (boredom-- <= -400) {
			boredom = random.nextInt(400) + 800;
		}

		if (getTarget() != null && getTarget() instanceof EntityItem) {
			if (bb.expand(0.5, 2.0, 0.5).intersectsWith(getTarget().bb)) {
				getTarget().move(xd * (random.nextInt(3) + 1), yd + 0.1, zd * (random.nextInt(3) + 1));
			}
		}

		// Same AI as above, just with birds instead.
		if (entityToAttack == null && boredom-- <= 0) {
			List<Entity> nearbyBirds = world
				.getEntitiesWithinAABB(BirdEntity.class, AABB.getBoundingBoxFromPool(bb.minX,
						bb.minY,
						bb.minZ,
						bb.maxX,
						bb.maxY,
						bb.maxZ)
					.expand(16.0, 4.0, 16.0)
				);

			if (!nearbyBirds.isEmpty()) {
				setTarget(nearbyBirds.get(world.rand.nextInt(nearbyBirds.size())));
			}
		}


		if (isTamed) {
			if (isSitting) {
				moveForward = 0.0f;
				moveStrafing = 0.0f;
				isJumping = false;
			}

			// Potty system - use a litter box once? a day.
			if (potty-- <= 0) {
				List<TileEntity> tileEntities = world.loadedTileEntityList;

				if (!tileEntities.isEmpty()) {
					for (TileEntity tileEntity : tileEntities) {
						if (tileEntity instanceof LitterboxTile) {
							if (!((LitterboxTile) tileEntity).isFilthy && potty <= 0) {
								pathToEntity = world.getEntityPathToXYZ(this, tileEntity.x, tileEntity.y, tileEntity.z, 16.0F);
								if (distanceToSqr(tileEntity.x, tileEntity.y, tileEntity.z) < 4.0 && !isPassenger()) {
									setPos(tileEntity.x, tileEntity.y, tileEntity.z);
									startRiding((IVehicle) tileEntity);
								}
							}

							if (isPassenger() && usingPottyTime-- <= -200) {
								((LitterboxTile) tileEntity).ejectRider();
								((LitterboxTile) tileEntity).isFilthy = true;
								usingPottyTime = 0;
								potty = random.nextInt(6000) + 6000;
							}
						}
					}
				}
			}
		}

		// EXPERIMENTAL //
		// Player follow code for the upcoming 7.3 release. Follow items are: Fish.
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);
		if (player != null && player.distanceToSqr(x, y, z) > 4.0) {
			ItemStack heldStack = player.getCurrentEquippedItem();
			if (heldStack != null && !isSitting && heldStack.itemID == Item.foodFishRaw.id) {
				faceEntity(player, 30.0F, 30.0F);
				moveForward = 1.0F;

				if (player.distanceToSqr(this) <= 12.0)
					moveForward = 0.0F;
			}
		}
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("IsTamed", isTamed);
		tag.putBoolean("IsSitting", isSitting);
		tag.putInt("Skin", skin);

		if (isTamed) tag.putString("Owner", ownerName);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		isTamed = tag.getBoolean("IsTamed");
		isSitting = tag.getBoolean("IsSitting");
		skin = tag.getInteger("Skin");

		if (isTamed) ownerName = tag.getString("Owner");
	}
}
