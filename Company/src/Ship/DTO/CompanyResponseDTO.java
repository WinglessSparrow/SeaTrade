package Ship.DTO;

import Types.Ship;

public record CompanyResponseDTO(
        boolean success,
        Ship ship,
        String error
) {
}
