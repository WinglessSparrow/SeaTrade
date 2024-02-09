package Ship.BusinessLogic;

import Types.Cargo;
import Types.Company;
import Database.DB;
import Types.Ship;
import Logger.Logger;
import Ship.API.ShipAPI;

import java.awt.*;
import java.io.Closeable;
import java.io.IOException;

public class ShipController implements Closeable {

    private final DB db;
    private final ShipAPI api;

    public ShipController(DB db, int port) {
        this.db = db;

        api = new ShipAPI(this, port);

        Logger.log("Starting SHIP API", this);

        api.start();
    }

    public Ship getShip(String shipId) {
        return db.getShip().get(shipId);
    }

    public synchronized Ship removeShip(String shipId) {
        return db.getShip().delete(shipId);
    }

    public Ship addNewShip(Ship ship) {

        synchronized (new Object()) {
            db.getShip().add(ship);
        }

        return db.getShip().get(ship.name());
    }

    public Ship moveShip(String shipName, Point point, int cost) {
        final var company = db.getCompany().get();
        final var ship = db.getShip().get(shipName);

        synchronized (new Object()) {
            db.startTransaction();

            db.getShip().update(new Ship(ship, point));

            db.getCompany().update(new Company(company, company.deposit() - cost));

            db.commitTransaction();
        }

        return db.getShip().get(shipName);
    }

    public Ship registerCargoLoad(String shipName, String cargoId) {
        final var currShip = db.getShip().get(shipName);
        final var cargo = db.getCargo().get(cargoId);

        synchronized (new Object()) {
            db.getShip().update(new Ship(currShip, cargo));
        }

        return db.getShip().get(shipName);
    }

    public Ship registerCargoUnload(String shipName, int profit) {
        final var currShip = db.getShip().get(shipName);
        final var company = db.getCompany().get();

        synchronized (new Object()) {
            db.startTransaction();

            db.getShip().update(new Ship(currShip, (Cargo) null));
            db.getCompany().update(new Company(company, company.deposit() + profit));

            db.commitTransaction();
        }

        return db.getShip().get(shipName);
    }

    @Override
    public void close() throws IOException {
        api.close();
    }
}
