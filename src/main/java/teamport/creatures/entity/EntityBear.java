package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.util.phys.AABB;
import net.minecraft.core.world.World;

import java.util.List;

public class EntityBear extends EntityAnimal {
	private boolean attackedByPlayer;
	public EntityBear(World world) {
		super(world);
		this.setSize(2.0F, 2.0F);
		this.health = 30;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/bear/0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/bear/0.png";
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (attacker instanceof EntityPlayer) {
			this.attackedByPlayer = true;
		}

		return attacker != this && super.hurt(attacker, damage, type);
	}

	@Override
	protected Entity findPlayerToAttack() {
		return attackedByPlayer ? world.getClosestPlayerToEntity(this, 16.0D) : null;
	}

	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (!(entity instanceof EntityItem)) {
			if (!(distance > 2.0F) || !(distance < 6.0F) || this.random.nextInt(10) != 0) {
				if ((double)distance < 3 && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
					this.attackTime = 20;
					entity.hurt(this, 2, DamageType.COMBAT);
				}
			} else if (this.onGround) {
				double d = entity.x - this.x;
				double d1 = entity.z - this.z;
				float f1 = MathHelper.sqrt_double(d * d + d1 * d1);
				this.xd = d / (double)f1 * 0.5 * 0.8F + this.xd * 0.2F;
				this.zd = d1 / (double)f1 * 0.5 * 0.8F + this.zd * 0.2F;
				this.yd = 0.4F;
			}
		}
	}

	@Override
	protected void updatePlayerActionState() {
		if (this.entityToAttack == null && !this.hasPath() && this.world.rand.nextInt(100) == 0) {
			List<Entity> nearbyAnimals = this.world
				.getEntitiesWithinAABBExcludingEntity(
					this, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)
				);

			if (!nearbyAnimals.isEmpty()) {
				Entity getNearbyAnimal = nearbyAnimals.get(this.world.rand.nextInt(nearbyAnimals.size()));

				if (getNearbyAnimal instanceof EntityAnimal) {
					this.setTarget(getNearbyAnimal);
				}
			}
		} else {
			super.updatePlayerActionState();
		}

		if (this.getTarget() instanceof EntityBear) {
			this.setTarget(null);
		}
	}


	public String getLivingSound() {
		return "creatures.beargrunt";
	}

	protected String getHurtSound() {
		return "creatures.bearhurt";
	}

	protected String getDeathSound() {
		return "creatures.beardeath";
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("AttackedByPlayer", this.attackedByPlayer);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.attackedByPlayer = tag.getBoolean("AttackedByPlayer");
	}
}
