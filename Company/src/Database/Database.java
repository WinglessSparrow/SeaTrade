package Database;

import DTO.Ship;

public class Database {


    private DBShip ship = new DBShip();

    public void clearDB() {

    }

    public DBShip getShip() {
        return ship;
    }
}
