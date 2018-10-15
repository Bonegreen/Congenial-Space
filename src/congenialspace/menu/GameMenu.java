package congenialspace.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameMenu extends BasicGameState{
	
	int state;

	Image Title;
	
	int Selection = 0;
	
	public GameMenu(int state){
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Title = new Image("rsc/Title.png");		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(Title, 0, 0);
		
		g.fillOval(115, 393 + 50*Selection, 10, 10);
		
	}

	@Override
	public void update(GameContainer c, StateBasedGame s, int t) throws SlickException {
		Input input = c.getInput();
		
		if (input.isKeyPressed(Input.KEY_DOWN)) {
			if(Selection < 3) {
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
				input.clearKeyPressedRecord();
				s.enterState(3);
				break;
			case 1:
				input.clearKeyPressedRecord();
				s.enterState(4);
				break;
			case 2:
				input.clearKeyPressedRecord();
				s.enterState(2);
				break;
			case 3:
				c.exit();
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
