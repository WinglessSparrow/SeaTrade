package Company.DTO;

import Types.Ship;

public record CompanyResponseDTO(
        boolean success,
        Ship ship,
        String[] harbours,
        String error
) {
}
