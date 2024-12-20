package teamport.creatures;

import net.minecraft.client.sound.SoundRepository;
import turniplabs.halplibe.util.ClientStartEntrypoint;

public class MoreMobsClient implements ClientStartEntrypoint {

	@Override
	public void beforeClientStart() {
	}

	@Override
	public void afterClientStart() {
		SoundRepository.SOUNDS.registerNamespace(MoreMobs.MOD_ID);
	}
}
