package congenialspace.common;

import java.awt.Font;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameLoadout extends BasicGameState{

	int state;
	
	private int Selection;
	private int[]  Weapon;
	
	private int NumberofUnits = 3;
	
	Image Loadout;
	Image Soldier;
	
	Image Shotgun;
	Image Rifle;
	
	SpriteSheet PlayerSheet;
	
	Font font = new Font("Candara", Font.PLAIN, 48);
	TrueTypeFont ttf = new TrueTypeFont(font, true);
	
	Font smallfont = new Font("Candara", Font.PLAIN, 28);
	TrueTypeFont ttfs = new TrueTypeFont(smallfont, true);
	
	public GameLoadout(int state){
		this.state = state;
	}
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		PlayerSheet = new SpriteSheet("rsc/Player_Spritesheet.png", 32, 32);
		
		Loadout = new Image("rsc/Loadout.png");
		Shotgun = new Image("rsc/Shotgun.png");
		Rifle = new Image("rsc/Rifle.png");
		Soldier = PlayerSheet.getSprite(0,0);;
		
		Weapon = new int[NumberofUnits];
		
	}

	@Override
	public void render(GameContainer c, StateBasedGame s, Graphics g) throws SlickException {
		//Render Background Image
		g.drawImage(Loadout, 0, 0);
		
		//Render Soldier Images
		
		for(int i = 0; i < NumberofUnits; i++) {
			Soldier.draw(96, 128 + 98*i, 2f);
			
			switch(Weapon[i]) {
				case 0:
					Shotgun.draw(224, 128 + 98*i);
					ttfs.drawString(500, 128 + 98*i, "Damage: 20");
					ttfs.drawString(500, 158 + 98*i, "Range: 10");
					break;
				case 1:
					Rifle.draw(224, 128 + 98*i);
					ttfs.drawString(500, 128 + 98*i, "Damage: 15");
					ttfs.drawString(500, 158 + 98*i, "Range: 20");
					break;
				default:
					break;
			}
		}
		
		g.setColor(Color.yellow);
		g.drawRect(96, 128 + 96*Selection, 384, 64);
		ttf.drawString(60, 20, "Select Loadout");
	}

	@Override
	public void update(GameContainer c, StateBasedGame s, int t) throws SlickException {
		Input input = c.getInput();
		
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			if(Selection < NumberofUnits - 1) {
				Selection++;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			if(Selection > 0) {
				Selection--;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT)) {
			if(Weapon[Selection] < 2) {
				Weapon[Selection]++;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT)) {
			if(Weapon[Selection] > 0) {
				Weapon[Selection]--;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			s.enterState(0);
		}
	}

	@Override
	public int getID() {
		return state;
	}
	
}
