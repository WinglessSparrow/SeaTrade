package Web.Handlers;

import Logger.Log;
import Web.Controller.WebController;
import Web.DTO.DBDumpDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

public class DataHandler implements HttpHandler {

    private final WebController controller;

    public DataHandler(WebController controller) {
        this.controller = controller;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Log.log("new DB-Dump request");

        var dto = new DBDumpDTO(controller.dumpDBData());
        var json = parseToJson(dto);
        var bytes = json.getBytes();

        Log.log("sending the DB-Dump");

        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.sendResponseHeaders(200, bytes.length);

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(bytes);
        }

        Log.log("DB-Dump sent");

        exchange.close();
    }

    private String parseToJson(DBDumpDTO dump) throws JsonProcessingException {
        var mapper = new ObjectMapper();
        return mapper.writeValueAsString(dump);
    }
}
