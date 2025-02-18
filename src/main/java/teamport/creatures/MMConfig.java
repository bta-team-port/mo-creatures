package teamport.creatures;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static teamport.creatures.MoreMobs.MOD_ID;

public class MMConfig {
	private static final Toml TOML = new Toml("Mo' Creatures TOML Config");
	public static TomlConfigHandler cfg;

	static {
		TOML.addCategory("IDs")
			.addEntry("startingItemID", 17000)
			.addEntry("startingBlockID", 1100)
			.addEntry("startingEntityID", 100);

		TOML.addCategory("SpawnFrequencies")
			.addEntry("bear", 25)
			.addEntry("bird", 102)
			.addEntry("bunny", 102)
			.addEntry("deer", 51)
			.addEntry("fox", 25)
			.addEntry("horse", 51)
			.addEntry("unicorn", 1)
			.addEntry("pegasus", 1)
			.addEntry("kitty", 2);

		cfg = new TomlConfigHandler(MOD_ID, TOML);
	}
}
