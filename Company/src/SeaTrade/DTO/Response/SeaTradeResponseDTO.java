package SeaTrade.DTO.Response;

public record SeaTradeResponseDTO(ResponseTypes CMD, CargoResponseDTO[] CARGO, HarbourResponseDTO[] HARBOUR,
                                  int DEPOSIT, SizeDTO SIZE, String ERROR, String DATA) {
}
