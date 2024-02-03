package Ship.DTO;

import DTO.ShipDTO;

public record CompanyResponseDTO(
        boolean success,
        ShipDTO ship,
        String error
) {
    public CompanyResponseDTO(CompanyResponseDTO answer, ShipDTO ship) {
        this(answer.success, ship, answer.error);
    }
}
