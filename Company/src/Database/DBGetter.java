package Database;

import Types.*;

import java.awt.*;

public class DBGetter {
    public DBDump dumpDB() {

        var ships = new Ship[]{new Ship("Titanic", new Point(0, 0), Direction.DOWN, null, null), new Ship("Olympic", new Point(0, 1), Direction.DOWN, null, null), new Ship("USS Gerald R. Ford", new Point(1, 0), Direction.DOWN, null, null),};

        var harbours = new Harbour[]{new Harbour(0, "New York", new Point(0, 0)), new Harbour(1, "Boston", new Point(1, 1)), new Harbour(2, "Tallinn", new Point(2, 3))};

        var cargos = new Cargo[]{new Cargo(harbours[0], harbours[1], 0, 10), new Cargo(harbours[2], harbours[0], 1, 100),};


        return new DBDump(new Company("Sea Trade Company", 1000, new Dimension(20, 20), 0), ships, cargos, harbours);
    }
}
