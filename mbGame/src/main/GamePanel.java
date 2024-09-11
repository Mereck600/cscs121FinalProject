package main;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
	/**
	 * This is to set up the tiles and pixels on the screen 
	 */
	//Screen settings
	final int originalTileSize = 16; //means 16x16 tile default size for player character can update later
	final int scale = 3;  // this will make the character look 48x48 since pc is bigger screen
	public final int tileSize = originalTileSize * scale; //48x48 tile actuall displayed on screen 
	
	public final int maxScreenCol = 16; // 16 tiles on the screen vertically 
	public final int maxScreenRow = 12; // 16 tiles on the screen horizontally ratio 4x3
	public final int screenWidth = tileSize * maxScreenCol; //768 pixels 
	public final int screenHeight = tileSize * maxScreenRow; // 576 pixels
	
	//world map parameters: these can be changed if we want
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	//FPS 
	int FPS = 60;
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler();	
	
	Thread gameThread; //This is the clock set up once started it will run until stopped kinda like a loop
	public CollisionChecker cChecker = new CollisionChecker(this);
	public Player player = new Player(this,keyH);
	
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); //if set to true all the drawing for this component will be buffered off screen
		this.addKeyListener(keyH);
		this.setFocusable(true); //this makes the gamePanel ale to recieve input
		
	}


/**
 * this is the run method i.e. runnable and starts the thread thread calls this 
 */
	
	public void startGameThread() {
		gameThread = new Thread(this); //passing game panel class to this constructor 
		gameThread.start();
	}
	
	/**
	 * THis is the core of the game this is the game loop so in the loop it updates then repaints etc
	 */
	@Override
	//public void run() {
		//timing is in nano instead of millis to be more precise and aviod err 1bil nano is 1sec
		//double drawInterval = 1000000000/FPS; //0.01666secs therefore allocated time for a loop is this 
		//double nextDrawTime = System.nanoTime() + drawInterval; 
		//while(gameThread != null) {
			 
			
			
			
			//step 1: update information such as characters positions 
			//update();
			
			
			//step2: Draw the screen with the updated information attemping to do either 30 or 60 fps
			//repaint(); //this is how you call paint component method
			
			
			
			//try { //pauses the loop until the sleeptime is done 
				//double remainingTime = nextDrawTime - System.nanoTime();
				//remainingTime = remainingTime /1000000; //this is now correct bc you need to convert to mil
				
				//if(remainingTime <0) {
				//	remainingTime = 0;  //means that if the code takes too long set to 0 
				//}
				//Thread.sleep((long) remainingTime);
				
				//nextDrawTime += drawInterval;
				
			//} catch (InterruptedException e) {
				// TODO Auto-generated catch block
			//	e.printStackTrace();
			//}
			
		//}
			
	//}
	/**
	 * every loop we add pasttime divided by drawInterval to delta 
	 * then we update and repaint and then reset delta
	 * fps is 60fps.
	 * this is more accurate than the aboe thread method
	 */
	public void run() {
		double drawInterval = 1000000000/FPS;
		
		double delta=0;
		long lastTime = System.nanoTime();
		long currentTime;
		while(gameThread !=null) {
			
		currentTime = System.nanoTime();
		delta +=(currentTime -lastTime)/drawInterval;
		lastTime = currentTime ;
		if(delta >= 1) {
		update();
		repaint();
		delta--;
		}
		
		}
	}
	
	
	/**
	 * Every time a key is pressed it is captured by KeyHandler and th eupdate chages the player cords
	 * then the pain component is called and rePaints the player model
	 */
	public void update() {
		player.update();
		
	}
	/**
	 * Existing componnet in java used to paint different characters or elements on screen
	 * graphics class is a class used to draw objects on screen
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);  //super means parent class ie. JPanel 
		
		Graphics2D g2 = (Graphics2D)g;//Graphics2D is a class that extends the graphics class to provide control over geo, cords, color, text
		
		//*** DO NOT PUT DRAW ITEMS ABOVE (VERY IMPORTANT)  ****//
		tileM.draw(g2); // this needs to be before player tiles or any other layer this is base
		
		player.draw(g2);
		
		
		
		g2.dispose(); //saves memory while not in use
		
		
		
	}
	
}
