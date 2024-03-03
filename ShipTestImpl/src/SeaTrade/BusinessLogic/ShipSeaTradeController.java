package SeaTrade.BusinessLogic;

import Central.CentralController;
import Company.BusinessLogic.CompanyController;
import SeaTrade.API.ShipSeaTradeAPI;
import Types.Direction;

import java.awt.*;
import java.io.Closeable;
import java.io.IOException;

public class ShipSeaTradeController implements Closeable {

    private final ShipSeaTradeAPI api;
    private final CompanyController cCtr;
    private final CentralController central;

    public ShipSeaTradeController(CentralController controller, CompanyController cCtr, String host, int port) {
        this.central = controller;
        this.cCtr = cCtr;
        this.api = new ShipSeaTradeAPI(host, port, this);
    }

    public void handleLaunched(Point pos, Direction dir, int cost) {
        cCtr.getApi().registerShip(central.getShip().name(), pos, dir, central.getShip().harbour().name(), cost);
    }

    public void handleMoved(Point pos, Direction dir, int cost) {
        cCtr.getApi().moveShip(cost, central.getShip(), pos, dir, central.getDestHarbour());
    }

    public void handleReached(String harbourName) {
        cCtr.getApi().reached(central.getShip(), harbourName);
    }

    public void handleLoad(int id) {
        cCtr.getApi().loadCargo(central.getShip(), id);
    }

    public void handleUnload(int profit) {
        cCtr.getApi().unloadCargo(central.getShip(), profit);
    }

    public ShipSeaTradeAPI getApi() {
        return api;
    }

    @Override
    public void close() throws IOException {
        api.close();
    }
}
