package Types;

import java.awt.*;

public record Ship(String name, int id, Point pos, Direction dir, Harbour harbour, Cargo heldCargo) {
    public Ship(Ship ship, Point newPos, Direction newDir) {
        this(ship.name, ship.id, newPos, newDir, ship.harbour, ship.heldCargo);
    }

    public Ship(Ship ship, Cargo cargo) {
        this(ship.name, ship.id, ship.pos, ship.dir, ship.harbour, cargo);
    }

    public Ship(Ship ship, Harbour harbour) {
        this(ship.name, ship.id, ship.pos, ship.dir, harbour, ship.heldCargo);
    }
}
