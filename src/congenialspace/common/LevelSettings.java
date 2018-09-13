package congenialspace.common;

import java.io.FileNotFoundException;
import java.util.LinkedList;

import org.newdawn.slick.SlickException;

public interface LevelSettings {
	LinkedList<Turret> level() throws FileNotFoundException, SlickException;
}
