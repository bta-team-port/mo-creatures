package teamport.creatures.entity;

import net.minecraft.core.entity.animal.EntityAnimal;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import useless.dragonfly.model.entity.AnimationState;

public class EntityBunny extends EntityAnimal {
	public AnimationState jumpState = new AnimationState();
	int jumpDelay;

	public EntityBunny(World world) {
		super(world);
		jumpDelay = 0;
		setSize(0.4F, 0.25F);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/entity/bunny/0.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/entity/bunny/0.png";
	}

	@Override
	protected void updatePlayerActionState() {
		this.tryToDespawn();
		if (this.onGround && this.jumpDelay-- <= 0) {

			this.jumpDelay = this.random.nextInt(20) + 10;
			this.isJumping = true;
			jumpState.start(tickCount);
			world.playSoundAtEntity(this, "creatures.rabbitland", 1.0F, 1.0F);

			this.moveStrafing = 1.0F - this.random.nextFloat() * 2.0F;
			this.moveForward = 1.0F;
		} else {
			this.isJumping = false;
			if (this.onGround) {
				this.moveStrafing = this.moveForward = 0.0F;
				jumpState.stop();
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
}
