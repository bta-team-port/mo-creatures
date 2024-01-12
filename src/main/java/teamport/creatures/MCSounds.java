package teamport.creatures;

import turniplabs.halplibe.helper.SoundHelper;

import static teamport.creatures.MoCreatures.MOD_ID;

public class MCSounds {

	public static void initializeSounds() {
		// Fox
		SoundHelper.Client.addSound(MOD_ID, "foxcall1.wav");
		SoundHelper.Client.addSound(MOD_ID, "foxcall2.wav");
		SoundHelper.Client.addSound(MOD_ID, "foxdying.wav");
		SoundHelper.Client.addSound(MOD_ID, "foxhurt1.wav");
		SoundHelper.Client.addSound(MOD_ID, "foxhurt2.wav");
	}
}
