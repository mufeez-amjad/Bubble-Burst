/* Mufeez Amjad
 * June 15th 2016
 * Bubble Burst
 * Ms Karasinska
 * Data Dictionary
 *    x is the randomly generated x location of a bubble
 *    diameter is the diameter of the bubble
 *    y is the y location of the bubble
 *    speed is the rate of movement of a bubble
 *    color is a random integer that represents the color of a bubble
 *    Blue1 is an image of a small blue bubble
 *    Blue2 is an image of a medium blue bubble
 *    Blue3 is an image of a large blue bubble
 *    Red1 is an image of a small red bubble
 *    Red2 is an image of a medium red bubble
 *    Red3 is an image of a large red bubble
 *    Green1 is an image of a small green bubble
 *    Green2 is an image of a medium green bubble
 *    Green3 is an image of a large green bubble
 *       
 * Class Description:
 *    This class deals with the properties of each bubble. It contains arguments and methods for updating the bubbles position, painting bubbles,
 *    generating bubbles ups. It also has accessor methods that are used in the "Game" class.
 */

//importing java packages

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Bubble{
	
	//initializing and declaring variables
	
	private int x;
	private int diameter;
	private int y = Game.frameWidth + diameter;
	public static double speed = 1;
	private int color = (int) (34 * Math.random() + 1); //simulates probability
	private int size;

	private static BufferedImage blue1 = null;
	private static BufferedImage blue2 = null;
	public static BufferedImage blue3 = null;
	private static BufferedImage red1 = null;
	private static BufferedImage red2 = null;
	public static BufferedImage red3 = null;
	private static BufferedImage green1 = null;
	private static BufferedImage green2 = null;
	public static BufferedImage green3 = null;

	public int RandomXLocation(){ //generates a random location on the x axis for the bubble
		return x = (int) ((Game.frameWidth-50) * Math.random() - 50) + 50;
	}

	private int RandomColour(){ //generates the random colour of the bubble
		color = (int) (34 * Math.random() + 1);
		return color;
	}

	public int RandomCircleSize(){ //generates the diameter of the bubble
		size = (int) (3 * Math.random() + 1);
		if (size == 1){
			return diameter = 50; //diameter of the bubble image
		}

		if (size == 2){
			return diameter = 75;
		}
		if (size == 3){
			return diameter = 100;
		}
		return diameter; //returns null if the conditions above are not true
	}

	public double Speed(){
		//if (speed <= 0) speed = 1;
		//return speed = (int) (3 * Math.random() + 1);
		return speed += 0.01; //increases the speed slightly as the game goes on

	}

	public void paint(Graphics g){ //the method that is included in the JPanel class, paints every frame

		if (blue1 == null){ //imports an image from the project files for small blue bubble
			try { //contains code that might throw an exception
				blue1 = ImageIO.read(getClass().getResource("/Bubble.png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (blue2 == null){ //imports an image from the project files for medium blue bubble
			try { //contains code that might throw an exception
				blue2 = ImageIO.read(getClass().getResource("/Bubble(2).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (blue3 == null){ //imports an image from the project files for large blue bubble
			try { //contains code that might throw an exception
				blue3 = ImageIO.read(getClass().getResource("/Bubble(3).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (red1 == null){ //imports an image from the project files for small red bubble
			try { //contains code that might throw an exception
				red1 = ImageIO.read(getClass().getResource("/Bubble(Red).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (red2 == null){ //imports an image from the project files for medium red bubble
			try { //contains code that might throw an exception
				red2 = ImageIO.read(getClass().getResource("/Bubble(Red2).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (red3 == null){ //imports an image from the project files for large red bubble
			try { //contains code that might throw an exception
				red3 = ImageIO.read(getClass().getResource("/Bubble(Red3).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (green1 == null){ //imports an image from the project files for small green bubble
			try { //contains code that might throw an exception
				green1 = ImageIO.read(getClass().getResource("/Bubble(Green).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (green2 == null){ //imports an image from the project files for medium green bubble
			try { //contains code that might throw an exception
				green2 = ImageIO.read(getClass().getResource("/Bubble(Green2).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (green3 == null){ //imports an image from the project files for large green bubble
			try { //contains code that might throw an exception
				green3 = ImageIO.read(getClass().getResource("/Bubble(Green3).png"));
			} catch (IOException e) { //handles exception if the file is not found
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (Game.powerUps){ //if the game has power ups
			if (color == 33){ //red bubble
				if (size == 1) g.drawImage(red1, x, y, null); //draws a red bubble
				if (size == 2) g.drawImage(red2, x, y, null);
				if (size == 3) g.drawImage(red3, x, y, null);
			}
			if (color == 34){ //green bubble
				if (size == 1) g.drawImage(green1, x, y, null); //draws a green bubble
				if (size == 2) g.drawImage(green2, x, y, null);
				if (size == 3) g.drawImage(green3, x, y, null);
			}
			else{ //blue bubble
				if (size == 1) g.drawImage(blue1, x, y, null); //draws a blue bubble
				if (size == 2) g.drawImage(blue2, x, y, null);
				if (size == 3) g.drawImage(blue3, x, y, null);
			}
		}
		else { //blue bubble
			if (size == 1) g.drawImage(blue1, x, y, null); //draws a blue bubble
			if (size == 2) g.drawImage(blue2, x, y, null);
			if (size == 3) g.drawImage(blue3, x, y, null);
		}

	}


	public Bubble(){ //constructor for each individual bubble
		RandomXLocation(); 
		RandomCircleSize(); 
		Speed(); 
		RandomColour();
	}

	public void update(){ //updates the position of the bubble

		if(y <= -diameter){ //if the bubble reaches the top, create a new bubble with different values at the bottom of the window
			RandomXLocation();
			Speed();
			RandomCircleSize();
			y = Game.frameHeight + diameter;
		}

		if (Game.freezeActive && color != 33){ //freezes the bubble
			speed = 0;
		}

		if(y > -diameter){ //moves the bubble to the top of the screen
			y -= speed;
		}
	}

	public int getX(){ //accessor method for x position of bubble
		return x;
	}
	public int getY(){
		return y;
	}

	public int getCircleSize(){
		return diameter;
	}

	public int getColor(){
		return color;
	}

	public static void setSpeed(double n){ //mutator method to set the speed for different difficulties
		speed = n;
	} 

}