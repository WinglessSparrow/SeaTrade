package Web.Controller;

import Database.DB;
import Types.DBDump;

public class WebController {

    private final DB db;

    public WebController(DB db) {
        this.db = db;
    }

    public DBDump dumpDBData() {
        return db.getGetter().dumpDB();
    }
}
