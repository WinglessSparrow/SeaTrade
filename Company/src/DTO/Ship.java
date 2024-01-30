package DTO;

import java.awt.geom.Point2D;

public record Ship(String name, Point2D pos, Direction dir, Harbour harbour, Cargo heldCargo) { }
