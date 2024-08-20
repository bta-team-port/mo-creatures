package teamport.creatures.extra.mixin;

import net.minecraft.core.entity.player.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = EntityPlayer.class, remap = false)
public abstract class EntityPlayerMixin {
	@Unique
	EntityPlayer thisAs = (EntityPlayer) (Object) this;

	@Unique
	public double getRideHeight() {
		return thisAs.bbHeight * 0.20;
	}

	@Inject(method = "moveEntityWithHeading", at = @At("TAIL"))
	private void creatures_movePlayerPassenger(float moveStrafing, float moveForward, CallbackInfo ci) {
		if (thisAs.passenger != null) thisAs.passenger.yRot = thisAs.yRot;
	}
}
