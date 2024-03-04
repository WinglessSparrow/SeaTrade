import java.awt.Point;
import java.io.IOException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;

import DataClasses.Direction;
import DataClasses.Harbour;
import DataClasses.Ship;

public class ShipController {

    private CompanyAPI company = null;
    private Ship ship = null;
    private SeaTradeAPI seatrade = null;

    public ShipController(CompanyAPI company, Ship ship, int port, String host) throws UnknownHostException, IOException {
        this.company = company;
        this.ship = ship;
        seatrade = new SeaTradeAPI(this, port, host);
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

//	public void launch(Harbour harbour, String name, String companyName) {
//
//		seatrade.launch(harbour, name, companyName);
//		
//    }
//
//    public void load() {
//
//        seatrade.load();
//
//    }
//
//    public void unload() {
//
//        seatrade.unload();
//
//    }
//
//    public void exit() {
//    	
//    	seatrade.exit();
//
//    }
//
//    public void moveTo(String harbourName) {
//
//        seatrade.moveTo(harbourName);
//
//    }

}
