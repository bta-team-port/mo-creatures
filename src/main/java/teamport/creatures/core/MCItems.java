package teamport.creatures.core;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPlaceable;
import turniplabs.halplibe.helper.ItemBuilder;
import turniplabs.halplibe.helper.ItemHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCItems {
	public static Item LITTERBOX;

	public static void initializeItems() {
		LITTERBOX = new ItemBuilder(MOD_ID)
			.build(new ItemPlaceable("litterbox", 16700, MCBlocks.LITTERBOX));
	}
}
