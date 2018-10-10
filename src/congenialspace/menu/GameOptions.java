package congenialspace.menu;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class GameOptions extends BasicGameState{
	
	int state;

	Image Credits;
		
	public GameOptions(int state){
		this.state = state;
	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Credits = new Image("rsc/Credits.png");		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		g.drawImage(Credits, 0, 0);
		
	}

	@Override
	public void update(GameContainer c, StateBasedGame s, int t) throws SlickException {
		Input input = c.getInput();
		
		if (input.isKeyPressed(Input.KEY_ENTER)) {
			input.clearKeyPressedRecord();
			s.enterState(1);
		}
	}

	@Override
	public int getID() {
		return state;
	}

}
