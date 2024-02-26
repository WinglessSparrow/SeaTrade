package DataClasses;

public class Cargo {

	private int id = 0;
	private Harbour dest = null;
	private Harbour src = null;
	private int value = 0;
	
	public Cargo get() {
		return this;
	}
	
	public void set(int id, Harbour dest, Harbour src, int value) {
		
		this.id = id;
		this.dest = dest;
		this.src = src;
		this.value = value;
		
	}
	
}
