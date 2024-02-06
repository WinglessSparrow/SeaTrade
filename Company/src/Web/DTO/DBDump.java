package Web.DTO;

import DTO.CargoDTO;
import DTO.CompanyDTO;
import DTO.HarbourDTO;
import DTO.ShipDTO;

public record DBDump(
        CompanyDTO company,
        ShipDTO[] ships,
        CargoDTO[] cargos,
        HarbourDTO[] harbour
) {
}
