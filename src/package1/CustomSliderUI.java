package package1;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

public class CustomSliderUI extends BasicSliderUI {
	
	//Sets the height of track and creates the defaultColor variable
    private int trackHeight = 10;
    private Color defaultColor = new Color(155,0,0);

    //Default constructor
    public CustomSliderUI(JSlider slider) {
        super(slider);
    }

    //This method paints the thumb of the slider and customizes it
    @Override
    public void paintThumb(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Rectangle thumbBounds = thumbRect;
        int thumbRadius = thumbBounds.width / 2;
        int thumbDiameter = thumbRadius * 2;

        //Calculate the x and y positions to center the thumb
        int thumbX = thumbBounds.x + thumbBounds.width / 2 - thumbRadius;
        int thumbY = thumbBounds.y + thumbBounds.height / 2 - thumbRadius;

        //Increase the size of the thumb
        int increasedThumbRadius = (int) (thumbRadius * 1.2);
        int increasedThumbDiameter = increasedThumbRadius * 2;
        int increasedThumbX = thumbX - (increasedThumbDiameter - thumbDiameter) / 2;
        int increasedThumbY = thumbY - (increasedThumbDiameter - thumbDiameter) / 2;

        //Add a border around the thumb
        g2d.setColor(Color.BLACK);  // Customize the color of the border
        g2d.setStroke(new BasicStroke(3));
        g2d.drawOval(increasedThumbX, increasedThumbY, increasedThumbDiameter, increasedThumbDiameter);

        g2d.setColor(defaultColor);
        g2d.fillOval(increasedThumbX, increasedThumbY, increasedThumbDiameter, increasedThumbDiameter);
    }

    
    //This method paints the track of the slider and customizes it
    @Override
    public void paintTrack(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        Rectangle trackBounds = trackRect;

        //Calculate the fill percentage of the slider
        double fillPercentage = (double) slider.getValue() / (slider.getMaximum() - slider.getMinimum());

        //Calculate the width of the filled portion
        int fillWidth = (int) (trackBounds.width * fillPercentage);

        //Calculate the rounded edges of the track
        int trackRadius = trackHeight / 2;
        Shape trackShape = new RoundRectangle2D.Double(trackBounds.x, trackBounds.y + (trackBounds.height - trackHeight) / 2,trackBounds.width, trackHeight, trackRadius, trackRadius); 

        //Paint the unfilled portion in gray
        g2d.setColor(Color.GRAY);
        g2d.fill(trackShape);

        //Paint the filled portion in defaultColor
        g2d.setColor(defaultColor);
        g2d.fillRoundRect(trackBounds.x, trackBounds.y + (trackBounds.height - trackHeight) / 2,fillWidth, trackHeight, trackRadius, trackRadius);

        g2d.dispose();
    }
}
