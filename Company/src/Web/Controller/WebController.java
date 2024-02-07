package Web.Controller;

import Database.DB;
import Web.DTO.DBDump;

public class WebController {

    private final DB db;

    public WebController(DB db) {
        this.db = db;
    }

    public DBDump dumbDBData() {
        return db.getGetter().dumpDB();
    }
}
