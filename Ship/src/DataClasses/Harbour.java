package DataClasses;

import java.awt.Point;

public class Harbour {
    private int id = 0;
    private String name = null;
    private Point pos = null;

    public Harbour(int id, String name, Point pos) {
        this.id = id;
        this.name = name;
        this.pos = pos;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPos(Point pos) {
        this.pos = pos;
    }
}
