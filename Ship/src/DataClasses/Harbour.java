package DataClasses;
import java.awt.Point;

public class Harbour {

	private int id = 0;
	private String name = null;
	private Point pos = null;
	
	public Harbour get() {
		return this;
	}
	
	public void set(int id, String name, Point pos) {
		
		this.id = id;
		this.name = name;
		this.pos = pos;
		
	}
	
}
