package teamport.creatures.core.entity;

import net.minecraft.core.world.World;
import net.minecraft.core.world.biome.Biome;
import net.minecraft.core.world.biome.Biomes;

public class PolarBearEntity extends BearEntity {
	public PolarBearEntity(World world) {
		super(world);
	}

	@Override
	public String getEntityTexture() {
		return "/assets/creatures/textures/entity/bear/1.png";
	}

	@Override
	public String getDefaultEntityTexture() {
		return "/assets/creatures/textures/entity/bear/1.png";
	}

	@Override
	public boolean getCanSpawnHere() {
		Biome biome = this.world.getBlockBiome((int) x, (int) y, (int) z);
		return (biome == Biomes.OVERWORLD_TUNDRA || biome == Biomes.OVERWORLD_TAIGA) && super.getCanSpawnHere();
	}
}
