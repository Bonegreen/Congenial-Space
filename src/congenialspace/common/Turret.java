package congenialspace.common;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Turret extends Actor{
	
	SpriteSheet TurretSheet;
	Image Idle;
	int tileWidth = 32;
	int x,y;
	
	private int dir = 3;

	public Turret(int[] Level) throws SlickException {
		super(Level[0], Level[1], Level[2], Level[3], Level[4]);
		
		TurretSheet = new SpriteSheet("rsc/Turret_Spritesheet.png", 32, 32);
		Idle = TurretSheet.getSprite(Level[4],0);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{				

		g.drawImage(Idle, Posx*tileWidth, Posy*tileWidth);
		
		//Display Attack direction
		g.setColor(new Color(255, 0, 0, 150));
		switch (dir) {
		case 0:
			g.fillRect(Posx*tileWidth, Posy*tileWidth + tileWidth, 32, 32);
			break;
		case 1:
			g.fillRect(Posx*tileWidth, Posy*tileWidth - tileWidth, 32, 32);
			break;
		case 2:
			g.fillRect(Posx*tileWidth + tileWidth, Posy*tileWidth, 32, 32);
			break;
		case 3:
			g.fillRect(Posx*tileWidth -tileWidth, Posy*tileWidth, 32, 32);
			break;
		default:
			break;
		}
		
	}

	public void attack(LinkedList<Character> team) {
		//Check Facing Direction
		switch (dir) {
			case 0://Down
				for(int i = 1; i < 4; i++) {
					x = Posx - i;
					for(int k = 0; k < team.size(); k++) {
						//System.out.println("x: " + x + " kx: " + team.get(k).Posx);
						//System.out.println("y: " + Posy + " ky: " + team.get(k).Posy);
						if(x == team.get(k).Posx && Posy == team.get(k).Posy) {
							team.remove(k);
							System.out.println("Shot A Dude");
						}else {
							System.out.println("Miss");
						}
					}
				}
			case 1://Up
				for(int i = 1; i < 4; i++) {
					y = Posy - i;
					for(int k = 0; k < team.size(); k++) {
						//System.out.println("x: " + y + " kx: " + team.get(k).Posx);
						//System.out.println("y: " + Posy + " ky: " + team.get(k).Posy);
						if(Posx == team.get(k).Posx && y == team.get(k).Posy) {
							team.remove(k);
							System.out.println("Shot A Dude");
						}else {
							System.out.println("Miss");
						}
					}
				}
				break;
			case 2://Right
				for(int i = 1; i < 4; i++) {
					x = Posx + i;
					for(int k = 0; k < team.size(); k++) {
						//System.out.println("x: " + x + " kx: " + team.get(k).Posx);
						//System.out.println("y: " + Posy + " ky: " + team.get(k).Posy);
						if(x == team.get(k).Posx && Posy == team.get(k).Posy) {
							team.remove(k);
							System.out.println("Shot A Dude");
						}else {
							System.out.println("Miss");
						}
					}
				}
				break;
			case 3://Left
				for(int i = 1; i < 4; i++) {
					x = Posx - i;
					for(int k = 0; k < team.size(); k++) {
						//System.out.println("x: " + x + " kx: " + team.get(k).Posx);
						//System.out.println("y: " + Posy + " ky: " + team.get(k).Posy);
						if(x == team.get(k).Posx && Posy == team.get(k).Posy) {
							team.remove(k);
							System.out.println("Shot A Dude");
						}else {
							System.out.println("Miss");
						}
					}
				}
				break;
			default:
				break;
		}
		//Rotate the attack direction of the turret clockwise
		if(dir < 3) {
			dir += 1;
		}else {
			dir = 0;
		}
		
	}
}
