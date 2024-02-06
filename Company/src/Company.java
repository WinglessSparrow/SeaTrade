import Database.*;
import Ship.BusinessLogic.ShipController;
import Web.Controller.WebController;


public class Company {
    public static void main(String[] args) {
        var db = new DB();
        var shipController = new ShipController(db);
        var webController = new WebController(db);
    }
}