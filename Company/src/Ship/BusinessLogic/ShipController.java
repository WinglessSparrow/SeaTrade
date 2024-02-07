package Ship.BusinessLogic;

import DTO.CargoDTO;
import DTO.CompanyDTO;
import Database.DB;
import DTO.ShipDTO;
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

    public ShipDTO getShip(String shipId) {
        return db.getShip().get(shipId);
    }

    public synchronized ShipDTO removeShip(String shipId) {
        return db.getShip().delete(shipId);
    }

    public ShipDTO addNewShip(ShipDTO ship) {

        synchronized (new Object()) {
            db.getShip().add(ship);
        }

        return db.getShip().get(ship.name());
    }

    public ShipDTO moveShip(String shipName, Point point, int cost) {
        final var company = db.getCompany().get();
        final var ship = db.getShip().get(shipName);

        synchronized (new Object()) {
            db.startTransaction();

            db.getShip().update(new ShipDTO(ship, point));

            db.getCompany().update(new CompanyDTO(company, company.deposit() - cost));

            db.commitTransaction();
        }

        return db.getShip().get(shipName);
    }

    public ShipDTO registerCargoLoad(String shipName, String cargoId) {
        final var currShip = db.getShip().get(shipName);
        final var cargo = db.getCargo().get(cargoId);

        synchronized (new Object()) {
            db.getShip().update(new ShipDTO(currShip, cargo));
        }

        return db.getShip().get(shipName);
    }

    public ShipDTO registerCargoUnload(String shipName, int profit) {
        final var currShip = db.getShip().get(shipName);
        final var company = db.getCompany().get();

        synchronized (new Object()) {
            db.startTransaction();

            db.getShip().update(new ShipDTO(currShip, (CargoDTO) null));
            db.getCompany().update(new CompanyDTO(company, company.deposit() + profit));

            db.commitTransaction();
        }

        return db.getShip().get(shipName);
    }

    @Override
    public void close() throws IOException {
        api.close();
    }
}
