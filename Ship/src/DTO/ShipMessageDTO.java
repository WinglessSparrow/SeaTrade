package DTO;

import DataClasses.Direction;
import DataClasses.Ship;

import java.awt.*;

public record ShipMessageDTO(
		ShipMessageType type, 
		int id, 
		String name, 
		Point point, 
		Direction direction, 
		String harbour, 
		Integer cargoId, 
		Integer cost
		) {
}
