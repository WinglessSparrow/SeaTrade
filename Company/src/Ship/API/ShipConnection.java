package Ship.API;

import DTO.ShipDTO;
import Logger.Logger;
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
        while (!isInterrupted() || !isDone) {
            try {
                String json = reader.readLine();

                Logger.log("the Ship send: " + json, this);

                CompanyResponseDTO answer = handleMessage(parseMessage(json));

                var answerString = parseAnswer(answer);

                writer.println(answerString);

                Logger.log("I answered: " + answerString, this);
            } catch (IOException e) {
                try {
                    writer.println(parseAnswer(new CompanyResponseDTO(false, null, "500 | unexpected error occurred | please reconnect")));
                    isDone = true;
                } catch (IOException ex) {
                    Logger.logErr(ex.toString(), this);
                }
            }
        }

        Logger.log("Ship removed, bye bye", this);

        close();
    }

    @SuppressWarnings("ReassignedVariable")
    private CompanyResponseDTO handleMessage(ShipMessageDTO message) {
        ShipDTO newShipState = null;

        switch (message.type()) {
            case UPDATE -> newShipState = shipController.getShip(message.ship().name());

            case MOVE -> newShipState = shipController.moveShip(message.ship().name(), message.point(), message.cost());

            case REMOVE -> {
                newShipState = shipController.removeShip(message.ship().name());
                isDone = false;
            }
            case ADD -> newShipState = shipController.addNewShip(message.ship());

            case LOAD -> newShipState = shipController.registerCargoLoad(message.ship().name(), message.cargoId());

            case UNLOAD -> newShipState = shipController.registerCargoUnload(message.ship().name(), message.cost());

            default -> isDone = true;
        }

        return (newShipState == null) ? new CompanyResponseDTO(false, null, "API doesn't know: " + message.type() + " please reconnect") : new CompanyResponseDTO(true, newShipState, null);

    }

    private ShipMessageDTO parseMessage(String jsonString) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ShipMessageDTO.class);
    }

    private String parseAnswer(CompanyResponseDTO answer) throws JsonProcessingException {
        var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(answer);
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
