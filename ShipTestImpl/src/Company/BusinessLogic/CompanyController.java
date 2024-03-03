package Company.BusinessLogic;

import Central.CentralController;
import Company.API.CompanyAPI;
import Types.Ship;

import java.io.Closeable;
import java.io.IOException;

public class CompanyController implements Closeable {

    private final CompanyAPI api;
    private final CentralController central;

    public CompanyController(String host, int port, CentralController controller) {
        this.central = controller;
        this.api = new CompanyAPI(this, host, port);
    }

    public void handleHarbours(String[] harbours) {
        central.setHarbours(harbours);
    }

    public void handleResponse(Ship ship) {
        central.setShip(ship);
    }

    public CompanyAPI getApi() {
        return api;
    }

    @Override
    public void close() throws IOException {
        api.close();
    }
}
