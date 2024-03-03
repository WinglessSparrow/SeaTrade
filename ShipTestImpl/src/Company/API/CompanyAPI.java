package Company.API;

import Company.BusinessLogic.CompanyController;
import Company.DTO.CompanyResponseDTO;
import Company.DTO.ShipMessageDTO;
import Company.DTO.ShipMessageType;
import Types.Direction;
import Types.Ship;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.io.*;
import java.net.Socket;

public class CompanyAPI implements Closeable {

    private final PrintWriter writer;
    private final Socket socket;
    private final CompanyResponseListener listener;

    public CompanyAPI(CompanyController controller, String host, int port) {

        try {
            this.socket = new Socket(host, port);
            this.writer = new PrintWriter(socket.getOutputStream(), true);

            var reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            this.listener = new CompanyResponseListener(reader, controller, this);
            listener.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String parseMessage(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public CompanyResponseDTO parseResponse(String json) {
        try {
            return new ObjectMapper().readValue(json, CompanyResponseDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void registerShip(String name, Point point, Direction dir, String harbour, int cost) {
        var msg = new ShipMessageDTO(ShipMessageType.ADD, null, name, point, dir, harbour, null, cost);

        writer.println(parseMessage(msg));
    }

    public void reached(Ship ship, String harbour) {
        var msg = new ShipMessageDTO(ShipMessageType.REACHED, ship.id(), null, null, null, harbour, null, null);

        writer.println(parseMessage(msg));
    }

    public void moveShip(int cost, Ship ship, Point point, Direction dir, String harbour) {
        var msg = new ShipMessageDTO(ShipMessageType.MOVE, ship.id(), ship.name(), point, dir, harbour, ship.heldCargo().id(), cost);

        writer.println(parseMessage(msg));
    }

    public void removeShip(Ship ship) {
        var msg = new ShipMessageDTO(ShipMessageType.REMOVE, ship.id(), null, null, null, null, null, null);

        writer.println(parseMessage(msg));
    }

    public void loadCargo(Ship ship, Integer cargoId) {
        var msg = new ShipMessageDTO(ShipMessageType.LOAD, ship.id(), null, null, null, null, cargoId, null);

        writer.println(parseMessage(msg));
    }

    public void unloadCargo(Ship ship, int profit) {
        var msg = new ShipMessageDTO(ShipMessageType.LOAD, ship.id(), null, null, null, null, null, profit);

        writer.println(parseMessage(msg));
    }

    public void getHarbours() {
        var msg = new ShipMessageDTO(ShipMessageType.HARBOURS, null, null, null, null, null, null, null);

        writer.println(parseMessage(msg));
    }

    public void update(Ship ship) {
        var msg = new ShipMessageDTO(ShipMessageType.UPDATE, ship.id(), null, null, null, null, null, null);

        writer.println(parseMessage(msg));
    }

    @Override
    public void close() throws IOException {
        this.socket.close();
        listener.interrupt();
    }
}
