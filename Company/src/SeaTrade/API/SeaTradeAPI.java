package SeaTrade.API;

import Logger.Logger;
import SeaTrade.BusinessLogic.SeaTradeController;
import SeaTrade.DTO.Parsers.CargoParser;
import SeaTrade.DTO.Parsers.CompanyParser;
import SeaTrade.DTO.Parsers.HarbourParser;
import SeaTrade.DTO.Request.CMDTypes;
import SeaTrade.DTO.Request.InfoTopic;
import SeaTrade.DTO.Request.SeaTradeRequestDTO;
import SeaTrade.DTO.Response.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;

public class SeaTradeAPI extends Thread implements Closeable {
    private final SeaTradeController controller;

    private final BufferedReader reader;
    private final PrintWriter writer;

    private final Socket socket;

    public SeaTradeAPI(SeaTradeController controller, String host, int port) {
        this.controller = controller;

        try {
            socket = new Socket(host, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            Logger.logErr(e.toString());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                String json = reader.readLine();

                Logger.log("New response from the SeaTrade server, they say " + json);

                if (isCargoObject(json)) {
                    handleNewCargoResponse(parseResponseCargo(json));
                } else {
                    handleResponse(parseResponse(json));
                }

                Logger.log("SeaTrade server response handled");

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private void handleNewCargoResponse(SeaTradeResponseCargo response) {
        controller.addNewCargo(CargoParser.parseResponse(response.CARGO()));
    }

    private void handleResponse(SeaTradeResponseDTO dto) {
        switch (dto.CMD()) {
            case error -> Logger.logErr(dto.ERROR() + " | " + dto.DATA());
            case endinfo -> {
                if (dto.CARGO() != null) {
                    controller.setCargos(CargoParser.parseResponseArr(dto.CARGO()));
                } else if (dto.HARBOUR() != null) {
                    controller.setHarbours(HarbourParser.parserResponseArr(dto.HARBOUR()));
                }
            }
            case registered -> controller.setCompany(CompanyParser.parseResponse(dto, controller.getCompanyName()));
            default -> Logger.logErr("Unknown Command received: " + dto.CMD());

        }
    }

    private boolean isCargoObject(String json) throws JsonProcessingException {
        return json.contains("\"CMD\":\"newCargo\"");
    }

    private SeaTradeResponseCargo parseResponseCargo(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, SeaTradeResponseCargo.class);
    }

    private SeaTradeResponseDTO parseResponse(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, SeaTradeResponseDTO.class);
    }

    private String parseRequest(SeaTradeRequestDTO dto) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(dto);
    }

    public void register(String companyName) {
        var request = new SeaTradeRequestDTO(CMDTypes.register, null, companyName);

        try {
            writer.println(parseRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Logger.log("Register Request OUT");
    }

    public void getCargos() {
        var request = new SeaTradeRequestDTO(CMDTypes.getinfo, InfoTopic.cargo, null);

        try {
            writer.println(parseRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Logger.log("Get Cargos OUT");
    }

    public void getHarbours() {
        var request = new SeaTradeRequestDTO(CMDTypes.getinfo, InfoTopic.harbour, null);

        try {
            writer.println(parseRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Logger.log("Get Harbours OUT");
    }

    public void exit() {
        var request = new SeaTradeRequestDTO(CMDTypes.exit, null, null);

        try {
            writer.println(parseRequest(request));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        Logger.log("Get TF OUT");
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
