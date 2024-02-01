package Ship.DTO;

import DTO.ShipDTO;

import java.awt.*;

public record ShipMessageDTO(
        ShipMessageType type,
        String cargoId,
        Integer cost,
        Point point,
        ShipDTO ship
) {
}
