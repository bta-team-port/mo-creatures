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

		// Bunnies
		SoundHelper.Client.addSound(MOD_ID, "rabbitdeath.ogg");
		SoundHelper.Client.addSound(MOD_ID, "rabbithurt1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "rabbithurt2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "rabbitland.ogg");
		SoundHelper.Client.addSound(MOD_ID, "rabbitlift.ogg");

		// Birds (oh boy)
		SoundHelper.Client.addSound(MOD_ID, "birdblack1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdblack2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdblack3.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdblue1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdblue2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdblue3.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdblue4.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdgreen1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdgreen2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdgreen3.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdred1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdred2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdred3.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdred4.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdred5.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdwhite1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdyellow1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdyellow2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdyellow3.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdyellow4.ogg");
		SoundHelper.Client.addSound(MOD_ID, "birdyellow5.ogg");

		// Honse
		SoundHelper.Client.addSound(MOD_ID, "horsegrunt1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "horsegrunt2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "horsegrunt3.ogg");
		SoundHelper.Client.addSound(MOD_ID, "horsehurt1.ogg");
		SoundHelper.Client.addSound(MOD_ID, "horsehurt2.ogg");
		SoundHelper.Client.addSound(MOD_ID, "horsedying.ogg");
		SoundHelper.Client.addSound(MOD_ID, "horsemad.ogg");
	}
}
