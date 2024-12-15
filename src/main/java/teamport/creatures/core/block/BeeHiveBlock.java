package teamport.creatures.core.block;

import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.item.Item;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;

public class BeeHiveBlock extends Block {
	public BeeHiveBlock(String key, int id) {
		super(key, id, Material.wood);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, Side side, int meta, EntityPlayer player, Item item) {
		if (player.getGamemode().areMobsHostile()) {

		}
	}
}
