
package Tools;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class MusicPlay {
	AudioInputStream audioInputStream;
	File in;
	Clip clip;
	boolean isplay=true;;
	public MusicPlay(String musicurl) {

		try {
			in = new File(musicurl);
			audioInputStream = AudioSystem.getAudioInputStream((in).getAbsoluteFile());
			clip = AudioSystem.getClip();
			clip.open(audioInputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void MusicStop() {
		clip.close();
	}

}
