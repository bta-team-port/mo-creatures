package teamport.creatures.core.block;

import teamport.creatures.MMConfig;

public final class MMBlocks {
	private static int startingID = MMConfig.cfg.getInt("IDs.startingBlockID");
	private static int nextID() {
		return ++startingID;
	}


}
