package Database;

import Types.Direction;
import Types.Ship;

import java.awt.*;

public class DBShip {
    public void update(Ship ship) {
    }

    public void add(Ship ship) {

    }

    public Ship delete(String shipId) {
        return new Ship("Name", new Point(0, 0), Direction.DOWN, null, null);
    }

    public Ship get(String name) {
        return new Ship("Name", new Point(0, 0), Direction.DOWN, null, null);
    }

}
