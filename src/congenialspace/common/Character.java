package congenialspace.common;

import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Character extends Actor{
	
	SpriteSheet PlayerSheet;
	
	Image Idle;
	
	Animation Foward;
	Animation Back;
	Animation Right;
	Animation Left;
	
	int tileWidth = 32;
	
	Animation Players[] = {Foward, Back, Left, Right};
	
	private boolean isMoving = false;
	
	public Character(int Posx, int Posy, int speed, int hp, int id) throws SlickException{
		super(Posx, Posy, speed, hp, id);
		
		PlayerSheet = new SpriteSheet("rsc/Player_Spritesheet.png", 32, 32);
		
		Idle = PlayerSheet.getSprite(id,0);
		
		//Assign Player Animations from Spritesheet
		int n = 0;//Animation Number
				
		//for(int x=0; x<3; x++) {//Number of Characters
			for(int y = 0; y<4; y++) {//Number of animations per character
				Players[n] = new Animation();
				Players[n].setAutoUpdate(true);
			      
				int tY = 0;
				int why;
				for (int row=0;row<3;row++) {
					why = tY+3*y;
					Players[n].addFrame(PlayerSheet.getSprite(id,why), 150);
					//System.out.println("Tx: "+id +", Ty: "+tY +", n: "+n +", why: " +why);
					tY++;
				}
				n++;
			}
					
		//}
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException 
	{				
		if(isMoving) {
			g.drawAnimation(Players[0], Posx, Posy);
		}else {
			g.drawImage(Idle, Posx*tileWidth, Posy*tileWidth);
		}
	}
	
	public void move(int Move[]){
		
		isMoving = true;
		//Take the list of moves and render slower
		
		for(int i = 0; i < Speed; i++) {
			switch (Move[i]) {
				case 0:
					Posy += 1;
					break;
				case 1:
					Posy -= 1;
					break;
				case 2:
					Posx += 1;
					break;
				case 3:
					Posx -= 1;
					break;
				default:
					break;
			}		
		}
		
		isMoving = false;
	}
	
	public void Attack(int Dir, LinkedList<Turret> beam) {
			CheckTarget(Dir, beam);
	}
	
	public void CheckTarget(int Dir, LinkedList<Turret> beam) {
		int x, y;
		switch (Dir) {
		case 0://Down
			for(int i = 1; i < 4; i++) {
				y = Posy + i;
				for(int k = 0; k < beam.size(); k++) {
					System.out.println("x: " + y + " kx: " + beam.get(k).Posx);
					System.out.println("y: " + Posy + " ky: " + beam.get(k).Posy);
					if(Posx == beam.get(k).Posx && y == beam.get(k).Posy) {
						beam.remove(k);
						System.out.println("Shot A Dude");
						return;
					}
				}
			}
			break;
		case 1://Up
			for(int i = 1; i < 4; i++) {
				y = Posy - i;
				for(int k = 0; k < beam.size(); k++) {
					System.out.println("x: " + y + " kx: " + beam.get(k).Posx);
					System.out.println("y: " + Posy + " ky: " + beam.get(k).Posy);
					if(Posx == beam.get(k).Posx && y == beam.get(k).Posy) {
						beam.remove(k);
						System.out.println("Shot A Dude");
						return;
					}
				}
			}
			break;
		case 2://Right
			for(int i = 1; i < 4; i++) {
				x = Posx + i;
				for(int k = 0; k < beam.size(); k++) {
					System.out.println("x: " + x + " kx: " + beam.get(k).Posx);
					System.out.println("y: " + Posy + " ky: " + beam.get(k).Posy);
					if(x == beam.get(k).Posx && Posy == beam.get(k).Posy) {
						beam.remove(k);
						System.out.println("Shot A Dude");
						return;
					}
				}
			}
			break;
		case 3://Left
			for(int i = 1; i < 4; i++) {
				x = Posx - i;
				for(int k = 0; k < beam.size(); k++) {
					System.out.println("x: " + x + " kx: " + beam.get(k).Posx);
					System.out.println("y: " + Posy + " ky: " + beam.get(k).Posy);
					if(x == beam.get(k).Posx && Posy == beam.get(k).Posy) {
						beam.remove(k);
						System.out.println("Shot A Dude");
						return;
					}
				}
			}
			break;
		default:
			break;
	}
		System.out.println("Miss");
		return;
		
	}
	
}
