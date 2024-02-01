package Ship.DTO;

import DTO.ShipDTO;

public record ShipAnswerDTO(AnswerType type, ShipDTO ship, String error) {
    public ShipAnswerDTO(ShipAnswerDTO answer, ShipDTO ship) {
        this(answer.type, ship, answer.error);
    }
}
