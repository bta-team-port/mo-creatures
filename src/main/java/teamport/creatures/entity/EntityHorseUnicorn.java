package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.Entity;
import net.minecraft.core.entity.EntityItem;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.util.helper.DamageType;
import net.minecraft.core.util.helper.MathHelper;
import net.minecraft.core.world.World;

public class EntityHorseUnicorn extends EntityHorse {
	private boolean angry;
	private int angerCounter = 0;
	public EntityHorseUnicorn(World world) {
		super(world);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/horse/3.png";
	}

	@Override
	protected void updatePlayerActionState() {
		super.updatePlayerActionState();
		if (annoyance > 260) {
			this.angerCounter = 1200;
		}

		if (this.angerCounter > 0) {
			--this.angerCounter;
			this.angry = true;
		} else {
			this.angry = false;
		}

		if (this.tamed) this.angry = false;

		System.out.println(angerCounter);
	}

	@Override
	public boolean hurt(Entity attacker, int damage, DamageType type) {
		if (attacker instanceof EntityPlayer) {
			this.angerCounter = 1200;
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
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putBoolean("Angry", this.angry);
		tag.putInt("AngerCounter", this.angerCounter);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		this.angry = tag.getBoolean("Angry");
		this.angerCounter = tag.getInteger("AngerCounter");
	}
}
