package teamport.creatures.extra.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.NamedSoundRepository;
import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.data.registry.Registry;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.creatures.MoreMobs;

@Environment(EnvType.CLIENT)
@Mixin(value = SoundRepository.class, remap = false)
public abstract class SoundRepositoryMixin extends Registry<NamedSoundRepository> {

	@Shadow
	public abstract void registerNamespace(@NotNull String namespace);

	@Inject(method = "<init>", at = @At("TAIL"))
	private void mm_registerNamespace(CallbackInfo ci) {
		registerNamespace(MoreMobs.MOD_ID);
	}
}
