package SeaTrade.DTO.Parsers;

import DTO.CargoDTO;
import DTO.HarbourDTO;
import SeaTrade.DTO.Response.CargoResponseDTO;

public class CargoParser {
    public static CargoResponseDTO parse(CargoDTO cargo) {
        return new CargoResponseDTO(cargo.id(), cargo.src().name(), cargo.dest().name(), cargo.value());
    }

    public static CargoDTO parseResponse(CargoResponseDTO cargo) {
        return new CargoDTO(new HarbourDTO(0, cargo.DESTINATION(), null), new HarbourDTO(0, cargo.SOURCE(), null), cargo.ID(), cargo.VALUE());
    }

    public static CargoDTO[] parseResponseArr(CargoResponseDTO[] cargos) {
        var cargoArr = new CargoDTO[cargos.length];

        for (int i = 0; i < cargos.length; i++) {
            cargoArr[i] = parseResponse(cargos[i]);
        }

        return cargoArr;
    }
}
