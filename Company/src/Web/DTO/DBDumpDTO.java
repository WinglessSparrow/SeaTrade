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
            Arrays.stream(dump.cargos()).forEach(withCounter((idx, cargo) -> {
                this.cargosData[idx] = cargoToArray(cargo);
            }));
        }

        this.shipsData = dump.ships() != null ? new String[dump.ships().length][] : new String[0][];
        if (dump.ships() != null) {
            Arrays.stream(dump.ships()).forEach(withCounter((idx, ship) -> {
                this.cargosData[idx] = shipToArray(ship);
            }));
        }

        this.harboursData = dump.harbour() != null ? new String[dump.harbour().length][] : new String[0][];
        if (dump.harbour() != null) {
            Arrays.stream(dump.harbour()).forEach(withCounter((idx, harbour) -> {
                this.harboursData[idx] = harbourToArray(harbour);
            }));
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

    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
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
