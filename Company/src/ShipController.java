import Database.Database;
import DTO.Ship;

public class ShipController {

    private ShipAPI api;
    private Database db;


    ShipController(Database db) {
        this.api = new ShipAPI(this, 8080);
        this.db = db;

        api.start();
    }

    public void removeShip(Ship ship) {
        db.getShip().delete(ship);
    }

    public void addNewShip(Ship ship) {
        db.getShip().add(ship);
    }

    public void moveShip(Ship ship, int cost) {
    }

    public void registerCargoLoad(Ship ship, String cargoId) {

    }

    public void registerCargoUnload(Ship ship, int profit) {

    }

}
