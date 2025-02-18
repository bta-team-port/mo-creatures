package teamport.creatures.core.entity.mob;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.ItemFood;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import net.minecraft.core.world.season.SeasonWinter;
import org.jetbrains.annotations.NotNull;
import teamport.creatures.MoreMobs;

import java.util.List;

public class MobBear extends MobAnimal {
	public static final int MASK_ANGRY = 0b0000_0001;
	public static final int MASK_ARCTIC = 0b0000_0010;
	public static final int MASK_ARCTIC_ANGRY = 0b0000_0100;
	public static final int DATA_GENERIC_FLAGS = 16;
	private int entityCheck;

	public MobBear(World world) {
		super(world);
		textureIdentifier = new NamespaceID(MoreMobs.MOD_ID, "bear");
		scoreValue = 250;
		moveSpeed = 0.9f;
		heartsHalvesLife = 40;
		entityCheck = 1800 + random.nextInt(1800);

		setSize(1, 1);
	}

	@Override
	protected void defineSynchedData() {
		super.defineSynchedData();
		entityData.define(DATA_GENERIC_FLAGS, (byte) 0, Byte.class);
	}

	@Override
	protected boolean makeStepSound() {
		return false;
	}

	public boolean isBearAngry() {
		return (entityData.getByte(DATA_GENERIC_FLAGS) & MASK_ANGRY) != 0 ||
			(entityData.getByte(DATA_GENERIC_FLAGS) & MASK_ARCTIC_ANGRY) != 0;
	}

	public boolean isBearPolar() {
		return (entityData.getByte(DATA_GENERIC_FLAGS) & MASK_ARCTIC) != 0;
	}

	@Override
	public String getEntityTexture() {
		if (isBearPolar()) {
			return isBearAngry() ? "/assets/creatures/textures/entity/bear_polar_angry/" + getTextureReference() + ".png" :
				"/assets/creatures/textures/entity/bear_polar/" + getTextureReference() + ".png";
		}

		return isBearAngry() ? "/assets/creatures/textures/entity/bear_angry/" + getTextureReference() + ".png" :
			"/assets/creatures/textures/entity/bear/" + getTextureReference() + ".png";
	}

	public void setBearAngry(boolean flag) {
		byte data = entityData.getByte(DATA_GENERIC_FLAGS);
		if (flag) {
			entityData.set(DATA_GENERIC_FLAGS, isBearPolar() ? (byte) ( data | MASK_ARCTIC_ANGRY) : (byte) (data | MASK_ANGRY));
		} else {
			entityData.set(DATA_GENERIC_FLAGS, isBearPolar() ? (byte) (data & ~MASK_ARCTIC_ANGRY) : (byte) (data & ~MASK_ANGRY));
		}
	}

	public void setBearPolar(boolean flag) {
		byte data = entityData.getByte(DATA_GENERIC_FLAGS);
		if (flag) {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data | MASK_ARCTIC));
		} else {
			entityData.set(DATA_GENERIC_FLAGS, (byte) (data & ~MASK_ARCTIC));
		}
	}

	@Override
	public void addAdditionalSaveData(@NotNull CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("IsAngry", isBearAngry());
		tag.putBoolean("IsArctic", isBearPolar());
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setBearAngry(tag.getBoolean("IsAngry"));
		setBearPolar(tag.getBoolean("IsArctic"));
	}

	@Override
	public String getLivingSound() {
		return "creatures:mob.bear";
	}

	@Override
	protected String getDeathSound() {
		return "creatures:mob.bear.death";
	}

	@Override
	protected String getHurtSound() {
		return "creatures:mob.bear.hurt";
	}

	@Override
	protected float getSoundVolume() {
		return 0.4f;
	}

	@Override
	protected void checkForPlayerHoldingItem() {
		if (target == null) {
			closestPlayer = world.getClosestPlayer(this.x, this.y, this.z, 10);
		}

		if (closestPlayer != null && closestPlayer.gamemode != Gamemode.creative) {
			if (closestPlayer.getHeldItem() != null && closestPlayer.getHeldItem().getItem() instanceof ItemFood) {
				setTarget(closestPlayer);
			}
		}
	}

	public void checkForEntitiesNearBear() {
		if (entityCheck-- <= 0) {
			entityCheck = 1800 + random.nextInt(1800);

			List<Entity> entitiesNearBear = world.getEntitiesWithinAABBExcludingEntity(this,
				bb.grow(10, 8, 10));

			if (!entitiesNearBear.isEmpty() && isAlive()) {
				for (Entity entity : entitiesNearBear) {
					if (entity instanceof MobAnimal && !(entity instanceof MobBear) && target == null) {
						setTarget(entity);
						break;
					}
				}
			}
		}
	}

	@Override
	protected void updateAI() {
		super.updateAI();
		checkForEntitiesNearBear();

		setBearAngry(target != null && target.isAlive());
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (attacker != null && !(attacker instanceof Player) && !(attacker instanceof ProjectileArrow)) {
			damage = (damage + 1) / 2;
		}

		if (!super.hurt(attacker, damage, type)) {
			return false;
		} else if (!isBearAngry()) {
			if ((attacker instanceof Player) && (((Player) attacker).getGamemode() != Gamemode.creative)) {
				setBearAngry(true);
				setTarget(attacker);
			}

			if ((attacker instanceof ProjectileArrow) && (((ProjectileArrow) attacker).owner != null)) {
				setBearAngry(true);
				setTarget(((ProjectileArrow) attacker).owner);
			}
		} else if (attacker != this && attacker != null) {
			setTarget(attacker);
		}

		return true;
	}

	@Override
	protected void attackEntity(@NotNull Entity entity, float distance) {
		if (distance > 3 && distance < 6 && random.nextInt(10) == 0) {
			if (onGround) {
				double dx = entity.x - x;
				double dz = entity.z - z;

				double targetDistance = Math.sqrt((dx * dx) + (dz * dz));
				xd = ((dx / targetDistance) * 0.5 * 0.8) + (xd * 0.2);
				yd = 0.4;
				zd = ((dz / targetDistance) * 0.5 * 0.8) + (zd * 0.2);
			}
		} else if ((distance <= 3f) && (entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY)) {
			attackTime = 20;
			byte damage = 4;

			entity.hurt(this, damage, DamageType.COMBAT);
		}
	}

	@Override
	public void spawnInit() {
		int mhX = MathHelper.floor(this.x);
		int mhY = MathHelper.floor(this.bb.minY);
		int mhZ = MathHelper.floor(this.z);
		Biome biome = world.getBlockBiome(mhX, mhY, mhZ);

		if (biome == Biomes.OVERWORLD_GLACIER || biome == Biomes.OVERWORLD_TUNDRA) {
			setBearPolar(true);
		}
	}

	@Override
	public boolean canSpawnHere() {
		int mhX = MathHelper.floor(this.x);
		int mhY = MathHelper.floor(this.bb.minY);
		int mhZ = MathHelper.floor(this.z);
		Biome biome = world.getBlockBiome(mhX, mhY, mhZ);

		if (world.seasonManager.getCurrentSeason() instanceof SeasonWinter &&
			(biome != Biomes.OVERWORLD_GLACIER || biome != Biomes.OVERWORLD_TUNDRA)) {
			return false;
		}

		if (world.checkIfAABBIsClear(bb) && !world.getIsAnyLiquid(bb)) {
			if (getBlockPathWeight(mhX, mhY, mhZ) > 0 && world.canBlockSeeTheSky(mhX, mhY, mhZ)) {
				if (world.getFullBlockLightValue(mhX, mhY, mhZ) > 8) {
					return (biome == Biomes.OVERWORLD_GLACIER || biome == Biomes.OVERWORLD_TUNDRA) || super.canSpawnHere();
				}
			}
		}
		return false;
	}

	@Override
	public int getMaxSpawnedInChunk() {
		return 2;
	}
}
