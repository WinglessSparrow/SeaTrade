package Web.DTO;

import Types.*;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class DBDumpDTO {
    private final Company company;
    private final String[][] cargosData;
    private final String[][] shipsData;
    private final String[][] harboursData;


    public DBDumpDTO(DBDump dump) {
        company = dump.company();

        this.cargosData = dump.cargos() != null ? new String[dump.cargos().length][] : new String[0][];
        if (dump.cargos() != null) {
            for (int i = 0; i < dump.cargos().length; i++) {
                this.cargosData[i] = cargoToArray(dump.cargos()[i]);
            }
        }

        this.shipsData = dump.ships() != null ? new String[dump.ships().length][] : new String[0][];
        if (dump.ships() != null) {
            for (int i = 0; i < dump.ships().length; i++) {
                this.shipsData[i] = shipToArray(dump.ships()[i]);
            }
        }

        this.harboursData = dump.harbour() != null ? new String[dump.harbour().length][] : new String[0][];
        if (dump.harbour() != null) {
            for (int i = 0; i < dump.harbour().length; i++) {
                this.harboursData[i] = harbourToArray(dump.harbour()[i]);
            }
        }
    }

    private String[] cargoToArray(Cargo cargo) {
        return new String[]{cargo.id() + "", cargo.src().name(), cargo.dest().name(), cargo.value() + ""};
    }

    private String[] shipToArray(Ship ship) {
        return new String[]{ship.name(), ship.pos().x + "", ship.pos().y + "", ship.dir().name(), ship.heldCargo() == null ? "null" : ship.heldCargo().id() + "", ship.harbour() == null ? "null" : ship.harbour().name()};
    }

    private String[] harbourToArray(Harbour harbour) {
        return new String[]{harbour.id() + "", harbour.name(), harbour.pos().x + "", harbour.pos().y + ""};
    }

    public Company getCompany() {
        return company;
    }

    public String[][] getCargosData() {
        return cargosData;
    }

    public String[][] getShipsData() {
        return shipsData;
    }

    public String[][] getHarboursData() {
        return harboursData;
    }
}
