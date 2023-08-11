package package1;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class WavPanel extends JLayeredPane implements ActionListener,ChangeListener{
	//Set width and height of panel
	final int SCREEN_WIDTH = 600;
	final int SCREEN_HEIGHT = 570;
	
	//Set width and height that will be used to resize Image icons
	final int RESIZE_WIDTH = 35;
	final int RESIZE_HEIGHT = 35;
	
	//Default border 
	Border defaultBorder = BorderFactory.createLineBorder(Color.black, 3);
	
	//Dark Red and Beige color
	Color darkRed = new Color(155, 0, 0);
	Color beige = new Color(213,189,184);
	
	//Create JButtons
	JButton playButton;
	JButton stopButton;
	JButton restartButton;
	
	//Label creation
	JLabel clouds;
	JLabel buttonLabel;
	JLabel sliderLabel;
	JLabel volumeSymbol;
	
	//Create song Clip 
	Clip songChoice;
	
	//Create volume slider
	JSlider volumeSlider;
	
	//Create instance variable for volume control
	FloatControl volumeLevel;
	
	//Setting up drop down menu for music
	JComboBox musicMenu; 
	
	WavPanel() throws UnsupportedAudioFileException, IOException, LineUnavailableException{
		//Create original Image icons
		ImageIcon background = new ImageIcon(getClass().getResource("/resources/background.gif"));
		ImageIcon play = new ImageIcon(getClass().getResource("/resources/play.png"));
		ImageIcon restart = new ImageIcon(getClass().getResource("/resources/restart.png"));
		ImageIcon stop = new ImageIcon(getClass().getResource("/resources/stop.png"));
		ImageIcon volume = new ImageIcon(getClass().getResource("/resources/volume.png"));
		
		//Resizing image icons
		background = gifResize(background, SCREEN_WIDTH, SCREEN_HEIGHT);
		play = imageResize(play, RESIZE_WIDTH, RESIZE_HEIGHT);
		stop = imageResize(stop, RESIZE_WIDTH, RESIZE_HEIGHT);
		restart = imageResize(restart, RESIZE_WIDTH, RESIZE_HEIGHT);
		volume = imageResize(volume, RESIZE_WIDTH, RESIZE_HEIGHT);
	
		//Customizing clouds label and adding the resized "background" icon to the label
		clouds = new JLabel();
		clouds.setIcon(background);
		clouds.setOpaque(true);
		clouds.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		
		//Setting the text and its customizations to the clouds label
		clouds.setText("Wav Player - By Luis Palma");
		clouds.setForeground(Color.red);
		clouds.setFont(new Font("Comic Sans", Font.ITALIC,20));
		clouds.setHorizontalTextPosition(JLabel.CENTER);
		clouds.setVerticalTextPosition(JLabel.TOP);
		clouds.setIconTextGap(-45);
		
		//Customizing all Buttons
		playButton = customizeButtons(playButton, play);
		restartButton = customizeButtons(restartButton, restart);
		stopButton = customizeButtons(stopButton, stop);
		
		//Customizing the button label
		buttonLabel = new JLabel();
		buttonLabel.setBounds(JLabel.CENTER, 250, SCREEN_WIDTH, SCREEN_HEIGHT);
		buttonLabel.setLayout(new FlowLayout(JLabel.CENTER,70,JLabel.CENTER));
		
		//Adding all buttons to the buttonLabel
		buttonLabel.add(playButton);
		buttonLabel.add(restartButton);
		buttonLabel.add(stopButton);
		
		//Customizing volume slider
		volumeSlider = new JSlider(0,100,50);
		volumeSlider.setOpaque(false);
		volumeSlider.setFocusable(false);
		volumeSlider.setPreferredSize(new Dimension(270,50));
		volumeSlider.addChangeListener(this);
		
		//This calls the CustomSliderUI class that changes the appearance of the slider
		volumeSlider.setUI(new CustomSliderUI(volumeSlider));
		
		//Customizing the volume Symbol label
		volumeSymbol = new JLabel(volume);
		
		//Customizing Slider label
		sliderLabel = new JLabel();
		sliderLabel.setBounds(70, 320, SCREEN_WIDTH, SCREEN_HEIGHT);
		sliderLabel.setLayout(new FlowLayout(JLabel.CENTER,5,JLabel.CENTER));
		
		//Establishing array that contain song choices
		String[] musicChoices = {"Bystanders","Aston Martin Music","Transportin'","7 Years", "Higher", "Stay With Me"};
		
		//Customizing and defining the musicMenu combo box
		musicMenu = new JComboBox(musicChoices);
		musicMenu.setFont(new Font("Verdana", Font.BOLD,11));
		musicMenu.setForeground(beige);
		musicMenu.setBackground(darkRed);
		musicMenu.setBorder(defaultBorder);
		musicMenu.addActionListener(this);
				
		//Adding volumeSymbol, volumeSlider, and musicMenu to sliderLabel 
		sliderLabel.add(volumeSymbol);
		sliderLabel.add(volumeSlider);
		sliderLabel.add(musicMenu);
		
		//Opening an AudioInputStream and setting default primary song as Bystanders
		songChoice = openStream("/resources/Bystanders.wav");
		
		//Setting up the layeredPane
		this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
		this.setFocusable(true);
		this.add(clouds, Integer.valueOf(0));
		this.add(buttonLabel, Integer.valueOf(1));
		this.add(sliderLabel, Integer.valueOf(1));
	}
	
	//This method was specifically made to resize GIF files, since they are not able to Scale_Smooth
	private ImageIcon gifResize(ImageIcon input, int width, int height) {
		Image image = input.getImage();
		Image imageRescale = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
		ImageIcon finalImage = new ImageIcon(imageRescale);
		return finalImage;
	}
	
	//This method resizes ImageIcons, except GIF images
	private ImageIcon imageResize(ImageIcon input, int width, int height) {
		Image image = input.getImage();
		Image imageRescale = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		ImageIcon finalImage = new ImageIcon(imageRescale);
		return finalImage;
	}
	
	//This method customizes all the buttons and gives them an action listener
	private JButton customizeButtons(JButton button, ImageIcon image) {
		button = new JButton(image);
		button.setBackground(darkRed);
		button.setBorder(defaultBorder);
		//button.setFocusable(false);
		button.setPreferredSize(new Dimension(110,45));
		button.addActionListener(this);
		return button;
	}

	//This method opens an audio input stream for a specific music file and returns the corresponding Clip
	private Clip openStream(String filePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		URL path = getClass().getResource(filePath);
		AudioInputStream songStream = AudioSystem.getAudioInputStream(path);
		Clip songClip = AudioSystem.getClip();
		songClip.open(songStream);
		songClip.setFramePosition(0);
		return songClip;
	}
	
	//This ActionListener handles the actions of the buttons and the drop down menu upon pressing them
	@Override
	public void actionPerformed(ActionEvent e) {
		//This section of the else-if statement handles the function of the buttons
		if(e.getSource()==playButton) {
			songChoice.start();
			
		} else if(e.getSource()==stopButton) {
			songChoice.stop();
			
		} else if (e.getSource() == restartButton) {
			songChoice.setFramePosition(0);
		
		//This section of the else-if statement handles the function of the drop down menu
		} else if(e.getSource()==musicMenu) {
			int songSelect = musicMenu.getSelectedIndex();
			
			switch(songSelect) {
			case 0:
				songChoice.close();
				try {
					songChoice = openStream("/resources/Bystanders.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				songChoice.start();
				break;
			case 1:
				songChoice.close();
				try {
					songChoice = openStream("/resources/AstonMartinMusic.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				songChoice.start();
				break;
			case 2:
				songChoice.close();
				try {
					songChoice = openStream("/resources/Transportin.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				songChoice.start();
				break;
			case 3:
				songChoice.close();
				try {
					songChoice = openStream("/resources/7Years.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				songChoice.start();
				break;
			case 4:
				songChoice.close();
				try {
					songChoice = openStream("/resources/Higher.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				songChoice.start();
				break;
			case 5:
				songChoice.close();
				try {
					songChoice = openStream("/resources/StayWithMe.wav");
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				songChoice.start();
				break;
			}
		}
	}
	
	//This method uses the volumeLevel FloatControl to manipulate the volume of the audio clip through the slider
	@Override
	public void stateChanged(ChangeEvent e) {
		
		//Defining volume Control
		volumeLevel = (FloatControl) songChoice.getControl(FloatControl.Type.MASTER_GAIN); 
		
		//Get the values for the equation
		int sliderValue = volumeSlider.getValue();
	    float volumeRange = volumeLevel.getMaximum() - volumeLevel.getMinimum();
	    
	    //Using linear mapping equation to make sure that when it is at 100, the volume will be set to its max volume
	    float volumeValue = volumeLevel.getMinimum() + (volumeRange * sliderValue / 100.0f);

	    //Set the volume using the volumeLevel control
	    volumeLevel.setValue(volumeValue);
	}
}

