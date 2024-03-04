package API;

import java.awt.Point;
import java.io.Closeable;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import DataClasses.Direction;
import DataClasses.Harbour;
import DataClasses.Ship;

public class ShipController implements Closeable {

    private CompanyAPI company = null;
    private Ship ship = null;

    private String[] harbours = new String[0];

    private final SeaTradeAPI api;

    public ShipController(CompanyAPI company, Ship ship, int port, String host) {
        this.company = company;
        this.ship = ship;

        this.api = new SeaTradeAPI(this, host, port);
    }

    public void onLaunch(Direction dir, Point pos, int cost) throws JsonProcessingException {

        company.notifyLaunch(ship.getName(), pos, dir, cost, ship.getHarbour().getName());
    }

    public void onMoved(Direction dir, Point pos, int cost) throws JsonProcessingException {
        company.notifyMoved(ship.getId(), pos, dir, cost);
    }

    public void onReached(String harbourName) throws JsonProcessingException {
        System.out.println("Reached: " + harbourName);
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

        this.ship.setId(ship.getId());
        this.ship.setName(ship.getName());
        this.ship.setPosDirection(ship.getPos(), ship.getDir());
        this.ship.setHarbour(ship.getHarbour());
        this.ship.setHeldCargo(ship.getHeldCargo());

    }

    public SeaTradeAPI getApi() {
        return api;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public void launch(Harbour harbour, String name, String companyName) {
        api.launch(harbour, name, companyName);
    }

    public void load() {
        api.load();
    }

    public void unload() {
        api.unload();
    }

    public void exit() {
        api.exit();
    }

    public void moveTo(String harbourName) {
        api.moveTo(harbourName);
    }


    public void updateHarbours() {
        try {
            company.getHarbours();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void setHarbours(String[] harbours) {
        this.harbours = harbours;
    }

    public String[] getHarbours() {
        return harbours;
    }

    public Ship getShip() {
        return ship;
    }

    public CompanyAPI getCompanyApi() {
        return company;
    }

    @Override
    public void close() throws IOException {
        api.close();
        company.close();
    }
}
