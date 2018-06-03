package congenialspace.common;

import java.io.FileNotFoundException;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Sound;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class GameMain extends BasicGameState{
	
	public int state;
	Image Map;
	private TiledMap Spaceship;
	static int tileWidth = 32;
	private static boolean[][] blocked;
	
	Character Team1;
	Character Team2;
	Character Team3;
	Character Team[] = {Team1, Team2, Team3};
	
	Turret [] Beam;
	
	int Speed = 0;
	int Move[] = {4,4,4,4,4,4};
	int turn = 0;
	float Currentx;
	float Currenty;
	
	Sound error;
	
	Boolean attack = false;
	
	Rectangle Path[] = {null,null,null,null,null,null};
	
	Level currentLevel = Level.LEVEL_1;
	
	public GameMain(int state){
        this.state = state;
    }

	@Override
	public void init(GameContainer gc, StateBasedGame arg1) throws SlickException {
		Map = new Image("rsc/Map.png");
		
		Spaceship = new TiledMap("rsc/Ship.tmx");
		blocked = new boolean[Spaceship.getWidth()][Spaceship.getHeight()];
			
		Team1 = new Character(64, 64, 4, 2, 1);
		Team2 = new Character(64, 128, 3, 2, 2);
		Team3 = new Character(128, 128, 4, 2, 3);
		Team[0] = Team1;
		Team[1] = Team2;
		Team[2] = Team3;
		Path[0] = new Rectangle(Team[0].Posx, Team[0].Posy, 32, 32);
		error = new Sound("rsc/error.ogg");
		
		try {
			Beam = currentLevel.level();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//getDisplayNodes(gc);
		
		setMapTerrain();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 0, 800, 600);
		Spaceship.render(0, 0);
		
		for(int i = 0; i < Team.length; i++) {
			Team[i].render(gc, g);
		}
		
		for(int i = 0; i < Beam.length; i++) {
			Beam[i].render(gc, g);
		}
		
		g.setColor(new Color(255, 0, 0, 150));
		if(Path[1] != null) {
			g.fill(Path[1]);
		}
		if(Path[2] != null) {
			g.fill(Path[2]);
		}
		if(Path[3] != null) {
			g.fill(Path[3]);
		}
		if(Path[4] != null) {
			g.fill(Path[4]);
		}
		
		//Display remaining moves for selected player
		g.setColor(Color.white);
		g.drawString("Moves: " + (Team[turn].Speed - Speed), 500, 500);
	}

	@Override
	public void update(GameContainer c, StateBasedGame s, int t) throws SlickException {
		Input input = c.getInput();
		int x;
		int y;
			
		if(input.isMousePressed(0)) {
			x = input.getMouseX();
			y = input.getMouseY();
			checkButton(x,y);
		}
		
		
		if (input.isKeyPressed(Input.KEY_DOWN))
		{
		    if(Speed < Team[turn].Speed && !isBlocked(Path[Speed].getX(), Path[Speed].getY() + 32)){
		    	Move[Speed] = 0;
		    	Path[Speed+1] = new Rectangle(Path[Speed].getX(), Path[Speed].getY() + 32, 32, 32);
		     	Currentx = Path[Speed+1].getX();
		    	Currenty = Path[Speed+1].getY();
		    	Speed++;
		    }else{
				error.play();
			}
		}
		
		if (input.isKeyPressed(Input.KEY_UP))
		{
			if(Speed < Team[turn].Speed && !isBlocked(Path[Speed].getX(), Path[Speed].getY() - 32)){
				Move[Speed] = 1;
				Path[Speed+1] = new Rectangle(Path[Speed].getX(), Path[Speed].getY() - 32, 32, 32);
				Currentx = Path[Speed+1].getX();
				Currenty = Path[Speed+1].getY();
				Speed++;
		    }else{
				error.play();
			}
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT))
		{
			if(Speed < Team[turn].Speed && !isBlocked(Path[Speed].getX() +32, Path[Speed].getY())){
				Move[Speed] = 2;
				Path[Speed+1] = new Rectangle(Path[Speed].getX() +32, Path[Speed].getY(), 32, 32);
				Currentx = Path[Speed+1].getX();
				Currenty = Path[Speed+1].getY();
				Speed++;
		    }else{
				error.play();
			}
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT))
		{
			if(Speed < Team[turn].Speed && !isBlocked(Path[Speed].getX() -32, Path[Speed].getY())){
				Move[Speed] = 3;
				Path[Speed+1] = new Rectangle(Path[Speed].getX() -32, Path[Speed].getY(), 32, 32);
				Currentx = Path[Speed+1].getX();
				Currenty = Path[Speed+1].getY();
				Speed++;
		    }else{
				error.play();
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER))
		{
			if(moveCheck(Currentx, Currenty)) {
			
				System.out.println("Move: "+ Move[0] + "Speed:" + Speed);
				Team[turn].move(Move);
					
				if(turn < Team.length - 1) {
					turn++;
				}else {
					turn = 0;
				}
			}else{
				error.play();
			}
			
			for(int j = 0; j < 6; j++) {
				Move[j] = 4;
			}
					
			Path[0] = new Rectangle(Team[turn].Posx, Team[turn].Posy, 32, 32);
			Path[1] = null;
			Path[2] = null;
			Path[3] = null;
			Path[4] = null;
			Speed = 0;
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			attack = true;
		}
		
		if (input.isKeyDown(Input.KEY_ESCAPE))
		{
		    c.exit();
		}
	}

	private void checkButton(int bx, int by) {
		System.out.println("" + bx + " " + by);
		
	}
	
	private boolean moveCheck(float x, float y) {
		for(int i = 0; i < Team.length; i++){
			if(Team[i].Posx == x && Team[i].Posy == y) {
				return false;
			}
		}
		
		return true;	
	}
	
	private void setMapTerrain() {
		for (int xAxis=0;xAxis<Spaceship.getWidth(); xAxis++)
        {
             for (int yAxis=0;yAxis<Spaceship.getHeight(); yAxis++)
             {
                 int tileID = Spaceship.getTileId(xAxis, yAxis, 0);
                 String value = Spaceship.getTileProperty(tileID, "blocked", "false");
                 if ("true".equals(value))
                 {
                     blocked[xAxis][yAxis] = true;
                 }else{
                	 blocked[xAxis][yAxis] = false;
                 }
             }
         }
	}

	@Override
	public int getID() {
		return 0;
	}
	
	static boolean isBlocked(float x, float y)
    {
        int xBlock = (int)x / tileWidth;
        int yBlock = (int)y / tileWidth;
        return blocked[xBlock][yBlock];
    }
	
	@SuppressWarnings("unused")
	private void getDisplayNodes(GameContainer gc) {
		DisplayMode[] modes = null;
		try {
			modes = Display.getAvailableDisplayModes();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}

        for (int i=0;i<modes.length;i++) {
            DisplayMode current = modes[i];
            System.out.println(current.getWidth() + "x" + current.getHeight() + "x" +
                                current.getBitsPerPixel() + " " + current.getFrequency() + "Hz");
        }
        System.out.println(gc.getAspectRatio());
	}

}
