package Ship.API;

import DTO.ShipDTO;
import Logger.Logger;
import Ship.BusinessLogic.ShipController;
import Ship.DTO.AnswerType;
import Ship.DTO.ShipAnswerDTO;
import Ship.DTO.ShipMessageDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class ShipConnection extends Thread implements Closeable {

    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private boolean isDone = false;
    private ShipController shipController;


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
        while (!isInterrupted() || isDone) {
            try {
                String json = reader.readLine();

                Logger.log("a Ship send: " + json, this);

                var message = parseMessage(json);

                ShipDTO newShipState = null;

                var answer = new ShipAnswerDTO(AnswerType.SUCCESS, null, null);

                switch (message.type()) {
                    case UPDATE -> {
                        newShipState = shipController.getShip(message.ship().name());
                    }
                    case MOVE -> {
                        newShipState = shipController.moveShip(message.ship().name(), message.point(), message.cost());
                    }
                    case REMOVE -> {
                        shipController.removeShip(message.ship().name());
                        isDone = false;
                    }
                    case ADD -> {
                        newShipState = shipController.addNewShip(message.ship());
                    }
                    case LOAD -> {
                        newShipState = shipController.registerCargoLoad(message.ship().name(), message.cargoId());
                    }
                    case UNLOAD -> {
                        newShipState = shipController.registerCargoUnload(message.ship().name(), message.cost());
                    }
                    default -> {
                        answer = new ShipAnswerDTO(AnswerType.ERROR, null, "API doesn't know: " + message.type() + " please reconnect");
                        isDone = true;
                    }
                }

                answer = new ShipAnswerDTO(answer, newShipState);

                var answerString = parseAnswer(answer);

                writer.println(answerString);

                Logger.log("I answered: " + answerString, this);
            } catch (IOException e) {
                try {
                    writer.println(parseAnswer(new ShipAnswerDTO(AnswerType.ERROR, null, "500 | unexpected error occurred")));
                } catch (IOException ex) {
                    Logger.logErr(ex.toString(), this);
                }
            }
        }

        Logger.log("Ship removed, bye bye", this);
    }

    private ShipMessageDTO parseMessage(String jsonString) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.readValue(jsonString, ShipMessageDTO.class);
    }

    private String parseAnswer(ShipAnswerDTO answer) throws JsonProcessingException {
        var writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return writer.writeValueAsString(answer);
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
