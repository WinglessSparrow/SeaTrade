package SeaTrade.DTO.Parsers;

import Types.Cargo;
import Types.Harbour;
import SeaTrade.DTO.Response.CargoResponseDTO;

public class CargoParser {
    public static CargoResponseDTO parse(Cargo cargo) {
        return new CargoResponseDTO(cargo.id(), cargo.src().name(), cargo.dest().name(), cargo.value());
    }

    public static Cargo parseResponse(CargoResponseDTO cargo) {
        return new Cargo(new Harbour(0, cargo.DESTINATION(), null), new Harbour(0, cargo.SOURCE(), null), cargo.ID(), cargo.VALUE());
    }

    public static Cargo[] parseResponseArr(CargoResponseDTO[] cargos) {
        var cargoArr = new Cargo[cargos.length];

        for (int i = 0; i < cargos.length; i++) {
            cargoArr[i] = parseResponse(cargos[i]);
        }

        return cargoArr;
    }
}
