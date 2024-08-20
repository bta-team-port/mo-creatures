package teamport.creatures.extra.mixin;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.creatures.core.entity.*;

import java.util.List;

@Mixin(value = Biome.class, remap = false)
public abstract class BiomeMixin {
	@Shadow
	protected List<SpawnListEntry> spawnableCreatureList;

	@Inject(method = "<init>", at = @At("TAIL"))
	private void creatures_addMobs(CallbackInfo ci) {
		spawnableCreatureList.add(new SpawnListEntry(FoxEntity.class, 25));
		spawnableCreatureList.add(new SpawnListEntry(ArcticFoxEntity.class, 25));
		spawnableCreatureList.add(new SpawnListEntry(BoarEntity.class, 25));
		spawnableCreatureList.add(new SpawnListEntry(BunnyEntity.class, 51));
		spawnableCreatureList.add(new SpawnListEntry(BirdEntity.class, 102));
		spawnableCreatureList.add(new SpawnListEntry(DeerEntity.class, 25));
		spawnableCreatureList.add(new SpawnListEntry(HorseEntity.class, 51));
		spawnableCreatureList.add(new SpawnListEntry(UnicornEntity.class, 1));
		spawnableCreatureList.add(new SpawnListEntry(PegasusEntity.class, 1));
		spawnableCreatureList.add(new SpawnListEntry(BearEntity.class, 25));
		spawnableCreatureList.add(new SpawnListEntry(PolarBearEntity.class, 55));
		spawnableCreatureList.add(new SpawnListEntry(DeerEntity.class, 51));
		spawnableCreatureList.add(new SpawnListEntry(DuckEntity.class, 51));
		spawnableCreatureList.add(new SpawnListEntry(KittyEntity.class, 1));
	}
}
