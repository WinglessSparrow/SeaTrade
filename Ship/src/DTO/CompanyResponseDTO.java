package DTO;

import DataClasses.Ship;

public record CompanyResponseDTO(boolean success, Ship ship, String error) {
}
