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

    public Ship getShip(int shipId) {
        return db.getShip().get(shipId);
    }

    public Ship removeShip(int shipId) {
        var lastShipState = db.getShip().get(shipId);

        synchronized (this) {
            db.getShip().delete(shipId);
        }

        return lastShipState;
    }

    public Ship addNewShip(Ship ship) {

        var company = db.getCompany().get();

        synchronized (this) {
            db.getShip().add(ship, company.id());
        }

        return db.getShip().getByName(ship.name());
    }

    public Ship moveShip(int shipId, Point point, int cost) {
        final var company = db.getCompany().get();
        final var ship = db.getShip().get(shipId);

        synchronized (this) {
            db.startTransaction();

            db.getShip().update(new Ship(ship, point));

            db.getCompany().update(new Company(company, company.deposit() - cost));

            db.commitTransaction();
        }

        return db.getShip().get(shipId);
    }

    public Ship registerCargoLoad(int shipId, int cargoId) {
        final var currShip = db.getShip().get(shipId);
        final var cargo = db.getCargo().get(cargoId);

        synchronized (this) {
            db.getShip().update(new Ship(currShip, cargo));
        }

        return db.getShip().get(shipId);
    }

    public Ship registerCargoUnload(int shipId, int profit) {
        final var currShip = db.getShip().get(shipId);
        final var company = db.getCompany().get();

        synchronized (this) {
            db.startTransaction();

            db.getShip().update(new Ship(currShip, (Cargo) null));
            db.getCompany().update(new Company(company, company.deposit() + profit));

            db.commitTransaction();
        }

        return db.getShip().get(shipId);
    }

    @Override
    public void close() throws IOException {
        api.close();
    }
}
