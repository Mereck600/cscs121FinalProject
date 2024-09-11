package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import java.awt.image.BufferedImage;

public class Player extends Entity {
	
	GamePanel gp;
	KeyHandler keyH;
	public final int screenX;   //the background scrolls as the player moves 
	public final int screenY;  //these dont change
	
	public Player(GamePanel gp, KeyHandler keyH) {
		this.gp = gp;
		this.keyH = keyH;
		
		screenX =gp.screenWidth/2 -(gp.tileSize/2); //returns halfway point of the screen
		screenY =gp.screenHeight/2 - (gp.tileSize/2); 
		//colision area for the player character 
		solidArea = new Rectangle(); // x,y,width,height
		solidArea.x = 8; //this is where it starts 
		solidArea.y = 16;
		solidArea.width = 32; //this will be smaller than the character
		solidArea.height = 32;
		
		
		setDefaultValues();
		getPlayerImage();
		
	}
	//sets the default values of the player
	public void setDefaultValues() {
		worldX = gp.tileSize *23; //these are screen position they are players pos on world map
		worldY = gp.tileSize *21; 
		speed = 4; 
		direction = "down"; 
	}
	//gets the pictues needed for the player model. 
	public void getPlayerImage() {
		try {
	        up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
	        up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
	        down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
	        down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
	        left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
	        left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
	        right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
	        right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	public void update() {
		//this if is what chagnges the player character from walking animation to standing 
		if(keyH.upPressed == true || keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true) {
			//all of these ifs handle movement of the character
			if(keyH.upPressed == true) {
				direction = "up";
				
				
			}
			else if(keyH.downPressed == true) {
				direction = "down";
				
			}
			else if(keyH.leftPressed == true){
				direction = "left";
				
				
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
				
			}
			//check tile collision
			collisionOn = false;
			gp.cChecker.checkTile(this); //pases player into checkTile method in collionChecker 
			//update gets called 60x per sec so every frame this below is
			//called and when it hits 12 the player image will change
			//if colliosion is false playe can move
			if(collisionOn == false) {
				switch(direction) {
				case"up":
					worldY -= speed; //in java the uppeer left is 0,0 and x increase to right and y+ as go down
					break;
				case"down":
					worldY += speed;
					break;
				case "left":
					worldX -= speed;
					break;
				case "right":
					worldX += speed;
					break;
				
				}
			}
			
			spriteCounter++;
			if(spriteCounter > 13) { //possibly make this lower 10toofast 12a little fast 15maybe slow
				if(spriteNum == 1) {
					spriteNum = 2;
				}
				else if(spriteNum == 2) {
					spriteNum =1;				
				}
				spriteCounter =0;
			}
		}
		
		
	}
	public void draw(Graphics2D g2) {
		//test object :
		//g2.setColor(Color.white); // setColor(color c) sets a color for drawing objects		
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize); 
		//*******************************************//
		
		//here is the method that draws the player image  should be self explainitory
		//switch works the same as an if statement for the most part. 
		BufferedImage image = null;
		
		switch(direction) {
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			if(spriteNum == 2) {
				image = up2;
			}
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			if(spriteNum == 2) {
				image = down2;
			}
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			if(spriteNum == 2) {
				image = left2;
			}
			break;
		case "right":
			if(spriteNum ==1 ) {
				image = right1;
			}
			if(spriteNum == 2) {
				image = right2;
			}
			break;			
		}
		g2.drawImage(image,  screenX,  screenY,  gp.tileSize, gp.tileSize, null); 
		//image oobserver is the null val. the other stuff draws that image with the size gp
	}
}
