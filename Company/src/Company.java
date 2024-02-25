import Database.*;
import Logger.Logger;
import SeaTrade.BusinessLogic.SeaTradeController;
import Ship.BusinessLogic.ShipController;
import Web.Controller.WebController;
import Web.Server.WebServer;

import java.io.IOException;
import java.util.Scanner;

public class Company {

    private static boolean running = true;

    public static void main(String[] args) {

        var db = new DB();

        db.clearDB();

        var webController = new WebController(db);

        try (var webServer = new WebServer(webController);
             var shipController = new ShipController(db, 8080);
             var seaTradeController = new SeaTradeController(db, "SeaTrade", 8081)
        ) {
            seaTradeController.init();

            var scanner = new Scanner(System.in);

            while (running) {
                running = !scanner.nextLine().equals("shutdown");
            }

        } catch (IOException e) {
            Logger.logErr("oopsie woopsie, everything broke!", Company.class);
            throw new RuntimeException(e);
        }

        Logger.logGreen("See you space cowboy...", Company.class);

    }
}