package Web.API;

import Logger.Logger;
import Web.Controller.WebController;
import Web.DTO.DBDump;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class WebConnectionHandler implements Runnable {

    private final Socket socket;
    private final WebController controller;

    public WebConnectionHandler(Socket socket, WebController controller) {
        this.socket = socket;
        this.controller = controller;
    }

    @Override
    public void run() {

        Logger.log("Starting to dump the DB sequence for: " + socket.getRemoteSocketAddress(), this);

        try {
            var writer = new PrintWriter(socket.getOutputStream(), true);

            var dump = controller.dumbDBData();

            writer.println(parseToJson(dump));

            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Logger.log("DB Dump Done for: " + socket.getRemoteSocketAddress(), this);
    }

    private String parseToJson(DBDump dump) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsString(dump);
    }
}
