package DataClasses;

import java.awt.Point;

public class Ship {

    private int id = 0;
    private String name = null;
    private Point pos = null;
    private Direction dir = null;
    private Harbour harbour = null;
    private Cargo heldCargo = null;


    public void set(int id, String name, Point pos, Direction dir, Harbour harbour) {

        this.id = id;
        this.name = name;
        this.pos = pos;
        this.dir = dir;
        this.harbour = harbour;

    }

    public Ship get() {
        return this;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Point getPos() {
        return pos;
    }

    public Direction getDir() {
        return dir;
    }

    public Harbour getHarbour() {
        return harbour;
    }

    public Cargo getCargo() {
        return heldCargo;
    }

    public void set(Point pos, Direction dir) {

        this.pos = pos;
        this.dir = dir;

    }

    public void set(Harbour harbour) {

        this.harbour = harbour;

    }

    public void set(Cargo heldCargo) {

        this.heldCargo = heldCargo;

    }

}
