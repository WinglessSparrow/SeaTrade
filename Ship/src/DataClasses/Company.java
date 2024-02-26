package DataClasses;

public class Company {

	private int id = 0;
	private String name = null;
	private int deposit = 0;
	private int height = 0;
	private int width = 0;
	
	public Company get() {
		return this;
	}
	
	public void set(int id, String name, int deposit, int height, int width) {
		
		this.id = id;
		this.name = name;
		this.deposit = deposit;
		this.height = height;
		this.width = width;
			
	}
	
	public void set(int deposit) {
		
		this.deposit = deposit;
		
	}
	
}
