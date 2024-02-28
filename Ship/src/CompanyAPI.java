import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import DTO.ShipMessageDTO;
import DTO.ShipMessageType;
import DataClasses.Direction;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class CompanyAPI {

    private Socket socket = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;

    public CompanyAPI(String host, int port) throws UnknownHostException, IOException {

        socket = new Socket(host, port);
        OutputStream outStream = socket.getOutputStream();
        writer = new PrintWriter(outStream, true);
        InputStream inStream = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(inStream));

    }

    public void notifyLaunch(int id, String name, Point pos, Direction dir, int cost) throws JsonProcessingException {

        var ms = new ShipMessageDTO(ShipMessageType.ADD, id, name, pos, dir, null, null, cost);
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

}
