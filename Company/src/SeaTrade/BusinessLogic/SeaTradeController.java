package SeaTrade.BusinessLogic;

import DTO.CargoDTO;
import DTO.CompanyDTO;
import DTO.HarbourDTO;
import Database.DB;
import SeaTrade.API.SeaTradeAPI;

public class SeaTradeController {
    private final DB db;

    private final String companyName;

    private final SeaTradeAPI api;

    public SeaTradeController(DB db, String companyName) {
        this.db = db;
        this.companyName = companyName;

        api = new SeaTradeAPI(this, "localhost", 8081);
    }

    public void init() {
        api.start();

        api.register(companyName);
        api.getCargos();
        api.getHarbours();
    }

    public synchronized void addNewCargo(CargoDTO cargo) {
        db.getCargo().add(cargo);
    }

    public synchronized void setCargos(CargoDTO[] cargos) {
        db.getCargo().addBulk(cargos);
    }

    public synchronized void setHarbours(HarbourDTO[] harbours) {
        db.getHarbour().addBulk(harbours);
    }

    public synchronized void setCompany(CompanyDTO company) {
        db.getCompany().add(company);
    }

    public String getCompanyName() {
        return companyName;
    }
}
