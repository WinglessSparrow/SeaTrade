package Types;

import java.awt.*;

public record Ship(
        String name,
        Point pos,
        Direction dir,
        Harbour harbour,
        Cargo heldCargo
) {
    public Ship(Ship ship, Point newPos) {
        this(ship.name, newPos, ship.dir, ship.harbour, ship.heldCargo);
    }

    public Ship(Ship ship, Cargo cargo) {
        this(ship.name, ship.pos, ship.dir, ship.harbour, cargo);
    }
}
