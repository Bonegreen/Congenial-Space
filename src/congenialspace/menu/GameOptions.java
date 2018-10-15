package congenialspace.menu;

import java.awt.Font;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOptions extends BasicGameState{
	
	int state;

	Image Options;
	int Selection = 0;
	
	int Resolution = 0;
	int Audio = 100;
	int Music = 100;
	int Speed = 50;
	
	Font font = new Font("Candara", Font.PLAIN, 48);
	TrueTypeFont ttf = new TrueTypeFont(font, true);
		
	public GameOptions(int state){
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Options = new Image("rsc/Options.png");		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(Options, 0, 0);
		
		g.fillOval(50, 136 + 80*Selection, 20, 20);
		
		switch (Resolution) {
		case 0:
			ttf.drawString(600, 125, "1920*1080");
			break;
		case 1:
			ttf.drawString(600, 125, "920*720");
			break;
		default:
			break;
		}
		
		ttf.drawString(600, 200, " " + Audio + "%");
		ttf.drawString(600, 275, " " + Music + "%");
		ttf.drawString(600, 350, " " + Speed + "%");
	}

	@Override
	public void update(GameContainer c, StateBasedGame s, int t) throws SlickException {
		Input input = c.getInput();
		
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			if(Selection < 4) {
				Selection++;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_UP)) {
			if(Selection > 0) {
				Selection--;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			switch (Selection) {
			case 0:
				if(Resolution < 1) {
					Resolution++;
				}else {
					Resolution = 0;
				}
				
				break;
			case 1:
				if(Audio < 100) {
					Audio += 10;
				}else {
					Audio = 0;
				}
				break;
			case 2:
				if(Music < 100) {
					Music += 10;
				}else {
					Music = 0;
				}
				break;
			case 3:
				if(Speed < 100) {
					Speed += 10;
				}else {
					Speed = 0;
				}
				break;
			case 4:
				
				c.setSoundVolume((float)Audio/100);
				c.setMusicVolume((float)Music/100);
				
				input.clearKeyPressedRecord();
				s.enterState(1);
				break;
			default:
				break;
			}
		}
	}

	@Override
	public int getID() {
		return state;
	}

}
