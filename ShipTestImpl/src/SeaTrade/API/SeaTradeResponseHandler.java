package SeaTrade.API;

import SeaTrade.BusinessLogic.ShipSeaTradeController;
import SeaTrade.DTO.PositionDTO;
import SeaTrade.DTO.SeaTradeResponse;
import Types.Direction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;

public class SeaTradeResponseHandler {
    private final ShipSeaTradeController shipCtr;
    private final String json;

    public SeaTradeResponseHandler(String json, ShipSeaTradeController shipCtr) {
        this.shipCtr = shipCtr;
        this.json = json;
    }

    private SeaTradeResponse parseResponse(String json) {
        try {
            return new ObjectMapper().readValue(json, SeaTradeResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void handle() {
        var msg = parseResponse(json);

        handleResponse(msg);
    }

    private void handleResponse(SeaTradeResponse response) {
        switch (response.CMD()) {
            case launched -> {
                var pos = new Point(response.POSITION().X(), response.POSITION().Y());
                var dir = Direction.valueOf(response.POSITION().DIRECTION());

                shipCtr.handleLaunched(pos, dir, response.PROFIT());
            }
            case moved -> {
                var pos = new Point(response.POSITION().X(), response.POSITION().Y());
                var dir = Direction.valueOf(response.POSITION().DIRECTION());

                shipCtr.handleMoved(pos, dir, response.PROFIT());
            }
            case reached -> shipCtr.handleReached(response.NAME());
            case loaded -> shipCtr.handleLoad(response.CARGO().ID());
            case unloaded -> shipCtr.handleUnload(response.PROFIT());
            case error ->
                    System.err.println("Error occurred during the SeaTrade response handling, received the following command:\n" + response.ERROR());

        }
    }
}
