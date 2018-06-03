package congenialspace.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.newdawn.slick.SlickException;

public enum Level implements LevelSettings {
    EMPTY, INTEGRATIONTEST, LEVEL_1, LEVEL_2;

    @Override
    public Turret [] level() throws FileNotFoundException, SlickException {
        return readFromFile(this.name() + ".level");
    }

    @SuppressWarnings("resource")
	private static Turret [] readFromFile(String filename) throws FileNotFoundException, SlickException {
    	    	
    	Scanner scanner = new Scanner(new File("rsc/" + filename));
    	int [] tall = new int [5];
    	
    	
    	String line;
    	int n = 0;
    	
	    line = scanner.nextLine();
	    int turretNum = Integer.parseInt(line);

    	Turret [] turrets =  new Turret[turretNum];
    	
    	while (scanner.hasNextLine()) {
    	    line = scanner.nextLine();
    	    if (line.isEmpty()) {
    	        break;
    	    }
    	    
    	    String[] integerStrings = line.split(" ");
    	    
    	    for (int i = 0; i < tall.length; i++){
    	        tall[i] = Integer.parseInt(integerStrings[i]); 
    	    }
    	    
    	    turrets[n] = new Turret(tall);
    	    //System.out.println("N: " +n);
    	    n++;
    	}
    	
    	
    	return turrets;
    }
}
