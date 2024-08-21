package teamport.creatures.core.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.block.entity.TileEntity;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.IVehicle;
import net.minecraft.core.world.World;
import teamport.creatures.core.block.entity.LitterboxTile;

import java.util.List;

public class KittyEntity extends EntityAnimal {
	public boolean isTamed = false;
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
		potty = random.nextInt(200) + 200;
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
		ItemStack heldItem = player.getHeldItem();

		if (heldItem != null && heldItem.itemID == Item.foodFishRaw.id) {
			if (!isTamed) {
				world.playSoundAtEntity(null, this, "creatures.kittyeating", 1.0F, soundPitch);

				if (!world.isClientSide) {
					faceEntity(player, 1.0F, 1.0F);
					heldItem.consumeItem(player);

					if (random.nextInt(3) == 0) {
						isTamed = true;
						showHeartsOrSmokeFX(true);
						ownerName = player.username;
					} else {
						showHeartsOrSmokeFX(false);
					}
				}
			} else {
				if (!world.isClientSide) {
					heal(4);
					heldItem.consumeItem(player);
				}
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
					this.attackTime = 20;
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

		List<Entity> nearbyEntities = world.getEntitiesWithinAABB(Entity.class, AABB.getBoundingBoxFromPool(x, y - 6.0F, z, x + 16.0F, y + 6.0F, z + 16.0F));

		if (!nearbyEntities.isEmpty() && random.nextInt(20) == 0) {
			for (Entity entities : nearbyEntities) {
				// Check for an EntityItem with an ID of string.

				if (boredom-- <= 0) {
					if (entities instanceof EntityItem) {
						ItemStack stringItem = ((EntityItem) entities).item;
						if (stringItem.itemID == Item.string.id) {
							setTarget(entities);

							if (entities.collidesWith(this)) {
								entities.xd = 0.05F;
								entities.yd = 0.10F;
							}

							if (boredom <= -400) {
								setTarget(null);
								boredom = random.nextInt(400) + 800;
							}
						}
					}
				}

				// Bird check!
				if (entities instanceof BirdEntity) {
					entityToAttack = entities;
				}
			}
		}

		if (isTamed) {
			potty--;
			List<TileEntity> tileEntities = world.loadedTileEntityList;

			if (!tileEntities.isEmpty()) {
				for (TileEntity tileEntity : tileEntities) {
					if (tileEntity instanceof LitterboxTile && !((LitterboxTile) tileEntity).isFilthy && potty <= 0) {
						pathToEntity = world.getEntityPathToXYZ(this, tileEntity.x, tileEntity.y, tileEntity.z, 16.0F);
						if (distanceToSqr(tileEntity.x, tileEntity.y, tileEntity.z) < 4.5F && !isPassenger()) {
							setPos(tileEntity.x, tileEntity.y, tileEntity.z);
							startRiding((IVehicle) tileEntity);
							usingPottyTime = 200;
						}

						if (isPassenger()) {
							if (usingPottyTime-- <= 200) {
								((LitterboxTile) tileEntity).ejectRider();
								((LitterboxTile) tileEntity).isFilthy = true;
								usingPottyTime = 0;
								potty = random.nextInt(200) + 200;
							}
						}
					}
				}
			}
		}

		// EXPERIMENTAL //
		// Player follow code for the upcoming 7.3 release. Follow items are: Fish.
		EntityPlayer player = world.getClosestPlayerToEntity(this, 16.0);
		if (player != null && (player.distanceToSqr(x, y, z) > 4.0)) {
			ItemStack heldStack = player.getCurrentEquippedItem();
			if (heldStack != null && heldStack.itemID == Item.foodFishRaw.id) {
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
		tag.putInt("Skin", skin);
		tag.putInt("Potty", potty);

		if (isTamed) tag.putString("Owner", ownerName);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		isTamed = tag.getBoolean("IsTamed");
		skin = tag.getInteger("Skin");
		potty = tag.getInteger("Potty");

		if (isTamed) ownerName = tag.getString("Owner");
	}
}
