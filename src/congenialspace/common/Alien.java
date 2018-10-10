package congenialspace.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Alien extends Actor {

	SpriteSheet AlienSheet;
	Image Idle;
	int tileWidth = 32;
	int speed;
	
	int pathLength = 0;
	
	int ArrayLength = 2;
	
	Node Target;
	
	LinkedList<Node> Open;
	LinkedList<Node> Closed;
	LinkedList<Node> Neighbours;
	LinkedList<Node> Path;
	
	Node Current;
	
	int f1, f2 = 0;
	
	Character c = null;
	
	private static boolean[][] Terrain;

	
	public Alien(int[] Level, boolean[][] Terrain) throws SlickException {
		super(Level[0], Level[1], Level[2], Level[3], Level[4]);
		this.Terrain = Terrain;
		this.Posx = Level[0];
		this.Posy = Level[1];
		this.speed = Level[2];
		
		AlienSheet = new SpriteSheet("rsc/Alien_Spritesheet.png", 32, 32);
		Idle = AlienSheet.getSprite(Level[4],0);
		
		Open = new LinkedList<Node>();
		Closed = new LinkedList<Node>();
		Neighbours = new LinkedList<Node>();
		Path = new LinkedList<Node>();
	}
	
	public void render(GameContainer gc, Graphics g) throws SlickException{
		g.drawImage(Idle, Posx*tileWidth, Posy*tileWidth);
		
		
		
	}
	
	public void move(LinkedList<Character> Chars, boolean[][] Terrain) {
		
		Open.clear();
		Closed.clear();
		Neighbours.clear();
		Path.clear();
		
		pathLength = 0;
		
		if(Chars.size() > 1) {
			getTarget(Chars, Terrain);
		}else {
			Target = new Node(new int[]{Chars.get(0).Posx, Chars.get(0).Posy});
		}
		
		//System.out.println("x: " + Target[0]);
		//System.out.println("y: " + Target[1]);
		
		//Open.add(Target);
		System.out.println("Start");
		
		Current = new Node(new int[]{Posx, Posy});
		f2 = 0;
		
		Open.add(Current);
		//System.out.println("Current: " + Current.Loc[0] + " " + Current.Loc[1]);
		
		PathFind(Terrain);
		
	}
	
	//Finds the closest Character and sets it's location to Target
	public void getTarget(LinkedList<Character> Chars, boolean[][] Terrain) {
		
		Integer[] Distance = new  Integer[Chars.size()];
		Integer[] DistanceMark = new  Integer[Chars.size()];
		
		for(int i = 0; i < Chars.size(); i++) {
			c = Chars.get(i);
			Distance[i] = Math.abs(Posx - c.Posx) + Math.abs(Posy - c.Posy);
		}
		
		DistanceMark =  Distance;
		Arrays.sort(Distance, Collections.reverseOrder());
		
		for(int i = 0; i < Chars.size(); i++) {
			if(DistanceMark[i] == Distance[0]) {
				c = Chars.get(i);
				break;
			}
		}
		
		Target = new Node(new int[]{c.Posx, c.Posy});
	}
	
	public void PathFind(boolean[][] Terrain) {
		
		while(!Open.isEmpty()) {
			
			//System.out.println("Run");
			
			if(f2 != 0) {
				Current = Open.get(0);
			}
			
			//System.out.println("OPEN: " + Open.size());
			
			for(int i = 0; i < Open.size(); i++) {
				
				Node temp = Open.get(i);
				
				f1 = temp.f;
				
				//System.out.println("F1 " + f1);
				//System.out.println("F2 " + f2);

				
				if(f2 != 0) {
					if(f1 < f2) {
						Current = Open.get(i);
						//System.out.println(" " + temp);
					}
				}
				
				f2 = temp.f;	
				
				//System.out.println("F1 " + f1);
				//System.out.println("F2 " + f2);

			}
			
			
			if(CheckID(Current, Target)) {
				//Path Found
				//System.out.println("Done!");
				GetPath();
				return;
			}
			
			if(!CheckID(Open, Current)) {
				Open.remove(Current);
				//System.out.println("REMOVE");
			}
			
			Closed.add(Current);
			
			CreateNeighbours();
						
			//System.out.println("NEIGH: " + Neighbours.size());

			for(int i = 0; i < Neighbours.size(); i++) {
				
				Node n = Neighbours.get(i);
				if(CheckID(Closed, n)) {
				
					n.f = n.g + (Math.abs(Target.Loc[0] - n.Loc[0]) + Math.abs(Target.Loc[1] - n.Loc[1]));
					if(CheckID(Open, n)) {
						Open.add(n);
						//System.out.println("NEIGH");
					}else {
						
					}
					
				}
				
				
			}
			//System.out.println("END");
		}
	}
	
	

	private boolean CheckID(Node current2, Node target2) {
		if(current2.Loc[0] == target2.Loc[0] && current2.Loc[1] == target2.Loc[1]) {
			return true;
		}
		
		return false;
	}

	private boolean CheckID(LinkedList<Node> closed2, Node n) {
		
		for(int i = 0; i < closed2.size(); i++) {
			if(closed2.get(i).Loc[0] == n.Loc[0] && closed2.get(i).Loc[1] == n.Loc[1]) {
				return false;
			}
		}
		
		return true;
	}

	public void CreateNeighbours(){
		
		Neighbours.clear();
		
		if(!isBlocked(new Node(ADD(Current.Loc, new int[]{0, -1})).Loc)) {
			Neighbours.add(new Node(ADD(Current.Loc, new int[]{0, -1})));
		}
		if(!isBlocked(new Node(ADD(Current.Loc, new int[]{0, 1})).Loc)) {
			Neighbours.add(new Node(ADD(Current.Loc, new int[]{0, 1})));
		}
		if(!isBlocked(new Node(ADD(Current.Loc, new int[]{-1, 0})).Loc)) {
			Neighbours.add(new Node(ADD(Current.Loc, new int[]{-1, 0})));
		}
		if(!isBlocked(new Node(ADD(Current.Loc, new int[]{1, 0})).Loc)) {
			Neighbours.add(new Node(ADD(Current.Loc, new int[]{1, 0})));
		}
		
		
		for(int i = 0; i < Neighbours.size(); i++) {		
			Neighbours.get(i).g = Current.g + 1;
			Neighbours.get(i).Parent = Current;
		}
		
		//Set g score
		//Set Current as parent node
	}
	
	private void GetPath() {
		
		while(Current != null) {
			
			Path.add(Current);
			//System.out.println("Path: " + Current.Loc[0] + " " + Current.Loc[1]);
			pathLength += 1;
		
			Current = Current.Parent;
		}
		
		Iterator<Node> i = Path.descendingIterator();
		
		for(int t = 0; t < speed; t++) {
			if(t < pathLength) {
				int[] lot = i.next().Loc;
				Posx = lot[0];
				Posy = lot[1];
			}	
		}		
	}

	private int[] ADD(int[] Cur, int[] Dir) {
		
		//System.out.println("Cur: " + Cur[0] + " " + Cur[1]);
		
		int[] Add = new int[ArrayLength];
		for(int i = 0; i < ArrayLength; i++) {
			Add[i] = Cur[i] + Dir[i];
		}		
		
		//System.out.println("LOCA: " + Add[0] + " " + Add[1]);
		return Add;
	}
	
	static boolean isBlocked(int[] Loc)
    {
        int xBlock = (int)Loc[0] / 32;
        int yBlock = (int)Loc[1] / 32;
        return Terrain[xBlock][yBlock];
    }
}
