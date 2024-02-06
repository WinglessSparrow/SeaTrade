package SeaTrade.BusinessLogic;

import DTO.CargoDTO;
import DTO.CompanyDTO;
import DTO.HarbourDTO;
import Database.DB;
import SeaTrade.API.SeaTradeAPI;

public class SeaTradeController {
    private final DB db;

    private final String companyName;

    public SeaTradeController(DB db, String companyName) {
        this.db = db;
        this.companyName = companyName;

//        new SeaTradeAPI(this).start();
    }

    public void addNewCargo(CargoDTO cargo) {

    }

    public void setCargos(CargoDTO[] cargos) {

    }

    public void setHarbours(HarbourDTO[] harbours) {

    }

    public void setCompany(CompanyDTO company) {

    }

    public String getCompanyName() {
        return companyName;
    }
}
