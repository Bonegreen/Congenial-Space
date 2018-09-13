package congenialspace.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.SlickException;

public enum Level implements LevelSettings {
    EMPTY, INTEGRATIONTEST, LEVEL_1, LEVEL_2;

    @Override
    public LinkedList<Turret> level() throws FileNotFoundException, SlickException {
        return readFromFile(this.name() + ".level");
    }

    @SuppressWarnings("resource")
	private static LinkedList<Turret> readFromFile(String filename) throws FileNotFoundException, SlickException {
    	    	
    	Scanner scanner = new Scanner(new File("rsc/" + filename));
    	int [] tall = new int [5];
    	
    	
    	String line;
    	int n = 0;
    	
	    line = scanner.nextLine();
	    int turretNum = Integer.parseInt(line);

    	LinkedList<Turret> turrets = new LinkedList<Turret>();
    	
    	while (scanner.hasNextLine()) {
    	    line = scanner.nextLine();
    	    if (line.isEmpty()) {
    	        break;
    	    }
    	    
    	    String[] integerStrings = line.split(" ");
    	    
    	    for (int i = 0; i < tall.length; i++){
    	        tall[i] = Integer.parseInt(integerStrings[i]); 
    	    }
    	    
    	    turrets.add(new Turret(tall));
    	    //System.out.println("N: " +n);
    	    n++;
    	}
    	
    	
    	return turrets;
    }
}
