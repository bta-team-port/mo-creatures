package teamport.creatures.mixin;

import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin extends EntityLiving {
	public EntityPlayerMixin(World world) {
		super(world);
	}

	@Override
	public double getRideHeight() {
		return bbHeight * 0.20;
	}

	@Inject(method = "moveEntityWithHeading", at = @At("TAIL"))
	private void creatures_movePlayerPassenger(float moveStrafing, float moveForward, CallbackInfo ci) {
		if (this.passenger != null) passenger.yRot = yRot;
	}
}
