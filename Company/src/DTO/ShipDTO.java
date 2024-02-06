package DTO;

import java.awt.*;

public record ShipDTO(
        String name,
        Point pos,
        Direction dir,
        HarbourDTO harbour,
        CargoDTO heldCargo
) {
    public ShipDTO(ShipDTO ship, Point newPos) {
        this(ship.name, newPos, ship.dir, ship.harbour, ship.heldCargo);
    }

    public ShipDTO(ShipDTO ship, CargoDTO cargo) {
        this(ship.name, ship.pos, ship.dir, ship.harbour, cargo);
    }
}
