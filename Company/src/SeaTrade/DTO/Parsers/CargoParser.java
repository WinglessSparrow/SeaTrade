package SeaTrade.DTO.Parsers;

import Types.Cargo;
import Types.Harbour;
import SeaTrade.DTO.Response.CargoDTO;

public class CargoParser {
    public static CargoDTO parse(Cargo cargo) {
        return new CargoDTO(cargo.id(), cargo.src().name(), cargo.dest().name(), cargo.value());
    }

    public static Cargo parseResponse(CargoDTO cargo) {
        return new Cargo(new Harbour(0, cargo.DESTINATION(), null), new Harbour(0, cargo.SOURCE(), null), cargo.ID(), cargo.VALUE());
    }

    public static Cargo[] parseResponseArr(CargoDTO[] cargos) {
        var cargoArr = new Cargo[cargos.length];

        for (int i = 0; i < cargos.length; i++) {
            cargoArr[i] = parseResponse(cargos[i]);
        }

        return cargoArr;
    }
}
