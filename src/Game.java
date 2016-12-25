/* Mufeez Amjad
 * June 15th 2016
 * Bubble Burst
 * Ms Karasinska
 * Data Dictionary
 *    frameWidth is the width of the JFrame
 *    frameHeight is the height of the JFrame
 *    paused is if the game is paused
 *    score is the user's score
 *    lives is the number of lives the user has
 *    highScore is the highest score from the text file
 *    bubbles is an array list containing all instances of bubbles
 *    playGame is if the game is being played
 *    gameOver is if the user has lost
 *    numBubbles is the number of bubbles in the game
 *    powerUps is if the game has power ups enabled
 *    powerUpActive is if any power up is active
 *    slowMoActive is if the bubbles have slowed down
 *    freezeActive is if the bubbles have stopped moving
 *    superPopActive is if all the bubbles have been popped using the Super Pop power up
 *    lifeUpActive is if the user has gained a life
 *    SlowMo is the icon for the slow-mo powerup
 *    Freeze is the icon for the freeze powerup
 *    SuperPop is the icon for the super pop powerup
 *    LifeUp is the icon for the life up powerup
 *    Again is the button for play again
 *    Info is the button for the information screen
 *    Play is the button for play
 *    Menu is the button for the main menu
 *    Back is the button for going back in the menu
 *    image is the logo for the game
 *    bg is the background for the game
 *    powerUp is the randomly generated power up
 *    font is the raw file of the "BubbleGum" font
 *    fontBig is a large font derived from font
 *    fontMedium is a medium-sized font derived from font
 *    fontSmall is a small font derived from font
 *    Instructions is if the current screen is the instructions screen
 *    showBlue is if the Blue Bubble is selected on the instructions screen
 *    showRed is if the Red Bubble is selected on the instructions screen
 *    showGreen is if the Green Bubble is selected on the instructions screen
 *    BlueGlow is a blue bubble with an outer glow
 *    RedGlow is a red bubble with an outer glow
 *    GreenGlow is a green bubble with an outer glow
 *    DifficultySelect is if the current screen is the difficulty select screen
 *    superPopStart is the time when the super pop power up was activated
 *    slowMoStart is the time when the slow-mo power up was activated
 *    freezeStart is the time when the freeze power up was activated
 *    lifeUpStart is the time when the user gained a life
 *    savedScore is to check if the score has been saved
 *    
 * Class Description:
 *    This class deals with the game itself. Basically, it is the framework for the game. It contains arguments and methods for power ups,
 *    the various menu screens, the animation, the window, mouse input,  the music, and handling of the bubble objects.
 */

//importing java packages to use later in the class

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ConcurrentModificationException; 
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Game extends JPanel{ //inherits properties of a JPanel

	//initializing and declaring variables

	public static final int frameWidth = 800; //cannot be changed
	public static final int frameHeight = 533; //cannot be changed

	private static boolean paused = true; 
	private static JLabel statusbar;
	private static int score = 0;
	public static int lives = 10;
	private static int levelLives;
	private static int highScore = 0;
	public static List<Bubble> bubbles = new ArrayList<Bubble>(); //creates an Array List of Bubbles
	private static boolean playGame = false;
	private static boolean gameOver = false;

	private static int numBubbles = 10;
	public static boolean powerUps = false;
	public static boolean powerUpActive = false;
	public static boolean slowMoActive = false;
	public static boolean freezeActive = false;
	public static boolean superPopActive = false;
	private static boolean lifeUpActive = false;
	private static BufferedImage SlowMo = null; 
	private static BufferedImage Freeze = null;
	private static BufferedImage SuperPop = null;
	private static BufferedImage LifeUp = null;

	private static BufferedImage Again = null; 
	private static BufferedImage Info = null;
	private static BufferedImage Play = null;
	private static BufferedImage Menu = null;
	private static BufferedImage Back = null;

	private static BufferedImage image = null; 
	private static BufferedImage bg = null;
	public static int powerUp;
	public static Font font;
	public static Font fontBig;
	public static Font fontMedium;
	public static Font fontSmall;
	private static boolean menu = true;
	private static boolean Instructions = false;
	private static boolean showBlue = false;
	private static boolean showRed = false;
	private static boolean showGreen = false;
	private static BufferedImage BlueGlow = null; 
	private static BufferedImage RedGlow = null;
	private static BufferedImage GreenGlow = null;
	private static boolean DifficultySelect = false;
	
	private static double superPopStart;
	private static double slowMoStart;
	public static double freezeStart;
	private static double lifeUpStart;
	private static double oldSpeed;
	private static boolean savedScore = false;
	private boolean freezeDone = false;
	private static int level;

	public Game() { //constructor for game
		for (int i = 0; i < numBubbles ; i++){ 
			bubbles.add(new Bubble()); //initializes Bubbles to the Bubbles Array List
		}
	}

	public void paint(Graphics graphics) { //paints every frame

		if (font == null){ //if the font has not been set,  imports font from project files
			try { //contains code that might throw an exception
				font = Font.createFont(Font.TRUETYPE_FONT, new File("src/BubbleGum.ttf")); 
				fontBig = font.deriveFont(Font.PLAIN, 90);
				fontMedium = font.deriveFont(Font.PLAIN, 25);
				fontSmall = font.deriveFont(Font.PLAIN, 18);
			} catch (IOException|FontFormatException e) { //handles exception if the file is not found or the font is invalid
				System.out.println("BubbleGum.ttf could not be found");
			}
		}

		if (bg == null){ //imports an image from the project files for background
			try {  //contains code that might throw an exception
				bg = ImageIO.read(this.getClass().getResource("underwater2.png"));
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("underwater2.png could not be found!");
			} 

		}

		if (Again == null){ //imports an image from the project files for play again button
			try { //contains code that might throw an exception
				Again = ImageIO.read(this.getClass().getResource("Again.png")); 
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("Again.png could not be found!");
			} 

		} 

		if (Info == null){ //imports an image from the project files for information button
			try { //contains code that might throw an exception
				Info = ImageIO.read(this.getClass().getResource("Info.png")); 
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("Info.png could not be found!");
			} 

		} 

		if (Play == null){ //imports an image from the project files for play button
			try { //contains code that might throw an exception
				Play = ImageIO.read(this.getClass().getResource("Play.png")); 
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("Play.png could not be found!");
			} 

		} 

		if (Menu == null){ //imports an image from the project files for home button
			try { //contains code that might throw an exception
				Menu = ImageIO.read(this.getClass().getResource("Menu.png")); 
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("Menu.png could not be found!");
			} 

		} 

		if (Back == null){ //imports an image from the project files for back button
			try { //contains code that might throw an exception
				Back = ImageIO.read(this.getClass().getResource("Back.png")); 
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("Back.png could not be found!");
			} 

		} 

		if (SlowMo == null){ //imports an image from the project files for slow-mo indicator
			try { //contains code that might throw an exception
				SlowMo = ImageIO.read(this.getClass().getResource("SlowMo.png")); 
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("SlowMo.png could not be found!");
			} 
		}

		if (Freeze == null){ //imports an image from the project files for freeze indicator
			try { //contains code that might throw an exception
				Freeze = ImageIO.read(this.getClass().getResource("Freeze.png"));
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("Freeze.png could not be found!");
			} 
		}

		if (SuperPop == null){ //imports an image from the project files for super pop indicator
			try { //contains code that might throw an exception
				SuperPop = ImageIO.read(this.getClass().getResource("SuperPop.png"));
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("SuperPop.png could not be found!");
			} 
		}

		if (LifeUp == null){ //imports an image from the project files for 1 up indicator
			try { //contains code that might throw an exception
				LifeUp = ImageIO.read(this.getClass().getResource("1Up.png")); 
			} catch (IOException e) { //handles exception if the file is not found
				System.out.println("1Up.png could not be found!");
			} 
		}
		int x = (frameWidth - bg.getWidth(null)) / 2; //center of screen
		int y = (frameHeight - bg.getHeight(null)) / 2;

		graphics.drawImage(bg, x, y, null); //draws the background in the center of the screen first
		try { //contains code that might throw an exception
			for (Bubble b : bubbles) b.paint(graphics); //paints all of the bubbles in the Bubbles Array List //enhanced for loop
		} catch (ConcurrentModificationException e){ //handles exception if the array list cannot be modified
			e.printStackTrace(); //prints the exception
		}

		if (playGame && !gameOver){ //if the game is running, show the score and lives
			graphics.setFont(fontSmall); //sets the current font to the imported font
			graphics.setColor(Color.WHITE); //sets the color to white
			graphics.drawString("Lives: " + lives, 10, 20); //paints the lives
			graphics.drawString("Score: " + score, frameWidth - 105, 20); //paints the score
		}

		if (gameOver && playGame){ //shows the Game Over screen
			graphics.setColor(Color.WHITE); //sets the color to white
			graphics.setFont(fontBig); //sets the font
			FontMetrics fontMetrics = graphics.getFontMetrics(fontBig); //used to calculate based on the measurements of the font 
			int stringLength = fontMetrics.stringWidth("GAME OVER"); //horizontal length of "Game Over"
			int stringHeight = fontMetrics.getHeight(); //vertical length of "Game Over"
			graphics.drawString("GAME OVER", (frameWidth/2) - (stringLength/2)  , (frameHeight/2) - (stringHeight/2)); //draws it in the exact center of the screen

			graphics.setFont(fontMedium);  //sets the font
			fontMetrics = graphics.getFontMetrics(fontMedium); //used to calculate based on the measurements of the font
			stringLength = fontMetrics.stringWidth("Your Score: " + score);   //horizontal length
			stringHeight = fontMetrics.getHeight();//vertical length
			graphics.drawString("Your Score: " + score , (frameWidth/2) - (stringLength/2) , (frameHeight/2) + 15); //draws a string

			graphics.setFont(fontSmall);  //sets the font
			fontMetrics = graphics.getFontMetrics(fontSmall);  //used to calculate based on the measurements of the font
			stringLength = fontMetrics.stringWidth("High Score: " + highScore(level)); //horizontal length
			graphics.drawString(String.format(score >  highScore(level) ? "High Score: " + score : "High Score: " + highScore(level)), (frameWidth/2) - (stringLength/2) , (frameHeight/2) + 40); //draws the score if it is the high score, if not, prints the highest score from the text file
			graphics.drawImage(Again, 250 ,frameHeight - 175, null); //draws an image
			graphics.drawImage(Menu, 450 ,frameHeight - 175, null);  //draws an image
		}

		if (!gameOver){
			if (powerUps){ //paints power up indicator if they are active
				x = (frameWidth - SuperPop.getWidth(null)) / 2;
				if (superPopActive){
					long currentTime = System.nanoTime(); //takes the current time in nano seconds
					double seconds = (double) currentTime / 1000000000.0; //and converts it to seconds

					if (superPopStart + 2 >= seconds){ //it paints the indicator for 2 seconds
						graphics.drawImage(SuperPop, x - 50, 5, null); //draws an image
					}
					else {
						superPopActive = false; //sets the indicator to false when 2 seconds are over
					}
				}

				if (slowMoActive){
					long currentTime = System.nanoTime(); //current time in nanoseconds
					double seconds = (double) currentTime / 1000000000.0;

					if (slowMoStart + 4 >= seconds){ //indicator lasts for 4 seconds
						graphics.drawImage(SlowMo, x - 100, 5, null); //draws an image
					}

					else {
						slowMoActive = false;
					}
				}

				if (freezeActive){
					long currentTime = System.nanoTime(); //current time in nanoseconds
					double seconds = (double) currentTime / 1000000000.0;
					oldSpeed = Bubble.speed;
					if (freezeStart + 4 >= seconds){ //indicator lasts for 4 seconds
						graphics.drawImage(Freeze, x + 50, 5, null); //draws an image
					}

					else {
						freezeActive = false;
						freezeDone = true;
					}
				}
			}

			if (lifeUpActive && !gameOver){ //if the user gets another life
				long currentTime = System.nanoTime();//current time in nanoseconds
				double seconds = (double) currentTime / 1000000000.0;

				if (lifeUpStart + 4 >= seconds){ //indicator lasts for 4 seconds
					graphics.drawImage(LifeUp, x + 100, 5, null); //draws an image
				}

				else {
					lifeUpActive = false;
				}

			}
		}

		if (menu && !Instructions && !DifficultySelect){ //the menu screen
			if (image == null){
				try { //contains code that might throw an exception
					image = ImageIO.read(this.getClass().getResource("Logo.png"));
				} catch (IOException e) { //handles exception if the file is not found
					System.out.println("Logo.png could not be found!");
				} 
			}
			x = (frameWidth - image.getWidth(null)) / 2; //gets the dimensions of the logo
			y = (frameHeight - image.getHeight(null)) / 2 - 100;
			graphics.drawImage(image, x, y, null); //draws an image
			graphics.setColor(Color.WHITE); //sets the color to white
			graphics.drawImage(Play, 450 ,frameHeight - 175, null); //draws an image
			graphics.drawImage(Info, 250 ,frameHeight - 175, null); //draws an image
		}

		if (Instructions){ //instructions screen
			if (GreenGlow == null){
				try { //contains code that might throw an exception
					GreenGlow = ImageIO.read(this.getClass().getResource("Bubbles(GreenGlow).png")); //imports an image from the project files
				} catch (IOException e) { //handles exception if the file is not found
					System.out.println("Bubbles(GreenGlow).png could not be found!");
				} 
			}
			if (BlueGlow == null){
				try { //contains code that might throw an exception
					BlueGlow = ImageIO.read(this.getClass().getResource("Bubble(Glow).png")); //imports an image from the project files
				} catch (IOException e) { //handles exception if the file is not found
					System.out.println("Bubble(Glow).png could not be found!");
				} 
			}
			if (RedGlow == null){
				try { //contains code that might throw an exception
					RedGlow = ImageIO.read(this.getClass().getResource("Bubbles(RedGlow).png")); //imports an image from the project files
				} catch (IOException e) { //handles exception if the file is not found
					System.out.println("Bubbles(RedGlow).png could not be found!");
				} 
			}

			graphics.setColor(Color.WHITE); //sets the color to white
			x = (frameWidth - Bubble.blue3.getWidth(null)) / 2; //dimensions of a large bubble
			y = (frameHeight - Bubble.blue3.getHeight(null)) / 2 - 50;
			if (!showBlue) graphics.drawImage(Bubble.blue3, x, y, null); //if the blue bubble is not selected show the regular bubble
			if (!showRed) graphics.drawImage(Bubble.red3, x-250, y, null); //draws an image
			if (!showGreen) graphics.drawImage(Bubble.green3, x+250, y, null); //draws an image
			graphics.setFont(fontBig);  //sets the font
			FontMetrics fontMetrics = graphics.getFontMetrics(fontBig);//used to calculate based on the measurements of the font
			int stringLength = fontMetrics.stringWidth("HOW TO PLAY");  //horizontal length
			graphics.drawString("HOW TO PLAY", (frameWidth/2) - (stringLength/2), 80); //draws a string
			graphics.setFont(fontMedium);  //sets the font
			fontMetrics = graphics.getFontMetrics(fontMedium);//used to calculate based on the measurements of the font
			stringLength = fontMetrics.stringWidth("Pop the Bubbles by Mousing-Over Them"); //horizontal length
			graphics.drawString("Pop the Bubbles by Mousing-Over Them", (frameWidth/2) - (stringLength/2), 120); //draws a string

			graphics.setFont(fontSmall);  //sets the font
			fontMetrics = graphics.getFontMetrics(fontSmall); //used to calculate based on the measurements of the font

			stringLength = fontMetrics.stringWidth("Click each bubble to learn more");  //horizontal length
			graphics.drawString("Click each bubble to learn more", (frameWidth/2) - (stringLength/2), 150); //draws a string
			graphics.drawImage(Back, 10, frameHeight - 120, null); //draws an image
			if (showBlue){ //if the blue bubble is clicked
				graphics.drawImage(BlueGlow, x, y, null); //paint the bubble with a glow to indicate its selection
				graphics.setFont(fontSmall);  //sets the font
				fontMetrics = graphics.getFontMetrics(fontSmall); //used to calculate based on the measurements of the font
				stringLength = fontMetrics.stringWidth("Pop these before they reach the top of the screen.");  //horizontal length
				graphics.drawString("Pop these before they reach the top of the screen.", (frameWidth/2) - (stringLength/2), frameHeight - 225); //draws a string
				stringLength = fontMetrics.stringWidth("You will lose a life for every bubble that floats away.");  //horizontal length
				graphics.drawString("You will lose a life for every bubble that floats away.", (frameWidth/2) - (stringLength/2), frameHeight - 185); //draws a string
				stringLength = fontMetrics.stringWidth("Pop 25, get one life."); //horizontal length
				graphics.drawString("Pop 25, get one life.", (frameWidth/2) - (stringLength/2), frameHeight - 145); //draws a string
			}
			if (showRed){
				graphics.drawImage(RedGlow, x-250, y, null); //draws an image
				graphics.setFont(fontSmall);  //sets the font
				fontMetrics = graphics.getFontMetrics(fontSmall); //used to calculate based on the measurements of the font
				stringLength = fontMetrics.stringWidth("These are danger bubbles."); //horizontal length
				graphics.drawString("These are danger bubbles.", (frameWidth/2) - (stringLength/2), frameHeight - 225); //draws a string
				stringLength = fontMetrics.stringWidth("If you hit them, it's Game Over."); //horizontal length
				graphics.drawString("If you hit them, it's Game Over.", (frameWidth/2) - (stringLength/2), frameHeight - 185); //draws a string
			}
			if (showGreen){
				graphics.drawImage(GreenGlow, x+250, y, null); //draws an image
				graphics.setFont(fontSmall);  //sets the font
				fontMetrics = graphics.getFontMetrics(fontSmall); //used to calculate based on the measurements of the font
				stringLength = fontMetrics.stringWidth("These bubbles give you a random power up."); //horizontal length
				graphics.drawString("These bubbles give you a random power up.", (frameWidth/2) - (stringLength/2), frameHeight - 225); //draws a string
				graphics.setFont(fontMedium);  //sets the font
				fontMetrics = graphics.getFontMetrics(fontMedium); //used to calculate based on the measurements of the font
				stringLength = fontMetrics.stringWidth("The Power Ups"); //horizontal length
				graphics.drawString("The Power Ups", (frameWidth/2) - (stringLength/2), frameHeight - 175); //draws a string
				graphics.drawImage(Freeze, 150 , frameHeight - 150, null); //draws an image
				graphics.drawString("Freeze", 125, frameHeight - 95); //draws a string
				graphics.drawImage(SlowMo, 300 , frameHeight - 150, null); //draws an image
				graphics.drawString("Slow-Mo", 260, frameHeight - 95); //draws a string
				graphics.drawImage(LifeUp, 450 , frameHeight - 150, null); //draws an image
				graphics.drawString("1-Up", 435, frameHeight - 95); //draws a string
				graphics.drawImage(SuperPop, 600 , frameHeight - 150, null); //draws an image
				graphics.drawString("Super Pop", 555, frameHeight - 95); //draws a string
			}

		}

		if (DifficultySelect){ //difficulty select screen
			graphics.drawImage(Back, 10, frameHeight - 120, null); //draws an image
			graphics.setColor(Color.WHITE); //sets the color to white
			graphics.setFont(fontBig);  //sets the font
			FontMetrics fontMetrics = graphics.getFontMetrics(fontBig); //used to calculate based on the measurements of the font
			int stringLength = fontMetrics.stringWidth("Difficulty"); //horizontal length
			graphics.drawString("Difficulty", (frameWidth/2) - (stringLength/2), 80); //draws a string
			graphics.setFont(fontMedium);  //sets the font
			fontMetrics = graphics.getFontMetrics(fontMedium); //used to calculate based on the measurements of the font
			stringLength = fontMetrics.stringWidth("Easy"); //horizontal length
			graphics.drawString("Easy", (frameWidth/2) - (stringLength/2), (frameHeight/2) - 60); //draws a string
			stringLength = fontMetrics.stringWidth("Normal"); //horizontal length
			graphics.drawString("Normal", (frameWidth/2) - (stringLength/2), (frameHeight/2)); //draws a string
			stringLength = fontMetrics.stringWidth("Hard"); //horizontal length
			graphics.drawString("Hard", (frameWidth/2) - (stringLength/2), (frameHeight/2) + 60); //draws a string
			stringLength = fontMetrics.stringWidth("Insane"); //horizontal length
			graphics.drawString("Insane", (frameWidth/2) - (stringLength/2), (frameHeight/2) + 120); //draws a string
		}    
	}

	public void update() { //updates the properties of the bubbles and the game    

		if (!paused || menu || gameOver || DifficultySelect || Instructions){
			for (Bubble b : bubbles) b.update(); //updates the position of the bubbles //enhanced for loop
		}

		if (bubbles.size() < numBubbles){ //adds more bubbles to the game if the number of bubbles is less than required
			for (int i = 0; i < numBubbles - bubbles.size() ; i++){
				bubbles.add(new Bubble()); //adds new instances of bubbles to the Bubbles array list
			}
		}

		if (freezeDone){
			for (Bubble b : bubbles){ //enhanced for loop
				b.setSpeed(1); //sets the speed of the bubbles back to 1
			}
			freezeDone = false;
		}
		for (Iterator<Bubble> it = bubbles.iterator(); it.hasNext(); ) { //used to cycle through the array list of bubbles
			Bubble b = it.next(); //ConcurrentModificationException // it.next is the next instance in the array list
			int x = b.getX(); //gets the x position of the bubble
			int y = b.getY(); //gets the y position
			int r = b.getCircleSize(); //gets the diameter
			int color = b.getColor(); //gets the color
			if (playGame){
				if (y <= -r && x <= frameWidth && x >= 0){ // if the bubble leaves the screen from the top
					if (color != 33 && color != 34){ //if the bubble is blue
						lives -= 1; //take of 1 life
						it.remove(); //and remove the bubble from the array list
					}
					if (lives <= 0){ //if 0 lives are left
						gameOver = true; //the game is over
					}
				}
			}
		}
	}

	public static synchronized void playMusic() { //plays the music
		new Thread(new Runnable() { //creates a new thread
			public void run() {
				try { //contains code that might throw an exception
					Clip clip = AudioSystem.getClip(); 
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							this.getClass().getResource("BG Music.wav"));
					clip.open(inputStream); //imports the audio file
					clip.loop(Clip.LOOP_CONTINUOUSLY); //loops it forever
				} catch (Exception e) { //handles exception if the file is not found
					e.printStackTrace(); //prints the exception
				}
			}
		}).start();
	}

	public static synchronized void playPop() { //plays the pop sound effect
		new Thread(new Runnable() { //creates a new thread
			public void run() {
				try { //contains code that might throw an exception
					Clip clip = AudioSystem.getClip();
					AudioInputStream inputStream = AudioSystem.getAudioInputStream(
							this.getClass().getResource("Pop.wav")); //imports the audio file
					clip.open(inputStream);
					clip.start(); //plays the sound
				} catch (Exception e) { //handles exception if the file is not found
					e.printStackTrace(); //prints the exception
				}
			}
		}).start();
	}

	public static void saveScore(int level) { //saves the score to the text file
		String filePath = null; //file path to the text file
		if (level == 1) filePath = "src/Easy.txt";
		if (level == 2) filePath = "src/Normal.txt";
		if (level == 3) filePath = "src/Hard.txt";
		if (level == 4) filePath = "src/Insane.txt";
		try { //contains code that might throw an exception
			FileWriter fw = new FileWriter(filePath, true); //creates or opens a text file
			PrintWriter pw = new PrintWriter(fw); //PrintWriter takes the file was a parameter
			if (score > 0) pw.write(score +"\r\n"); //prints the score in the text file
			pw.close(); //stops the printing
			fw.close(); //closes the file
			savedScore  = true;
		}
		catch(IOException e) { //handles exception if the file is not found
			System.err.println("Writing - HighScores.txt could not be found!");
		}
	}

	public static int highScore(int level) { //calculates the highest score from the text file
		highScore = 0;
		String filePath = null; //file path to the text file
		if (level == 1) filePath = "src/Easy.txt";
		if (level == 2) filePath = "src/Normal.txt";
		if (level == 3) filePath = "src/Hard.txt";
		if (level == 4) filePath = "src/Insane.txt";
		Scanner scanner;
		try { //contains code that might throw an exception
			scanner = new Scanner(new File(filePath)); //opens the text file
			List<Integer> scores = new ArrayList<Integer>(); //creates and array list of scores
			while(scanner.hasNextInt())//while there is another score
			{
				scores.add(scanner.nextInt()); //add the score to the array list
			}

			for (int s : scores){ //enhanced for loop
				if (s > highScore){ //if s is the highest score so far, set it to high score
					highScore = s;
				}
			}
			scanner.close(); //closes the file
			scores.clear(); //clears the array list
		}
		catch (FileNotFoundException e) { //handles exception if the file is not found
			// TODO Auto-generated catch block
			e.printStackTrace(); //prints the exception
		}

		return highScore;
	}

	public static void main(String[] args) throws InterruptedException { //"InterruptedException" is thrown for Thread.Sleep(10)

		JFrame frame = new JFrame(); //creates an instance of a JFrame
		Game game = new Game(); //creates an instance of a game


		game.addMouseMotionListener(new MouseAdapter() { //adds mouse movement input to the game

			@Override
			public void mouseMoved(MouseEvent e) { //checks if the user moved the mouse
				for (Iterator<Bubble> it = bubbles.iterator(); it.hasNext(); ) {
					Bubble b = it.next(); //ConcurrentModificationException
					int x = b.getX(); //accessor method
					int y = b.getY();
					int r = b.getCircleSize();
					int color = b.getColor();
					if (playGame && !gameOver){ //if the game is being played
						if ( (e.getX() - 10 >= (x - r/2) && e.getX() -10 <= (x+ r/2)) && (e.getY() - 15 >= (y - r/2)  && e.getY() -15 <= (y+ r/2)) ){ //if the mouse is inside the bubble
							if(it.hasNext()) {  
								it.remove(); //pop the bubble
							}  

							if (color == 33 && powerUps) gameOver = true; //if the bubble is red, game over
							if (color == 34 && powerUps) { //if the bubble is blue, power up active
								powerUpActive = true;
								powerUp = (int) (4 * Math.random() +1); //randomizes the power up
								if (powerUp == 1){ //SuperPop
									score += bubbles.size(); //points for every bubble popped
									bubbles.clear(); //pops all of the bubbles

									superPopActive = true;
									long startTime = System.nanoTime(); //current time in nanoseconds
									double seconds = (double) startTime / 1000000000.0;
									superPopStart = seconds; //time that the power up started
								}
								if (powerUp == 2){ //Slow-Mo
									for (Bubble b1 : bubbles){ //enhanced for loop
										b1.setSpeed(1); //sets the speed of all the bubbles to 1
									}
									slowMoActive = true;
									long startTime = System.nanoTime(); //current time in nanoseconds
									double seconds = (double) startTime / 1000000000.0;
									slowMoStart = seconds;

								}
								if (powerUp == 3){ // 1-Up
									lives++;
									lifeUpActive = true;
									long startTime = System.nanoTime(); //current time in nanoseconds
									double seconds = (double) startTime / 1000000000.0;
									lifeUpStart = seconds;
								}

								if (powerUp == 4){ //Freeze
									freezeActive = true;
									long startTime = System.nanoTime(); //current time in nanoseconds
									double seconds = (double) startTime / 1000000000.0;
									freezeStart = seconds;
								}
							} 
							else score++; //1 point for a blue bubble
							playPop(); // plays the pop sound effect

							if (score % 25 == 0){ //1 life every 25 points
								lives +=1; //adds a life
								lifeUpActive = true;
							}
						}
					}
				}

			}

		});

		game.addMouseListener(new MouseAdapter() { //adds mouse input to the game

			@Override
			public void mouseExited(MouseEvent e){
				paused = true; //pauses the game if the mouse leaves the window
			}   

			public void mouseEntered(MouseEvent e){
				paused = false; //unpauses when the mouse re-renters
			}

			public void mouseClicked(MouseEvent e) {
				if (menu){ //menu screen

					if (e.getX() >=  250 && e.getX() <= 250 + 85){ //information button
						if (e.getY() >=  355 && e.getY() <= 355 + 85){ //condition for mouse inside button
							Instructions = true;
							menu = false;
						}
					}

					if (e.getX() >=  450 && e.getX() <= 450 + 85){ //play button
						if (e.getY() >=  355 && e.getY() <= 355 + 85){ //condition for mouse inside button
							DifficultySelect  = true;
							menu = false;
						}

					}
				}

				if (Instructions){ //instructions screen
					if (e.getX() >=  (400 - 50) && e.getX() <= (400 + 50)){ //blue bubble
						if (e.getY() >=  (210 - 50) && e.getY() <= (210 + 50)){ //condition for mouse inside button
							showBlue = true;
							showRed = false;
							showGreen = false;
						}

					}

					if (e.getX() >=  (150 - 50) && e.getX() <= (150 + 50)){ //red bubble
						if (e.getY() >=  (210 - 50) && e.getY() <= (210 + 50)){ //condition for mouse inside button
							showRed = true;
							showBlue = false;
							showGreen = false;
						}

					}

					if (e.getX() >=  (650 - 50) && e.getX() <= (650 + 50)){ //green bubble
						if (e.getY() >=  (210 - 50) && e.getY() <= (210 + 50)){ //condition for mouse inside button
							showGreen = true;
							showRed = false;
							showBlue = false;
						}

					}

					if (e.getX() >=  10 && e.getX() <= 10+85){ //back button
						if (e.getY() >=  410 && e.getY() <= 410+85){ //condition for mouse inside button
							Instructions = false;
							showGreen = false;
							showRed = false;
							showBlue = false;
							menu = true;
						}

					}

				}

				if (DifficultySelect){ //difficulty screen
					if (e.getX() >=  360 && e.getX() <= 435){ //easy
						if (e.getY() >=  180 && e.getY() <= 215){ //condition for mouse inside button
							level = 1;
							DifficultySelect = false;
							playGame = true;
							numBubbles  = 5;
							levelLives = 10;
							lives = levelLives;
							powerUps = false;
							bubbles.clear();
							Bubble.setSpeed(1); 
							}
					}

					if (e.getX() >=  335 && e.getX() <= 455){ //normal
						if (e.getY() >=  235 && e.getY() <= 275){ //condition for mouse inside button
							level = 2;
							DifficultySelect = false;
							playGame = true;
							levelLives = 5;
							lives = levelLives;
							bubbles.clear();
							powerUps = true;
							Bubble.setSpeed(1); 
						}
					}

					if (e.getX() >=  360 && e.getX() <= 440){ //hard
						if (e.getY() >=  295 && e.getY() <= 330){ //condition for mouse inside button
							level = 3;
							DifficultySelect = false;
							playGame = true;
							levelLives = 5;
							lives = levelLives;
							numBubbles  = 15;
							Bubble.setSpeed(1.5);
							bubbles.clear();
							powerUps = true;

						}
					}

					if (e.getX() >=  350 && e.getX() <= 450){ //insane
						if (e.getY() >=  355 && e.getY() <= 390){ //condition for mouse inside button
							level = 4;
							DifficultySelect = false;
							playGame = true;
							numBubbles  = 15;
							Bubble.setSpeed(2);
							levelLives = 10;
							lives = levelLives;
							bubbles.clear();
							powerUps = false;
						}
					}

					if (e.getX() >=  10 && e.getX() <= 10+85){ //back button
						if (e.getY() >=  410 && e.getY() <= 410+85){ //condition for mouse inside button
							DifficultySelect = false;
							menu = true;
						}

					}
				}

				if (gameOver){
					if (e.getX() >=  250 && e.getX() <= 250 + 85){ //play again button
						if (e.getY() >=  355 && e.getY() <= 355 + 85){ //condition for mouse inside button
							gameOver = false;
							bubbles.clear();
							lives = 5;
							score = 0;
							Bubble.setSpeed(1); 
							savedScore = false;
						}
					}

					if (e.getX() >=  450 && e.getX() <= 450 + 85){ //home button
						if (e.getY() >=  355 && e.getY() <= 355 + 85){ //condition for mouse inside button
							menu = true;
							gameOver = false;
							playGame = false;
							bubbles.clear();
							lives = levelLives;
							score = 0;
							Bubble.setSpeed(1); 
							savedScore = false;
						}

					}
				}
			}

		});

		frame.add(game); //adds the game to the window

		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\Icon.png")); //sets the frame icon to a picture of a bubble

		Image cursor = Toolkit.getDefaultToolkit().getImage("src\\finger.png"); //imports the mouse cursor
		game.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(cursor,new Point(game.getX(), game.getY()),"custom cursor")); //sets the cursor to a custom cursor

		frame.setVisible(true); //makes the window visible
		frame.setSize(frameWidth, frameHeight); //makes the window of the sizes initialized previously
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //terminates the program if the user exits the window
		frame.setTitle("Bubble Burst"); //sets the window's title to "Bubble Burst"
		frame.setResizable(false); //makes the window a fixed size
		frame.setLocationRelativeTo(null); //centers the window on the screen
		playMusic(); //plays the music

		do {
				game.update(); //updates the game
				game.repaint(); //repaints all of the screens
				Thread.sleep(10); //slows down the painting to make the animation more smooth
				if (gameOver && !savedScore){
					saveScore(level); //saves the score if gameOver
				}
		} while (true); //keeps running
	}
}