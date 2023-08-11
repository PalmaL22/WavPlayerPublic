package package1;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;

public class WavFrame extends JFrame {
	//This method is used to create the frame pop up
	WavFrame() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		this.setTitle("Wav Player");
		this.add(new WavPanel());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);	
	}
}
