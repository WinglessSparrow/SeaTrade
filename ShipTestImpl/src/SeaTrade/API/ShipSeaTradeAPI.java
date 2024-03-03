package SeaTrade.API;

import SeaTrade.BusinessLogic.ShipSeaTradeController;
import SeaTrade.DTO.SeaTradeMessage;
import SeaTrade.DTO.ShipCMD;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ShipSeaTradeAPI implements Closeable {

    private final PrintWriter writer;
    private final Socket socket;
    private final SeaTradeResponseListener listener;

    public ShipSeaTradeAPI(String host, int port, ShipSeaTradeController shipCtr) {
        try {
            this.socket = new Socket(host, port);
            this.writer = new PrintWriter(socket.getOutputStream(), true);

            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.listener = new SeaTradeResponseListener(reader, shipCtr);
            listener.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String parseMessage(Object msg) {
        try {
            return new ObjectMapper().writeValueAsString(msg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void launch(String companyName, String harbourName, String shipName) {
        var msg = new SeaTradeMessage(ShipCMD.launch, null, companyName, harbourName, shipName);

        writer.println(parseMessage(msg));
    }

    public void moveTo(String harbourName) {
        var msg = new SeaTradeMessage(ShipCMD.moveto, harbourName, null, null, null);

        writer.println(parseMessage(msg));
    }

    public void loadCargo() {
        var msg = new SeaTradeMessage(ShipCMD.loadcargo, null, null, null, null);

        writer.println(parseMessage(msg));
    }

    public void unloadCargo() {
        var msg = new SeaTradeMessage(ShipCMD.unloadcargo, null, null, null, null);

        writer.println(parseMessage(msg));
    }

    public void exit() {
        var msg = new SeaTradeMessage(ShipCMD.exit, null, null, null, null);

        writer.println(parseMessage(msg));
    }

    @Override
    public void close() throws IOException {
        socket.close();
        listener.interrupt();
    }
}
