package Main;

import Database.DB;
import SeaTrade.BusinessLogic.SeaTradeController;
import Ship.BusinessLogic.ShipController;
import Web.Controller.WebController;
import Web.Server.WebServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class CompanyController {

    private final DB db = new DB();
    private final WebServer webServer;
    private final ShipController shipController;
    private final SeaTradeController seaTradeController;

    public CompanyController(InetSocketAddress seaTradeAddr, int shipApiPort, String companyName) {
        webServer = new WebServer(new WebController(db), new InetSocketAddress("localhost", 9000));
        shipController = new ShipController(db, shipApiPort);
        seaTradeController = new SeaTradeController(db, companyName, seaTradeAddr);
    }

    public void launch() {
        db.clearDB();

        seaTradeController.init();
        shipController.launch();
        webServer.launch();
    }

    public void restart() {
        shutdown();

        launch();
    }

    public void shutdown() {
        try {
            webServer.close();
            shipController.close();
            seaTradeController.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
