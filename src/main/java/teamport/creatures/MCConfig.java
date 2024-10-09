package teamport.creatures;

import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCConfig {
	private static final Toml PROPERTIES = new Toml("Mo' Creatures TOML Config");
	public static TomlConfigHandler cfg;

	static {
		PROPERTIES.addCategory("IDs")
			.addEntry("startingItemID", 17000)
			.addEntry("startingBlockID", 1100)
			.addEntry("startingEntityID", 100);

		PROPERTIES.addCategory("SpawnFrequencies")
			.addEntry("fox", 25)
			.addEntry("arcticFox", 25)
			.addEntry("boar", 25)
			.addEntry("bunny", 51)
			.addEntry("bird", 102)
			.addEntry("deer", 25)
			.addEntry("horse", 51)
			.addEntry("unicorn", 1)
			.addEntry("pegasus", 1)
			.addEntry("bear", 13)
			.addEntry("polarBear", 25)
			.addEntry("duck", 51)
			.addEntry("kitty", 2);

		cfg = new TomlConfigHandler(MOD_ID, PROPERTIES);
	}
}
