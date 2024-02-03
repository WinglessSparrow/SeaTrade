import DTO.CompanyDTO;
import Database.*;
import Ship.BusinessLogic.ShipController;

public class Company {

    public static void main(String[] args) {
        var db = new DB();
        var shipController = new ShipController(db);
    }
}