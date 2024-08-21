package teamport.creatures.core;

import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemPlaceable;
import turniplabs.halplibe.helper.ArmorHelper;
import turniplabs.halplibe.helper.ItemBuilder;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCItems {
	public static final Item LITTERBOX = new ItemBuilder(MOD_ID)
		.build(new ItemPlaceable("litterbox", 16700, MCBlocks.LITTERBOX));;

	public static void initializeItems() {
	}
}
