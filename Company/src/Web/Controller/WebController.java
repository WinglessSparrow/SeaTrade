package Web.Controller;

import Database.DB;
import Database.DBGetter;
import Logger.Logger;
import Ship.API.ShipAPI;
import Web.API.WebAPI;
import Web.DTO.DBDump;

public class WebController {

    private final DB db;


    public WebController(DB db) {
        this.db = db;

        Logger.log("Starting Web API", this);

        var webAPI = new WebAPI(this, 8081);

        webAPI.start();
    }

    public DBDump dumbDBData() {
        return db.getGetter().dumpDB();
    }
}
