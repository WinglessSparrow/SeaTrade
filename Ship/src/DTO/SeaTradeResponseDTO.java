package DTO;

public record SeaTradeResponseDTO(ResponseTypes CMD, CargoResponseDTO CARGO, String HARBOUR, PositionDTO POSITION, int COST, int PROFIT,
                                   String ERROR, String DATA) {
}
