package congenialspace.common;

import java.io.FileNotFoundException;

import org.newdawn.slick.SlickException;

public interface LevelSettings {
	Turret[] level() throws FileNotFoundException, SlickException;
}
