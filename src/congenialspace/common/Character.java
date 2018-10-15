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
	Image img_Down;
	Image img_Up;
	Image img_Right;
	Image img_Left;
	
	
	Animation Foward;
	Animation Back;
	Animation Right;
	Animation Left;
	
	int Range;
	
	int tileWidth = 32;
	
	Animation Players[] = {Foward, Back, Left, Right};
	
	private boolean isMoving = false;
		
	public Character(int Posx, int Posy, int speed, int hp, int id, int Range) throws SlickException{
		super(Posx, Posy, speed, hp, id);
		
		PlayerSheet = new SpriteSheet("rsc/Player_Spritesheet.png", 32, 32);
		
		Idle = PlayerSheet.getSprite(id,0);
		img_Down = PlayerSheet.getSprite(id,0);
		img_Up = PlayerSheet.getSprite(id,3);
		img_Right = PlayerSheet.getSprite(id,7);
		img_Left = PlayerSheet.getSprite(id,10);
		
		
		this.Range = Range;
				
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
					Idle = img_Down;
					break;
				case 1:
					Posy -= 1;
					Idle = img_Up;
					break;
				case 2:
					Posx += 1;
					Idle = img_Right;
					break;
				case 3:
					Posx -= 1;
					Idle = img_Left;
					break;
				default:
					break;
			}		
		}
		
		isMoving = false;
	}
	
	public void Attack(int Dir, LinkedList<Turret> beam, LinkedList<Character> character, LinkedList<Alien> alien) {
		int x, y;
		switch (Dir) {
		case 0://Down
			x = Posx;
			for(int i = 1; i < Range; i++) {
				y = Posy + i;
				Idle = img_Down;
				CheckTargets(beam, character, alien, x, y);
			}
			break;
		case 1://Up
			x = Posx;
			for(int i = 1; i < Range; i++) {
				y = Posy - i;
				Idle = img_Up;
				CheckTargets(beam, character, alien, x, y);
			}
			break;
		case 2://Right
			y = Posy;
			Idle = img_Right;
			for(int i = 1; i < Range; i++) {
				x = Posx + i;
				CheckTargets(beam, character, alien, x, y);
			}
			break;
		case 3://Left
			y = Posy;
			for(int i = 1; i < Range; i++) {
				x = Posx - i;
				Idle = img_Left;
				CheckTargets(beam, character, alien, x, y);
			}
			break;
		default:
			break;
	}
		System.out.println("Miss");
		return;
		
	}
	
	private void CheckTargets(LinkedList<Turret> beam, LinkedList<Character> character, LinkedList<Alien> alien, int x, int y) {
		int EnemyNumber = Integer.max(beam.size(), Integer.max(character.size(), alien.size()));
		
		//TODO Fix so only first enemy is killed
		
		for(int k = 0; k < EnemyNumber; k++) {
			if(k < beam.size()) {
				if(x == beam.get(k).Posx && y == beam.get(k).Posy) {
					beam.remove(k);
					break;
				}
			}
			if(k < character.size()) {
				if(x == character.get(k).Posx && y == character.get(k).Posy) {
					character.remove(k);
					break;
				}
			}
			if(k < alien.size()) {
				if(x == alien.get(k).Posx && y == alien.get(k).Posy) {
					alien.remove(k);
					break;
				}
			}
		}
		
		return;
	}
}
