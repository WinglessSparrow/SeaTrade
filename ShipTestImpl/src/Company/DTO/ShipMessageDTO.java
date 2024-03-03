package Company.DTO;

import Types.Direction;

import java.awt.*;

public record ShipMessageDTO(
        ShipMessageType type,
        Integer id,
        String name,
        Point point,
        Direction direction,
        String harbour,
        Integer cargoId,
        Integer cost
) {
}
