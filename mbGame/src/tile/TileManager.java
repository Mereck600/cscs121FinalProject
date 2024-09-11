/**
 * Ok so TileManager is a class that displays the tiles
 * to recap GamePanel is where this is called.
 * remeber not to put layers above in the gamePanel
 * also all tiles are 48px so when making the screen keep in mind 
 */

package tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.awt.Graphics2D;
import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	GamePanel gp;
	public Tile [] tile;
	public int mapTileNum[][]; //array 
	
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10]; // this means im creating 10 kinds of tiles ie grass water wall can be changed 
		mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow]; //this is the array that reads .txt file 
		getTileImage();
		loadMap("/maps/world01.txt");
		
	}
	
	public void getTileImage() {
		try {
			//instanciate the tile array
			//grass
			tile[0]= new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png")); //file get put into imageIo to display
			//wall
			tile[1]= new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/wall.png"));
			tile[1].collision = true; //makes collision you dont need to change if it is not collision tile
			//water
			tile[2]= new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2].collision = true;
			//earth
			tile[3]= new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/earth.png"));
			//tree
			tile[4]= new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/tree.png"));
			tile[4].collision = true;
			//sand
			tile[5]= new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sand.png"));
		}
		catch(IOException e){
			e.printStackTrace(); //shows error
			
		}
	}
	public void loadMap(String filePath) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
			int col = 0;
			int row = 0;
			
			while(col < gp.maxWorldCol && row < gp.maxWorldRow ) {
				String line = br.readLine(); //reads a line of text from the .txt 
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" "); //split string at space and put in array
					
					int num = Integer.parseInt(numbers[col]); 
					
					mapTileNum[col][row]= num;
					col++;
				}
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			br.close();
		}catch(Exception e){
			
		}
	}
	//implementing a camera into this method so the game loads the character and it seems like the world moves 
	// three things we want to knwo for this method 1. tile image, 2. where do draw x, where to draw y
	//heres should be my final version of this unless i edit this text chaged it to render only what the player
	//can see because my computer becomes a jet and feels like its going to explode 
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow =0;
		
		
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			//first we check the tiles of world x, then we need to know where to draw it 
			// worldx is pos on map screen x is where on map we need to draw the tile 
			//so if player is at worldx&worldy:500 then we draw 500,500 thats why we sub here 
			// adding the gp.player makes it so the player is not displayed at the center this offsets it and disply in middle of the screen
			
			//this creates an imaginary boundray wich the screen will display and it is expanded a little to
			//avoid blackscreen overlaying 
			if(worldX+ gp.tileSize > gp.player.worldX -gp.player.screenX &&
					worldX  - gp.tileSize< gp.player.worldX + gp.player.screenX &&
					worldY + gp.tileSize> gp.player.worldY - gp.player.screenY &&
					worldY - gp.tileSize< gp.player.worldY +gp.player.screenY) {
				g2.drawImage(tile[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null); //tile[ileNum].image is the idex of above func
				
			}
			
			worldCol++; //increase col by one 
		
			if(worldCol == gp.maxWorldCol) { //if it hits the max for col which is 16, reset col&x then increase y
				worldCol = 0; 
				
				worldRow++; //moves to next row 
				
				
			}
			
		}
	
	}
}


/**  kinda recycle bin for code:
 * 	heres what i was doing before I realized there was a better way for the draw method  
 *
 * //row order decending increases row and in rows descending increases columns 
	// ive got a 5x5 grid to start 240 should be max x
	//row one 
	g2.drawImage(tile[1].image, 0,0, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[1].image, 48,0, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[1].image, 96,0, gp.tileSize ,gp.tileSize, null); // must increase by 48px
	g2.drawImage(tile[1].image, 144,0, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[1].image, 192,0, gp.tileSize ,gp.tileSize, null); 
	
	//row two 
	g2.drawImage(tile[1].image, 0,48, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[0].image, 48,48, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[0].image, 96,48, gp.tileSize ,gp.tileSize, null); // must increase by 48px
	g2.drawImage(tile[0].image, 144,48, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[1].image, 192,48, gp.tileSize ,gp.tileSize, null); 
	
	//row three 
	g2.drawImage(tile[1].image, 0,96, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[0].image, 48,96, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[0].image, 96,96, gp.tileSize ,gp.tileSize, null); // must increase by 48px
	g2.drawImage(tile[0].image, 144,96, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[0].image, 192,96, gp.tileSize ,gp.tileSize, null); 
	
	//row four 
	g2.drawImage(tile[1].image, 0,144, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[0].image, 48,144, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[0].image, 96,144, gp.tileSize ,gp.tileSize, null); // must increase by 48px
	g2.drawImage(tile[0].image, 144,144, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[1].image, 192,144, gp.tileSize ,gp.tileSize, null); 
	
	//row five 
	g2.drawImage(tile[1].image, 0,192, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[2].image, 48,192, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[2].image, 96,192, gp.tileSize ,gp.tileSize, null); // must increase by 48px
	g2.drawImage(tile[2].image, 144,192, gp.tileSize ,gp.tileSize, null); 
	g2.drawImage(tile[1].image, 192,192, gp.tileSize ,gp.tileSize, null); 
	
	******************************************************************
	*****************************************************************
	*this code below will print a screen of grass tiles but not exactly what i want im putting it down here 
	*incase i need something simple for later.
	*
	
	public void draw(Graphics2D g2) {
		int col = 0;
		int row =0;
		int x =0;
		int y =0;
		while(col < gp.maxScreenCol && row < gp.maxScreenRow) {
			
			g2.drawImage(tile[0].image, x, y, gp.tileSize, gp.tileSize, null); 
			col++; //increase col by one 
			x += gp.tileSize; // add a tile size to the x value
			if(col == gp.maxScreenCol) { //if it hits the max for col which is 16, reset col&x then increase y
				col = 0; 
				x =0;
				row++;
				y += gp.tileSize; 
				
			}
			
		}
	
	
	*/