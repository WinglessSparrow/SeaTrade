package Database;

import DTO.Direction;
import DTO.ShipDTO;

import java.awt.*;

public class DBShip {
    public void update(ShipDTO ship) {
    }

    public void add(ShipDTO ship) {

    }

    public ShipDTO delete(String shipId) {
        return new ShipDTO("Name", new Point(0, 0), Direction.DOWN, null, null);
    }

    public ShipDTO get(String name) {
        return new ShipDTO("Name", new Point(0, 0), Direction.DOWN, null, null);
    }

}
