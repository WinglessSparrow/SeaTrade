import DTO.CompanyDTO;
import Database.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Company {

    public static void main(String[] args) {
        var db = new Database();
        var shipController = new ShipController(db);


        //JACKSON in use
        var rec = new CompanyDTO("SeaTrade", 2, 2, 0);

        var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {

            String json = writer.writeValueAsString(rec);

            System.out.println(json);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

}