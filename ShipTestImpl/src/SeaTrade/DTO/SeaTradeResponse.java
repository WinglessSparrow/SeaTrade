package SeaTrade.DTO;

public record SeaTradeResponse(
        ResponseShipCMD CMD,
        PositionDTO POSITION,
        int COST,
        String NAME,
        CargoDTO CARGO,
        int PROFIT,
        String ERROR
) {
}
