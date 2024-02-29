package API;

import java.awt.Point;

import com.fasterxml.jackson.core.JsonProcessingException;

import DataClasses.Direction;
import DataClasses.Harbour;
import DataClasses.Ship;

public class ShipController {

    private CompanyAPI company = null;
    private Ship ship = null;

    private final SeaTradeAPI api;

    public ShipController(CompanyAPI company, Ship ship, int port, String host) {
        this.company = company;
        this.ship = ship;

        this.api = new SeaTradeAPI(this, host, port);
    }

    public void onLaunch(Direction dir, Point pos, int cost) throws JsonProcessingException {

        company.notifyLaunch(ship.getName(), pos, dir, cost);
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

    public void updateShip(Ship ship) {

        this.ship.set(ship.getId());
        this.ship.set(ship.getName());
        this.ship.set(ship.getPos(), ship.getDir());
        this.ship.set(ship.getHarbour());
        this.ship.set(ship.getCargo());

    }

    public SeaTradeAPI getApi() {
        return api;
    }
}
