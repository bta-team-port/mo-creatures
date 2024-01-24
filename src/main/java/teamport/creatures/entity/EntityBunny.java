package teamport.creatures.entity;

import com.mojang.nbt.CompoundTag;
import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.world.World;
import useless.dragonfly.model.entity.AnimationState;

public class EntityBunny extends EntityAnimal {
	public AnimationState jumpState = new AnimationState();
	int jumpDelay;
	int skinVariant;

	public EntityBunny(World world) {
		super(world);
		this.health = 5;
		this.jumpDelay = 0;
		this.setSize(0.4F, 0.25F);
		this.skinVariant = random.nextInt(5);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/bunny/" + skinVariant + ".png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/bunny/0.png";
	}

	@Override
	protected void updatePlayerActionState() {
		++this.entityAge;
		this.tryToDespawn();
		if (this.isInWater() || this.isInLava()) this.isJumping = true;

		if (this.onGround && this.jumpDelay-- <= 0) {

			this.jumpDelay = this.random.nextInt(20) + 10;
			this.isJumping = true;
			jumpState.start(tickCount);
			world.playSoundAtEntity(this, "creatures.rabbitland", 1.0F, 1.0F);

			this.moveStrafing = 1.0F - this.random.nextFloat() * 2.0F;
			this.moveForward = 1.0F;
			this.roamRandomPath();
		} else {
			this.isJumping = false;
			if (this.onGround) {
				jumpState.stop();
				this.moveStrafing = this.moveForward = 0.0F;

				if (this.random.nextFloat() < 0.05F) this.randomYawVelocity = (this.random.nextFloat() - 0.5F) * 20.0F;
				this.yRot += this.randomYawVelocity;
				this.xRot = this.defaultPitch;
			}
		}
	}

	@Override
	protected String getDeathSound() {
		return "creatures.rabbitdeath";
	}

	@Override
	protected String getHurtSound() {
		return "creatures.rabbithurt";
	}

	@Override
	public String getLivingSound() {
		return null;
	}

	@Override
	public void addAdditionalSaveData(CompoundTag tag) {
		super.addAdditionalSaveData(tag);
		tag.putInt("SkinVariant", skinVariant);
	}

	@Override
	public void readAdditionalSaveData(CompoundTag tag) {
		super.readAdditionalSaveData(tag);
		skinVariant = tag.getInteger("SkinVariant");
	}
}
