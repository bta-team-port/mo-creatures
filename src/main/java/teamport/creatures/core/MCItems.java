package teamport.creatures.core;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPlaceable;
import teamport.creatures.MCConfig;
import turniplabs.halplibe.helper.ItemBuilder;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCItems {
	private static int startingID = MCConfig.cfg.getInt("IDs.startingItemID");
	private static int nextID() {
		return ++startingID;
	}

	public static final Item LITTERBOX = new ItemBuilder(MOD_ID)
		.setStackSize(1)
		.build(new ItemPlaceable("litterbox", nextID(), MCBlocks.LITTERBOX));

	public static void initializeItems() {
	}
}
