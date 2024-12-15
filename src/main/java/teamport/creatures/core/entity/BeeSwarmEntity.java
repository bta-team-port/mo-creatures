package teamport.creatures.core.entity;

import net.minecraft.core.entity.EntityFlying;
import net.minecraft.core.entity.animal.IAnimal;
import net.minecraft.core.world.World;

public class BeeSwarmEntity extends EntityFlying implements IAnimal {
	public BeeSwarmEntity(World world) {
		super(world);
	}
}
