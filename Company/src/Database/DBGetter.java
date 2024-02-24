package Database;

import Types.*;

public class DBGetter {

    private final DB db;

    public DBGetter(DB db) {
        this.db = db;
    }

    public DBDump dumpDB() {
        return new DBDump(db.getCompany().get(), db.getShip().getAllShips(), db.getCargo().getAllCargo(), db.getHarbour().getAllHarbours());
    }
}
