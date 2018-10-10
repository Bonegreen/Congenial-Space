package congenialspace.common;

import java.io.FileNotFoundException;
import java.util.LinkedList;

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
	LinkedList<Character> Team;
	
	LinkedList<Turret> Beam;
	
	//TODO
	Alien alien;
	
	int Speed = 0;
	int Move[] = {4,4,4,4,4,4};
	int turn = 0;
	float Currentx;
	float Currenty;
	int AttackDir = 3;
	
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
		
		Team = new LinkedList<Character>();
			
		Team1 = new Character(2, 2, 4, 2, 1);
		Team2 = new Character(2, 4, 3, 2, 2);
		Team3 = new Character(4, 4, 4, 2, 3);
		Team.add(Team1);
		Team.add(Team2);
		Path[0] = new Rectangle(Team.get(0).Posx*tileWidth, Team.get(0).Posy*tileWidth, 32, 32);
		error = new Sound("rsc/error.ogg");
		
		try {
			Beam = currentLevel.level();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		//TODO
		int[] a = new int[5];
		a[0] = 16;
		a[1] = 5;
		a[2] = 4;
		a[3] = 5;
		a[4] = 0;
		alien = new Alien(a, blocked);
		
		//getDisplayNodes(gc);
		
		setMapTerrain();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame s, Graphics g) throws SlickException {
		g.setColor(Color.gray);
		g.fillRect(0, 0, 800, 600);
		Spaceship.render(0, 0);
				
		//Render Allies and Enemies
		for(int i = 0; i < Team.size(); i++) {
			Team.get(i).render(gc, g);
		}
		
		for(int i = 0; i < Beam.size(); i++) {
			Beam.get(i).render(gc, g);
		}
		
		alien.render(gc, g);
		
		//Render Move Path
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
		
		//Render an attack marker in the facing direction
		if(attack) {
			switch (AttackDir) {
			case 0:
				g.fillRect(Team.get(turn).Posx*tileWidth, Team.get(turn).Posy*tileWidth + tileWidth, 32, 32);
				break;
			case 1:
				g.fillRect(Team.get(turn).Posx*tileWidth, Team.get(turn).Posy*tileWidth - tileWidth, 32, 32);
				break;
			case 2:
				g.fillRect(Team.get(turn).Posx*tileWidth + tileWidth, Team.get(turn).Posy*tileWidth, 32, 32);
				break;
			case 3:
				g.fillRect(Team.get(turn).Posx*tileWidth -tileWidth, Team.get(turn).Posy*tileWidth, 32, 32);
				break;
			default:
				break;
			}
		}
		
		//Display remaining moves for selected player
		g.setColor(Color.white);
		
		if(Team.size() > 0) {
			g.drawString("Moves: " + (Team.get(turn).Speed - Speed), 500, 500);
		}
		
		if(attack) {
			g.drawString("Mode: Attack", 500, 530);
		}else {
			g.drawString("Mode: Move", 500, 530);
		}
		
		g.drawString("Turn " + (turn), 500, 515);
	}

	@Override
	public void update(GameContainer c, StateBasedGame s, int delta) throws SlickException {
		Input input = c.getInput();
		
		//Test click input
		int x;
		int y;
			
		if(input.isMousePressed(0)) {
			x = input.getMouseX();
			y = input.getMouseY();
			checkButton(x,y);
		}
		
		//Keyboard Input
		if (input.isKeyPressed(Input.KEY_DOWN))
		{
			if(!attack) {
				if(Speed < Team.get(turn).Speed && !isBlocked(Path[Speed].getX(), Path[Speed].getY() + 32)){
			    	Move[Speed] = 0;
			    	Path[Speed+1] = new Rectangle(Path[Speed].getX(), Path[Speed].getY() + 32, 32, 32);
			     	Currentx = Path[Speed+1].getX();
			    	Currenty = Path[Speed+1].getY();
			    	Speed++;
			    }else{
					error.play();
				}
			}else {
				AttackDir = 0;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_UP))
		{
			if(!attack) {
				if(Speed < Team.get(turn).Speed && !isBlocked(Path[Speed].getX(), Path[Speed].getY() - 32)){
					Move[Speed] = 1;
					Path[Speed+1] = new Rectangle(Path[Speed].getX(), Path[Speed].getY() - 32, 32, 32);
					Currentx = Path[Speed+1].getX();
					Currenty = Path[Speed+1].getY();
					Speed++;
			    }else{
					error.play();
				}
			}else {
				AttackDir = 1;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_RIGHT))
		{
			if(!attack) {
				if(Speed < Team.get(turn).Speed && !isBlocked(Path[Speed].getX() +32, Path[Speed].getY())){
					Move[Speed] = 2;
					Path[Speed+1] = new Rectangle(Path[Speed].getX() +32, Path[Speed].getY(), 32, 32);
					Currentx = Path[Speed+1].getX();
					Currenty = Path[Speed+1].getY();
					Speed++;
			    }else{
					error.play();
				}
			}else {
				AttackDir = 2;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_LEFT))
		{
			if(!attack) {
				if(Speed < Team.get(turn).Speed && !isBlocked(Path[Speed].getX() -32, Path[Speed].getY())){
					Move[Speed] = 3;
					Path[Speed+1] = new Rectangle(Path[Speed].getX() -32, Path[Speed].getY(), 32, 32);
					Currentx = Path[Speed+1].getX();
					Currenty = Path[Speed+1].getY();
					Speed++;
			    }else{
					error.play();
				}
			}else {
				AttackDir = 3;
			}
		}
		
		if (input.isKeyPressed(Input.KEY_ENTER))
		{
			
			if(attack) {
				
				Team.get(turn).Attack(AttackDir, Beam);
				attack = false;
				
				if(turn < Team.size() - 1) {
					turn++;
				}else {
					turn = 0;
					for(int i = 0; i < Beam.size(); i++) {
						Beam.get(i).attack(Team);
					}
			
					alien.move(Team, blocked);
			
					for(int i = 0; i < Team.size(); i++) {
						System.out.println("x:" + Team.get(i).Posx + " " + alien.Posx);
						System.out.println("y:" + Team.get(i).Posy + " " + alien.Posy);
						if(alien.Posx == Team.get(i).Posx && alien.Posy == Team.get(i).Posy) {
						Team.remove(i);
						}
					}
				}
				
				Path[0] = new Rectangle(Team.get(turn).Posx*tileWidth, Team.get(turn).Posy*tileWidth, 32, 32);	
				
			}else {
				
				Move(delta);
				attack = true;
			}
			
					
		}
		
		if(input.isKeyPressed(Input.KEY_SPACE)) {
			if(attack) {
				attack = false;
			}else {
				attack = true;
			}
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
		for(int i = 0; i < Team.size(); i++){
			if(Team.get(i).Posx == x && Team.get(i).Posy == y) {
				return false;
			}
		}
		
		return true;	
	}
	
	private void Move(int delta) {
		if(moveCheck(Currentx/tileWidth, Currenty/tileWidth)) {
			
			System.out.println("Move: "+ Move[0] + "Speed:" + Speed);
			Team.get(turn).move(Move);
			
		}else{
			error.play();
		}
		
		for(int j = 0; j < 6; j++) {
			Move[j] = 4;
		}
		
		Path[1] = null;
		Path[2] = null;
		Path[3] = null;
		Path[4] = null;
		Speed = 0;		
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
