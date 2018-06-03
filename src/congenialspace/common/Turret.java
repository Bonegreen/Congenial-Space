package congenialspace.common;

import java.io.FileNotFoundException;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Turret extends Actor{
	
	SpriteSheet TurretSheet;
	Image Idle;
	int tileWidth = 32;

	public Turret(int[] Level) throws SlickException {
		super(Level[0], Level[1], Level[2], Level[3], Level[4]);
		
		TurretSheet = new SpriteSheet("rsc/Turret_Spritesheet.png", 32, 32);
		Idle = TurretSheet.getSprite(Level[4],0);
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{				

		g.drawImage(Idle, Posx*tileWidth, Posy*tileWidth);
		
	}

}
