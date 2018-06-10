package congenialspace.common;

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
	
	Animation Players[] = {Foward, Back, Left, Right};
	
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
		g.drawImage(Idle, Posx, Posy);
		//g.drawAnimation(Players[0], Posx, Posy);
	}
	
	public void move(int Move[]){
		for(int i = 0; i < Speed; i++) {
			switch (Move[i]) {
				case 0:
					Posy += 32;
					break;
				case 1:
					Posy -= 32;
					break;
				case 2:
					Posx += 32;
					break;
				case 3:
					Posx -= 32;
					break;
				default:
					break;
			}
						
		}
	}
	
	public void Attack(int Dir, Turret[] turret) {
			CheckTarget(Dir, turret);
	}
	
	public boolean CheckTarget(int Dir, Turret[] turret) {
		int x, y;
		for(int i = 1; i < 4; i++) {
			x = Posx + 32*i;
			for(int k = 0; k < turret.length; k++) {
				System.out.println("x: " + x + " kx: " + turret[k].Posx);
				System.out.println("y: " + Posy + " ky: " + turret[k].Posy);
				if(x == turret[k].Posx && Posy == turret[k].Posy) {
					System.out.println("Shot A Dude");
					return true;
				}
			}
		}
		System.out.println("Miss");
		return false;
		
	}
	
}
