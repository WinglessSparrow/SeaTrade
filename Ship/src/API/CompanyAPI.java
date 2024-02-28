package API;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DTO.CompanyResponseDTO;
import DTO.SeaTradeResponseDTO;
import DTO.ShipMessageDTO;
import DTO.ShipMessageType;
import DataClasses.Direction;
import DataClasses.Ship;


public class CompanyAPI {

    private Socket socket = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;
    private ResponseListener responseListener = null;
    private ShipController controller = null;

    class ResponseListener extends Thread {

        public void run() {
            while (!isInterrupted()) {
                try {
                    String json = reader.readLine();
                    var mapper = new ObjectMapper();
                    var response = mapper.readValue(json, CompanyResponseDTO.class);
                    parseResponse(response);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

    }

    public CompanyAPI(String host, int port) throws UnknownHostException, IOException {

//		socket = new Socket(host, port);
//		OutputStream outStream = socket.getOutputStream();
//		writer = new PrintWriter(outStream, true);
//		InputStream inStream = socket.getInputStream();
//		reader = new BufferedReader(new InputStreamReader(inStream));
//		responseListener = new ResponseListener();
//		responseListener.start();

    }

    public void notifyLaunch(String name, Point pos, Direction dir, int cost) throws JsonProcessingException {

        var ms = new ShipMessageDTO(ShipMessageType.ADD, 0, name, pos, dir, null, null, cost);
        writer.write(new ObjectMapper().writeValueAsString(ms));

    }

    public void notifyMoved(int id, Point pos, Direction dir, int cost) throws JsonProcessingException {

        var ms = new ShipMessageDTO(ShipMessageType.MOVE, id, null, pos, dir, null, null, cost);
        writer.write(new ObjectMapper().writeValueAsString(ms));

    }

    public void notifyReached(int id, String harbour) throws JsonProcessingException {

        var ms = new ShipMessageDTO(ShipMessageType.REACHED, id, null, null, null, harbour, null, null);
        writer.write(new ObjectMapper().writeValueAsString(ms));

    }

    public void notifyLoad(int id, int cargoId) throws JsonProcessingException {

        var ms = new ShipMessageDTO(ShipMessageType.LOAD, id, null, null, null, null, cargoId, null);
        writer.write(new ObjectMapper().writeValueAsString(ms));

    }

    public void notifyUnload(int id, int cost) throws JsonProcessingException {

        var ms = new ShipMessageDTO(ShipMessageType.UNLOAD, id, null, null, null, null, null, cost);
        writer.write(new ObjectMapper().writeValueAsString(ms));

    }

    public void notifyExit(int id) throws JsonProcessingException {

        var ms = new ShipMessageDTO(ShipMessageType.REMOVE, id, null, null, null, null, null, null);
        writer.write(new ObjectMapper().writeValueAsString(ms));

    }

    public void setController(ShipController controller) {

        this.controller = controller;

    }

    private void parseResponse(CompanyResponseDTO response) throws JsonProcessingException {

        if (controller == null) {
            return;
        }

        if (response.success() == true) {


        }

    }

}
