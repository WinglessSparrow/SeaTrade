package API;

import java.io.*;
import java.awt.Point;
import java.net.Socket;
import java.net.UnknownHostException;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DTO.SeaTradeResponseDTO;
import DataClasses.Cargo;
import DataClasses.Direction;
import DataClasses.Harbour;

public class SeaTradeAPI implements Closeable {

    Socket socket = null;
    PrintWriter writer = null;
    BufferedReader reader = null;
    JSONParser parser = null;
    ShipController controller = null;
    ResponseListener responseListener = null;

    @Override
    public void close() throws IOException {
        responseListener.interrupt();
        socket.close();
    }

    class ResponseListener extends Thread {

        public void run() {
            while (!isInterrupted()) {
                try {
                    String json = reader.readLine();
                    if (json != null) {
                        var mapper = new ObjectMapper();
                        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
                        var response = mapper.readValue(json, SeaTradeResponseDTO.class);
                        parseResponse(response);
                    }
                } catch (IOException e) {
                    System.out.println("SeaTrade socket closed");
                }
            }
        }

    }

    public SeaTradeAPI(ShipController controller, String host, int port) {

        try {
            socket = new Socket(host, port);
            OutputStream outStream = socket.getOutputStream();
            writer = new PrintWriter(outStream, true);
            InputStream inStream = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inStream));
            parser = new JSONParser();
            this.controller = controller;
            responseListener = new ResponseListener();
            responseListener.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void launch(Harbour harbour, String name, String companyName) {
        JSONObject json = parser.parseToJSON("CMD", "launch", "COMPANY", companyName, "HARBOUR", harbour.getName(), "SHIPNAME", name);

        writer.println(json);
    }

    public void load() {

        JSONObject json = parser.parseToJSON("CMD", "loadcargo");
        writer.println(json);

    }

    public void unload() {

        JSONObject json = parser.parseToJSON("CMD", "unloadcargo");
        writer.println(json);

    }

    public void exit() {
        JSONObject json = parser.parseToJSON("CMD", "exit");
        writer.println(json);

        try {
            controller.getCompanyApi().notifyExit(controller.getShip().getId());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void moveTo(String harbourName) {

        JSONObject json = parser.parseToJSON("CMD", "moveto", "NAME", harbourName);
        writer.println(json);

    }

    private void parseResponse(SeaTradeResponseDTO response) throws JsonProcessingException {

        switch (response.CMD()) {
            case launched:
                Point pos1 = new Point(response.POSITION().X(), response.POSITION().Y());
                controller.onLaunch(response.POSITION().DIRECTION(), pos1, response.COST());
                break;
            case moved:
                Point pos2 = new Point(response.POSITION().X(), response.POSITION().Y());
                controller.onMoved(response.POSITION().DIRECTION(), pos2, response.COST());
                break;
            case reached:
                controller.onReached(response.NAME());
                break;
            case loaded:
                controller.onLoad(response.CARGO().ID());
                break;
            case unloaded:
                controller.onUnload(response.PROFIT());
                break;
            case error:
                System.out.println("Error: " + response.ERROR() + " Data: " + response.DATA());
                break;
            default:
                break;
        }

    }

}