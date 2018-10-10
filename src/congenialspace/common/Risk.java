package congenialspace.common;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import congenialspace.menu.GameCredits;
import congenialspace.menu.GameMenu;
import congenialspace.menu.GameOptions;
 
public class Risk extends StateBasedGame{
 
	public static final String name = "Congenial Space";
	public static final int Game = 0;
	public static final int Menu = 1;
	public static final int Credits = 2;
	public static final int Cutscene = 3;
	public static final int Options = 4;
	public static final int MiniGame = 7;
	
    public static void main(String[] args) {
    	AppGameContainer app;
    	try{
    		app = new AppGameContainer(new Risk(name));
    		//app.setFullscreen(true);
    		app.setDisplayMode(960, 640, false);
    		//app.setDisplayMode(1920, 1080, true);
    	    //app.setIcon("rsc/Icon1.png");
    	    app.setShowFPS(false);
    	    app.start();
    	}catch(SlickException e){
    	    e.printStackTrace();
    	}
    }
 
    public Risk(String name) {
        super(name);
    }
 
    @Override
    public void initStatesList(GameContainer container) throws SlickException {
    	
    	this.addState(new GameMenu(Menu));
    	this.addState(new GameMain(Game));
    	this.addState(new GameCredits(Credits));
    	this.addState(new GameOptions(Options));
}
 
}