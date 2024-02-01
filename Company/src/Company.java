import DTO.CompanyDTO;
import Database.*;
import Ship.BusinessLogic.ShipController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Company {

    public static void main(String[] args) {
        var db = new DB();
        var shipController = new ShipController(db);
    }

}