package mc.jeryn.dev.regen.bta;

import net.minecraft.client.sound.SoundRepository;
import net.minecraft.core.sound.SoundTypes;

import static mc.jeryn.dev.regen.bta.Regeneration.MOD_ID;


public class RegenerationSounds {

	public static void init(){
		SoundTypes.loadSoundsJson("regenerated");
		SoundRepository.registerNamespace(MOD_ID);
	}

}
