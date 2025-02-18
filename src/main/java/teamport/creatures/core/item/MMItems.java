package teamport.creatures.core.item;

import net.minecraft.core.item.Items;
import teamport.creatures.MMConfig;

public final class MMItems {
	private static int startingID = MMConfig.cfg.getInt("IDs.startingItemID");
	private static int nextID() {
		return ++startingID;
	}

	public static void addItemsToTags() {
		Items.EGG_CHICKEN.withTags(MMItemTags.FOXES_FAVORITE_ITEM);
		Items.SEEDS_WHEAT.withTags(MMItemTags.BIRDS_FAVORITE_ITEM);
		Items.SEEDS_PUMPKIN.withTags(MMItemTags.BIRDS_FAVORITE_ITEM);
		Items.WHEAT.withTags(MMItemTags.BUNNIES_FAVORITE_ITEM);
	}
}
