package teamport.creatures.extra.mixin;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.creatures.MCConfig;
import teamport.creatures.core.entity.*;

import java.util.List;

@Mixin(value = Biome.class, remap = false)
public abstract class BiomeMixin {
	@Shadow
	protected List<SpawnListEntry> spawnableCreatureList;

	@Unique
	private int creatures_getFreq(String entity) {
		return MCConfig.cfg.getInt("SpawnFrequencies." + entity);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void creatures_addMobs(CallbackInfo ci) {
		spawnableCreatureList.add(new SpawnListEntry(FoxEntity.class, creatures_getFreq("fox")));
		spawnableCreatureList.add(new SpawnListEntry(ArcticFoxEntity.class, creatures_getFreq("arcticFox")));
		spawnableCreatureList.add(new SpawnListEntry(BoarEntity.class, creatures_getFreq("boar")));
		spawnableCreatureList.add(new SpawnListEntry(BunnyEntity.class, creatures_getFreq("bunny")));
		spawnableCreatureList.add(new SpawnListEntry(BirdEntity.class, creatures_getFreq("bird")));
		spawnableCreatureList.add(new SpawnListEntry(DeerEntity.class, creatures_getFreq("deer")));
		spawnableCreatureList.add(new SpawnListEntry(HorseEntity.class, creatures_getFreq("horse")));
		spawnableCreatureList.add(new SpawnListEntry(UnicornEntity.class, creatures_getFreq("unicorn")));
		spawnableCreatureList.add(new SpawnListEntry(PegasusEntity.class, creatures_getFreq("pegasus")));
		spawnableCreatureList.add(new SpawnListEntry(BearEntity.class, creatures_getFreq("bear")));
		spawnableCreatureList.add(new SpawnListEntry(PolarBearEntity.class, creatures_getFreq("polarBear")));
		spawnableCreatureList.add(new SpawnListEntry(DuckEntity.class, creatures_getFreq("duck")));
		spawnableCreatureList.add(new SpawnListEntry(KittyEntity.class, creatures_getFreq("kitty")));
	}
}
