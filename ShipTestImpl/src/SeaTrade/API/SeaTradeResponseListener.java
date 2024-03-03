package SeaTrade.API;

import SeaTrade.BusinessLogic.ShipSeaTradeController;

import java.io.BufferedReader;
import java.io.IOException;

public class SeaTradeResponseListener extends Thread {

    private final BufferedReader reader;

    private final ShipSeaTradeController controller;

    public SeaTradeResponseListener(BufferedReader reader, ShipSeaTradeController controller) {
        this.reader = reader;
        this.controller = controller;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                var json = reader.readLine();

                new SeaTradeResponseHandler(json, controller).handle();


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
