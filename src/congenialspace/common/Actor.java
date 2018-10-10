package congenialspace.common;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Actor {
	
	int Posx;
	int Posy;
	int Speed;
	int Health;
	
	public Actor(int Posx, int Posy, int speed, int hp, int id){
		this.Posx = Posx;
		this.Posy = Posy;
		this.Speed = speed;
		this.Health = hp;	
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException {				

		
		
	}
}
