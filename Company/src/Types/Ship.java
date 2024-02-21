package Types;

import java.awt.*;

public record Ship(
        String name,
        int id,
        Point pos,
        Direction dir,
        Harbour harbour,
        Cargo heldCargo
) {
    public Ship(Ship ship, Point newPos) {
        this(ship.name, ship.id, newPos, ship.dir, ship.harbour, ship.heldCargo);
    }

    public Ship(Ship ship, Cargo cargo) {
        this(ship.name, ship.id, ship.pos, ship.dir, ship.harbour, cargo);
    }
}
