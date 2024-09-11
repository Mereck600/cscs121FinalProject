/**
 * This is the parent class for the Player Class and any other character classes
 * entity class : stores variables that will be used in player, monster and npc classes
 */

package entity;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public class Entity {
	public int worldX, worldY; //world x and y 
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;//describes an image with an accessible buffer of image data. (used to store img)
	public String direction;
	
	public int spriteCounter =0;
	public int spriteNum =1;
	public Rectangle solidArea;  //this will be hit makers
	public boolean collisionOn = false;
	
	
}
