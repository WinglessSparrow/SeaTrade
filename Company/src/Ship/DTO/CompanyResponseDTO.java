package Ship.DTO;

import DTO.ShipDTO;

public record CompanyResponseDTO(
        boolean success,
        ShipDTO ship,
        String error
) {
}
