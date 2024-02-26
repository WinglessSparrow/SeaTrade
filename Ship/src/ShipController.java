import java.awt.Point;

import com.fasterxml.jackson.core.JsonProcessingException;

import DataClasses.Direction;
import DataClasses.Harbour;
import DataClasses.Ship;

public class ShipController {
	
	private CompanyAPI company = null;
	private Ship ship = null;
	
	public ShipController(CompanyAPI company, Ship ship) {
		this.company = company;
		this.ship = ship;
	}
	
	public void onLaunch(Direction dir, Point pos, int cost) throws JsonProcessingException {
		
		company.notifyLaunch(ship.getId(), ship.getName(), pos, dir, cost);
	}

	public void onMoved(Direction dir, Point pos, int cost) throws JsonProcessingException {
		company.notifyMoved(ship.getId(), pos, dir, cost);
	}
	
	public void onReached(String harbourName) throws JsonProcessingException {
		company.notifyReached(ship.getId(), harbourName);
	}

	public void onLoad(int cargoId) throws JsonProcessingException {
		company.notifyLoad(ship.getId(), cargoId);
	}
	
	public void onUnload(int profit) throws JsonProcessingException {
		company.notifyUnload(ship.getId(), profit);
	}	
	
	public void onExit() throws JsonProcessingException {
		company.notifyExit(ship.getId());
	}
	
}
