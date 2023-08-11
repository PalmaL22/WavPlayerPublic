package package1;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//This main class simply creates an instance of the WavFrame, in order for it to run accordingly
public class Wav {
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new WavFrame();
	}
}
