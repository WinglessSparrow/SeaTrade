package SeaTrade.DTO.Response;

public record SeaTradeCargoArrayDTO(ResponseTypes CMD, CargoDTO[] CARGO, HarbourDTO[] HARBOUR,
                                    int DEPOSIT, SizeDTO SIZE, String ERROR, String DATA) {
}
