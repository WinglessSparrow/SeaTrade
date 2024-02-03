package Ship.BusinessLogic;

import DTO.CompanyDTO;
import Database.DB;
import DTO.ShipDTO;
import Logger.Logger;
import Ship.API.ShipAPI;

import java.awt.*;

public class ShipController {

    private final ShipAPI api;
    private final DB db;


    public ShipController(DB db) {
        this.api = new ShipAPI(this, 8080);
        this.db = db;

        Logger.log("Starting SHIP API", this);

        api.start();
    }

    public ShipDTO getShip(String shipId) {
        return db.getShip().get(shipId);
    }

    public ShipDTO removeShip(String shipId) {
        return db.getShip().delete(shipId);
    }

    public ShipDTO addNewShip(ShipDTO ship) {
        db.getShip().add(ship);

        return db.getShip().get(ship.name());
    }

    public ShipDTO moveShip(String shipName, Point point, int cost) {
        final var company = db.getCompany().get();
        final var ship = db.getShip().get(shipName);

        db.startTransaction();

        db.getShip().update(new ShipDTO(ship, point));

        int newDeposit = company.deposit() - cost;

        db.getCompany().update(new CompanyDTO(company, newDeposit));

        db.commitTransaction();

        return db.getShip().get(shipName);
    }

    public ShipDTO registerCargoLoad(String shipName, String cargoId) {
        var currShip = db.getShip().get(shipName);
        final var cargo = db.getCargo().get(cargoId);

        final var newCargoShip = new ShipDTO(currShip, cargo);

        currShip = db.getShip().get(shipName);

        return currShip;
    }

    public ShipDTO registerCargoUnload(String shipName, int profit) {
        return db.getShip().get("");
    }

}
