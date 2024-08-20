package teamport.creatures.core;

import net.minecraft.client.render.block.model.BlockModelEmpty;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import net.minecraft.core.sound.BlockSounds;
import teamport.creatures.core.block.LitterboxBlock;
import teamport.creatures.client.render.block.LitterboxRenderer;
import teamport.creatures.core.block.entity.LitterboxTile;
import turniplabs.halplibe.helper.BlockBuilder;
import turniplabs.halplibe.helper.EntityHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCBlocks {
	public static Block LITTERBOX;

	public static void initializeBlocks() {
		LITTERBOX = new BlockBuilder(MOD_ID)
			.setBlockSound(BlockSounds.WOOD)
			.setBlockModel(BlockModelEmpty::new)
			.setTags(BlockTags.NOT_IN_CREATIVE_MENU)
			.build(new LitterboxBlock("litterbox", 1700));
	}

	public static void initializeTiles() {
		EntityHelper.createSpecialTileEntity(LitterboxTile.class, "Litterbox", LitterboxRenderer::new);
	}
}
