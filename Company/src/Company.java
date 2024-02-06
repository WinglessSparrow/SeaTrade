import Database.*;
import SeaTrade.BusinessLogic.SeaTradeController;
import Ship.BusinessLogic.ShipController;
import Web.Controller.WebController;


public class Company {
    public static void main(String[] args) {
        var db = new DB();
        var seaTradeController = new SeaTradeController(db, "SeaTrade");

        seaTradeController.init();

        var shipController = new ShipController(db);
        var webController = new WebController(db);
    }
}