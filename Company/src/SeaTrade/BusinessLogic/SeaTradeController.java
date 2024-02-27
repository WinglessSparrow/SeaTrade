package SeaTrade.BusinessLogic;

import Types.Cargo;
import Types.Company;
import Types.Harbour;
import Database.DB;
import SeaTrade.API.SeaTradeAPI;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;

public class SeaTradeController implements Closeable {
    private final DB db;

    private final String companyName;

    private final SeaTradeAPI api;

    public SeaTradeController(DB db, String companyName, InetSocketAddress addr) {
        this.db = db;
        this.companyName = companyName;

        api = new SeaTradeAPI(this, addr.getHostName(), addr.getPort());
    }

    public void init() {
        api.start();

        api.register(companyName);
        api.getHarbours();
        api.getCargos();
    }

    public synchronized void addNewCargo(Cargo cargo) {
        var dest = db.getHarbour().getByName(cargo.dest().name());
        var src = db.getHarbour().getByName(cargo.src().name());

        db.getCargo().add(new Cargo(dest, src, cargo));
    }

    public synchronized void setCargos(Cargo[] cargos) {
        db.getCargo().addBulk(cargos);
    }

    public synchronized void setHarbours(Harbour[] harbours) {
        db.getHarbour().addBulk(harbours);
    }

    public synchronized void setCompany(Company company) {
        db.getCompany().add(company);
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public void close() throws IOException {
        api.exit();
        api.close();
    }
}
