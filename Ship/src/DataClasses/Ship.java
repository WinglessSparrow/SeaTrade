package DataClasses;
import java.awt.Point;
import java.io.IOException;
import java.net.UnknownHostException;

import API.CompanyAPI;
import API.ShipController;

public class Ship {

	private int id = 0;
	private String name = null;
	private Point pos = null;	
	private Direction dir = null;
	private Harbour harbour = null;
	private Cargo heldCargo = null;
	
	public Ship() {
		
		
		
	}
	
	public void createApi(int cPort, String cHost, int sPort, String sHost) throws UnknownHostException, IOException {
		
		CompanyAPI company = new CompanyAPI(cHost, cPort);
		ShipController controller = new ShipController(company, this, sPort, sHost);
		company.setController(controller);
		
	}
	
	public Ship get() {
		return this;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Point getPos() {
		return pos;
	}
	
	public Direction getDir() {
		return dir;
	}
	
	public Harbour getHarbour() {
		return harbour;
	}
	
	public Cargo getCargo() {
		return heldCargo;
	}	
	
	public void set(int id) {
		
		this.id = id;
		
	}
	
	public void set(String name) {

		this.name = name;
		
	}		
		
	public void set(Point pos, Direction dir) {
		
		this.pos = pos;
		this.dir = dir;
		
	}
	
	public void set(Harbour harbour) {
		
		this.harbour = harbour;
		
	}
	
	public void set(Cargo heldCargo) {
		
		this.heldCargo = heldCargo;
		
	}
	
}
