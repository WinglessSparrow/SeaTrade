package Database;

import Types.*;

import java.awt.*;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBGetter {

    private final DB db;

    public DBGetter(DB db) {
        this.db = db;
    }

    public DBDump dumpDB() {
        return new DBDump(
                db.getCompany().get(),
                db.getShip().getAllShips(),
                db.getCargo().getAllCargo(),
                db.getHarbour().getAllHarbours()
        );
    }
}
