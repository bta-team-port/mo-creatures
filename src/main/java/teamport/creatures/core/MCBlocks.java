package teamport.creatures.core;

import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import teamport.creatures.MCConfig;
import teamport.creatures.core.block.BeeHiveBlock;
import teamport.creatures.core.block.LitterboxBlock;
import teamport.creatures.client.render.block.LitterboxRenderer;
import teamport.creatures.core.block.LitterboxEntity;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCBlocks {
	private static int startingID = MCConfig.cfg.getInt("IDs.startingBlockID");
	private static int nextID() {
		return ++startingID;
	}

	public static final Block LITTERBOX  = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.WOOD)
		.setBlockModel(BlockModelEmpty::new)
		.setTags(BlockTags.NOT_IN_CREATIVE_MENU)
		.build(new LitterboxBlock("litterbox", nextID()));

	public static final Block BEE_HIVE = new BlockBuilder(MOD_ID)
		.setBlockSound(BlockSounds.WOOD)
		.setTopTexture("creatures:block/bee_hive/top")
		.setSideTextures("creatures:block/bee_hive/side")
		.setBottomTexture("creatures:block/bee_hive/bottom")
		.setHardness(2.0f)
		.setTags(BlockTags.MINEABLE_BY_AXE)
		.build(new BeeHiveBlock("bee_hive", nextID()));

	public static void initializeBlocks() {
	}

	public static void initializeTiles() {
		EntityHelper.createSpecialTileEntity(LitterboxEntity.class, "Litterbox", LitterboxRenderer::new);
	}
}
