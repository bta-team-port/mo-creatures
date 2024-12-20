package teamport.creatures.extra.mixin;

import net.minecraft.core.entity.SpawnListEntry;
import net.minecraft.core.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import teamport.creatures.MMConfig;
import teamport.creatures.core.entity.mob.MobFox;

import java.util.List;

@Mixin(value = Biome.class, remap = false)
public abstract class BiomeMixin {
	@Shadow
	protected List<SpawnListEntry> spawnableCreatureList;

	@Unique
	private int creatures_getFreq(String entity) {
		return MMConfig.cfg.getInt("SpawnFrequencies." + entity);
	}

	@Inject(method = "<init>", at = @At("TAIL"))
	private void creatures_addMobs(CallbackInfo ci) {
		spawnableCreatureList.add(new SpawnListEntry(MobFox.class, creatures_getFreq("fox")));
	}
}
