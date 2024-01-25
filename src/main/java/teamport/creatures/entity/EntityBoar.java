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

public class EntityBoar extends EntityAnimal {
	boolean angry;
	int angerCounter;

	public EntityBoar(World world) {
		super(world);
		setSize(0.9F, 0.9F);
		health = 10;
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/boar/0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/boar/0.png";
	}

	@Override
	public void tick() {
		super.tick();
		if (angerCounter-- > 0) {
			angry = true;
		} else {
			angry = false;
		}
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (attacker instanceof EntityPlayer) {
			this.angerCounter = 400;
		}
		return super.hurt(attacker, damage, type);
	}

	@Override
	protected Entity findPlayerToAttack() {
		return angry ? world.getClosestPlayerToEntity(this, 16.0D) : null;
	}


	@Override
	protected void attackEntity(Entity entity, float distance) {
		if (!(entity instanceof EntityItem)) {
			if (!(distance > 2.0F) || !(distance < 6.0F) || this.random.nextInt(10) != 0) {
				if ((double)distance < 1.5 && entity.bb.maxY > this.bb.minY && entity.bb.minY < this.bb.maxY) {
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
		super.updatePlayerActionState();
		if (this.entityToAttack == null && !this.hasPath() && this.world.rand.nextInt(200) == 0) {
			List<Entity> nearbyPlayers = this.world
				.getEntitiesWithinAABB(
					EntityPlayer.class, AABB.getBoundingBoxFromPool(this.x, this.y, this.z, this.x + 1.0, this.y + 1.0, this.z + 1.0).expand(16.0, 4.0, 16.0)
				);
			if (!nearbyPlayers.isEmpty())
				this.setTarget(nearbyPlayers.get(this.world.rand.nextInt(nearbyPlayers.size())));
		}
	}

	@Override
	public void playLivingSound() {
		world.playSoundAtEntity(this,
			getLivingSound(),
			getSoundVolume(),
			(this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 0.6F);
	}

	public String getLivingSound() {
		return "mob.pig";
	}

	protected String getHurtSound() {
		return "mob.pig";
	}

	protected String getDeathSound() {
		return "mob.pigdeath";
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("Anger", this.angerCounter);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.angerCounter = tag.getInteger("Anger");
	}
}
