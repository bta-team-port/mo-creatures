package teamport.creatures.core.entity.mob;

import com.mojang.nbt.tags.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.animal.MobAnimal;
import net.minecraft.core.entity.animal.MobChicken;
import net.minecraft.core.entity.player.Player;
import net.minecraft.core.entity.projectile.ProjectileArrow;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.gamemode.Gamemode;
import net.minecraft.core.util.collection.NamespaceID;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;
import org.jetbrains.annotations.NotNull;
import teamport.creatures.MoreMobs;
import teamport.creatures.core.item.MMItemTags;

import java.util.List;

public class MobFox extends MobAnimal {
	public static final int MASK_ANGRY = 0b0000_0001;
	public static final int MASK_ARCTIC = 0b0000_0010;
	public static final int MASK_ARCTIC_ANGRY = 0b0000_0100;
	public static final int DATA_GENERIC_FLAGS = 16;
	private int entityCheck;

	public MobFox(World world) {
		super(world);
		textureIdentifier = new NamespaceID(MoreMobs.MOD_ID, "fox");
		scoreValue = 125;
		moveSpeed = 1.1f;
		entityCheck = 1800 + random.nextInt(1800);
		heartsHalvesLife = 10;

		setSize(0.8F, 0.8F);
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

	public boolean isFoxAngry() {
		return (entityData.getByte(DATA_GENERIC_FLAGS) & MASK_ANGRY) != 0 ||
			(entityData.getByte(DATA_GENERIC_FLAGS) & MASK_ARCTIC_ANGRY) != 0;
	}

	public boolean isFoxArctic() {
		return (entityData.getByte(DATA_GENERIC_FLAGS) & MASK_ARCTIC) != 0;
	}

	@Override
	public String getEntityTexture() {
		if (isFoxArctic()) {
			return isFoxAngry() ? "/assets/creatures/textures/entity/fox_arctic_angry/" + getTextureReference() + ".png" :
				"/assets/creatures/textures/entity/fox_arctic/" + getTextureReference() + ".png";
		}

		return isFoxAngry() ? "/assets/creatures/textures/entity/fox_angry/" + getTextureReference() + ".png" :
			"/assets/creatures/textures/entity/fox/" + getTextureReference() + ".png";
	}

	public void setFoxAngry(boolean flag) {
		byte data = entityData.getByte(DATA_GENERIC_FLAGS);
		if (flag) {
			entityData.set(DATA_GENERIC_FLAGS, isFoxArctic() ? (byte) ( data | MASK_ARCTIC_ANGRY) : (byte) (data | MASK_ANGRY));
		} else {
			entityData.set(DATA_GENERIC_FLAGS, isFoxArctic() ? (byte) (data & ~MASK_ARCTIC_ANGRY) : (byte) (data & ~MASK_ANGRY));
		}
	}

	public void setFoxArctic(boolean flag) {
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
		tag.putBoolean("IsAngry", isFoxAngry());
		tag.putBoolean("IsArctic", isFoxArctic());
		tag.putInt("EntityCheckTimer", entityCheck);
	}

	@Override
	public void readAdditionalSaveData(@NotNull CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		setFoxAngry(tag.getBoolean("IsAngry"));
		setFoxArctic(tag.getBoolean("IsArctic"));
		entityCheck = tag.getInteger("EntityCheckTimer");
	}

	@Override
	public String getLivingSound() {
		return isFoxAngry() ? "creatures:mob.fox.angry" : "creatures:mob.fox";
	}

	@Override
	protected String getDeathSound() {
		return "creatures:mob.fox.death";
	}

	@Override
	protected String getHurtSound() {
		return "creatures:mob.fox.hurt";
	}

	@Override
	protected float getSoundVolume() {
		return 0.4f;
	}

	private void checkForEntitiesNearFox() {
		if (entityCheck-- <= 0) {
			entityCheck = 1800 + random.nextInt(1800);
			List<Entity> entitiesNearFox = world.getEntitiesWithinAABBExcludingEntity(this, bb.grow(10, 10, 10));

			if (!(entitiesNearFox.isEmpty()) && (isAlive())) {
				for (Entity entity : entitiesNearFox) {
					if (entity instanceof MobChicken && getTarget() == null) {
						setFoxAngry(true);
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
		checkForEntitiesNearFox();


		setFoxAngry(target != null && target.isAlive());
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (attacker != null && !(attacker instanceof Player) && !(attacker instanceof ProjectileArrow)) {
			damage = (damage + 1) / 2;
		}

		if (!super.hurt(attacker, damage, type)) {
			return false;
		} else if (!isFoxAngry()) {
			if ((attacker instanceof Player) && (((Player) attacker).getGamemode() != Gamemode.creative)) {
				setFoxAngry(true);
				setTarget(attacker);
			}

			if ((attacker instanceof ProjectileArrow) && (((ProjectileArrow) attacker).owner != null)) {
				setFoxAngry(true);
				setTarget(((ProjectileArrow) attacker).owner);
			}
		} else if (attacker != this && attacker != null) {
			setTarget(attacker);
		}

		return true;
	}

	@Override
	protected void attackEntity(@NotNull Entity entity, float distance) {
		if (distance > 2 && distance < 6 && random.nextInt(10) == 0) {
			if (onGround) {
				double dx = entity.x - x;
				double dz = entity.z - z;

				double targetDistance = Math.sqrt((dx * dx) + (dz * dz));
				xd = ((dx / targetDistance) * 0.5 * 0.8) + (xd * 0.2);
				yd = 0.4;
				zd = ((dz / targetDistance) * 0.5 * 0.8) + (zd * 0.2);
			}
		} else if ((distance <= 2) && (entity.bb.maxY > bb.minY && entity.bb.minY < bb.maxY)) {
			attackTime = 20;
			byte damage = 2;

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
			setFoxArctic(true);
		}
	}

	@Override
	public boolean canSpawnHere() {
		int mhX = MathHelper.floor(this.x);
		int mhY = MathHelper.floor(this.bb.minY);
		int mhZ = MathHelper.floor(this.z);
		Biome biome = world.getBlockBiome(mhX, mhY, mhZ);

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
	public boolean isFavouriteItem(ItemStack itemStack) {
		return itemStack != null && itemStack.getItem().hasTag(MMItemTags.FOXES_FAVORITE_ITEM);
	}
}
