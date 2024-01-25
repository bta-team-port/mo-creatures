package teamport.creatures.mixin;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.creatures.entity.*;

import java.util.List;

@Mixin(value = Biome.class, remap = false)
public abstract class BiomeMixin {
	@Shadow
	protected List<SpawnListEntry> spawnableCreatureList;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void creatures_addMobs(CallbackInfo ci) {
		spawnableCreatureList.add(new SpawnListEntry(EntityFox.class, 10));
		spawnableCreatureList.add(new SpawnListEntry(EntityFoxArctic.class, 5));
		spawnableCreatureList.add(new SpawnListEntry(EntityBoar.class, 50));
		spawnableCreatureList.add(new SpawnListEntry(EntityBunny.class, 40));
		spawnableCreatureList.add(new SpawnListEntry(EntityBird.class, 20));
		spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 20));
		spawnableCreatureList.add(new SpawnListEntry(EntityHorseUnicorn.class, 1));
		spawnableCreatureList.add(new SpawnListEntry(EntityHorsePegasus.class, 1));
	}
}
