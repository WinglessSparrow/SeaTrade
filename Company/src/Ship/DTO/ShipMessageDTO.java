package Ship.DTO;

import Types.Ship;

import java.awt.*;

public record ShipMessageDTO(
        ShipMessageType type,
        String cargoId,
        Integer cost,
        Point point,
        Ship ship
) {
}
