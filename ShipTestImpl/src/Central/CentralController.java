package Central;

import Company.BusinessLogic.CompanyController;
import SeaTrade.BusinessLogic.ShipSeaTradeController;
import Types.Harbour;
import Types.Ship;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class CentralController implements Closeable {
    private Ship ship;
    private String[] harbours;
    private String destHarbour;
    private final String companyName;

    private final CompanyController cCtr;
    private final ShipSeaTradeController sCtr;

    public String getDestHarbour() {
        return destHarbour;
    }

    public CentralController() {
        var scanner = new Scanner(System.in);

        System.out.print("Company Name:");
        companyName = scanner.nextLine();

        System.out.print("Company IP:");
        final String host = scanner.nextLine();

        System.out.print("Company Port:");
        final int port = Integer.parseInt(scanner.nextLine());

        this.cCtr = new CompanyController(host, port, this);

        cCtr.getApi().getHarbours();

        System.out.print("SeaTrade IP:");
        final String seatradeHost = scanner.nextLine();

        System.out.print("SeaTrade Port:");
        final int seatradePort = Integer.parseInt(scanner.nextLine());

        System.out.print("Ship name:");
        final String shipName = scanner.nextLine();

        System.out.println("Available Harbours: ");
        Arrays.stream(harbours).forEach(System.out::println);

        System.out.print("Starting Harbour: ");
        final String startingHarbour = scanner.nextLine();

        ship = new Ship(shipName, 0, null, null, new Harbour(-1, startingHarbour, null), null);

        this.sCtr = new ShipSeaTradeController(this, cCtr, seatradeHost, seatradePort);
        sCtr.getApi().launch(companyName, startingHarbour, shipName);
    }

    public void setDestHarbour(String destHarbour) {
        this.destHarbour = destHarbour;
    }

    public synchronized Ship getShip() {
        return ship;
    }

    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public String[] getHarbours() {
        return harbours;
    }

    public void setHarbours(String[] harbours) {
        this.harbours = harbours;
    }

    @Override
    public void close() throws IOException {
        cCtr.close();
        sCtr.close();
    }
}
