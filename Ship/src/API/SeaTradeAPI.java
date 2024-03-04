package API;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.awt.Point;
import java.io.BufferedReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import DTO.SeaTradeResponseDTO;
import DataClasses.Cargo;
import DataClasses.Direction;
import DataClasses.Harbour;

public class SeaTradeAPI {

    Socket socket = null;
    PrintWriter writer = null;
    BufferedReader reader = null;
    JSONParser parser = null;
    ShipController controller = null;
    ResponseListener responseListener = null;

    class ResponseListener extends Thread {

        public void run() {
            while (!isInterrupted()) {
                try {
                    String json = reader.readLine();
                    var mapper = new ObjectMapper();
                    var response = mapper.readValue(json, SeaTradeResponseDTO.class);
                    parseResponse(response);

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
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
                controller.onReached(response.HARBOUR());
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