package SeaTrade.BusinessLogic;

import DTO.CargoDTO;
import DTO.CompanyDTO;
import DTO.HarbourDTO;
import Database.DB;
import SeaTrade.API.SeaTradeAPI;

import java.io.Closeable;
import java.io.IOException;

public class SeaTradeController implements Closeable {
    private final DB db;

    private final String companyName;

    private final SeaTradeAPI api;

    public SeaTradeController(DB db, String companyName, int port) {
        this.db = db;
        this.companyName = companyName;

        api = new SeaTradeAPI(this, "localhost", port);
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

    @Override
    public void close() throws IOException {
        api.close();
    }
}
