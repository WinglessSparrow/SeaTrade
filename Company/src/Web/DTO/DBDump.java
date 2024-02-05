package Web.DTO;

import DTO.CargoDTO;
import DTO.CompanyDTO;
import DTO.DTO;
import DTO.ShipDTO;

public record DBDump(
        CompanyDTO company,
        ShipDTO[] ships,
        CargoDTO[] cargos,
        DTO[] harbour
) {
}
