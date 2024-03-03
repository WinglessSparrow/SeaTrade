package SeaTrade.DTO;

public record SeaTradeMessage(
        ShipCMD CMD,
        String NAME,
        String COMPANY,
        String HARBOUR,
        String SHIPNAME
) {
}
