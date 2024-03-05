package Ship.API;

import Ship.DTO.ShipMessageType;
import Types.Harbour;
import Types.Ship;
import Logger.Log;
import Ship.BusinessLogic.ShipController;
import Ship.DTO.CompanyResponseDTO;
import Ship.DTO.ShipMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ShipConnection extends Thread implements Closeable {

    private final Socket socket;
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final ShipController shipController;
    private boolean isDone = false;

    private int shipId;

    public ShipConnection(Socket socket, ShipController shipController) {
        this.socket = socket;
        this.shipController = shipController;

        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (!isInterrupted() && !isDone) {
            try {
                String json = reader.readLine();

                Log.log("a Ship talked to me");

                CompanyResponseDTO answer = handleMessage(parseMessage(json));

                var answerString = parseAnswer(answer);

                writer.println(answerString);

                Log.log("I answered: " + answerString);
            } catch (IOException e) {
                isDone = true;

                Log.logErr(e.toString());

                if (e.toString().contains("socket is closed")) {
                    try {
                        writer.println(parseAnswer(new CompanyResponseDTO(false, null, null, "500 | unexpected error occurred | please reconnect")));
                    } catch (IOException ex) {
                        Log.logErr(ex.toString());
                    }
                }
            }
        }

        shipController.removeShip(shipId);

        Log.logGreen("Ship - " + shipId + ", has exited, bye bye");

        close();
    }

    @SuppressWarnings("ReassignedVariable")
    private CompanyResponseDTO handleMessage(ShipMessageDTO message) {
        Log.log("Ships message: ");
        Log.logJson(message);

        Ship newShipState = null;
        String[] harbours = null;
        boolean unknownCommand = false;

        shipId = message.id();

        switch (message.type()) {
            case UPDATE -> newShipState = shipController.getShip(message.id());

            case MOVE ->
                    newShipState = shipController.moveShip(message.id(), message.point(), message.direction(), message.harbour(), message.cost());

            case REMOVE -> {
                newShipState = shipController.removeShip(message.id());
                isDone = true;
            }
            case ADD ->
                    newShipState = shipController.addNewShip(new Ship(message.name(), -1, message.point(), message.direction(), new Harbour(-1, message.harbour(), null), null));

            case LOAD -> newShipState = shipController.registerCargoLoad(message.id(), message.cargoId());

            case UNLOAD -> newShipState = shipController.registerCargoUnload(message.id(), message.cost());

            case REACHED -> newShipState = shipController.registerHarbourReached(message.id(), message.harbour());

            case HARBOURS -> harbours = shipController.getHarbourNames();

            default -> {
                isDone = true;
                unknownCommand = true;
                Log.logErr("Unknown command received");
            }
        }

        var errorMessage = (newShipState == null && unknownCommand) ? "API doesn't know: " + message.type() + " please reconnect" : "The ship ID of " + message.id() + " not contained in the DB";

        CompanyResponseDTO resp;

        if (newShipState == null && harbours == null) resp = new CompanyResponseDTO(false, null, null, errorMessage);
        else if (newShipState != null) resp = new CompanyResponseDTO(true, newShipState, null, null);
        else resp = new CompanyResponseDTO(true, null, harbours, null);

        return resp;

    }

    private ShipMessageDTO parseMessage(String jsonString) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ShipMessageDTO.class);
    }

    private String parseAnswer(CompanyResponseDTO answer) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(answer);
    }

    @Override
    public void close() {
        interrupt();
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
